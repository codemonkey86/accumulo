package org.apache.accumulo.core.iterators.conf;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.iterators.conf.ColumnUtil.ColFamHashKey;
import org.apache.accumulo.core.iterators.conf.ColumnUtil.ColHashKey;
import org.apache.accumulo.start.classloader.AccumuloClassLoader;
import org.apache.hadoop.io.Text;


@SuppressWarnings("deprecation")
public class ColumnToClassMapping<K> {

	private HashMap<ColFamHashKey, K> objectsCF;
	private HashMap<ColHashKey, K> objectsCol;
	
	private ColHashKey lookupCol = new ColHashKey();
	private ColFamHashKey lookupCF = new ColFamHashKey();
	
	public ColumnToClassMapping(){
		objectsCF = new HashMap<ColFamHashKey, K>();
		objectsCol = new HashMap<ColHashKey, K>();
	}
	
	public ColumnToClassMapping(Map<String, String> objectStrings, Class<? extends K> c) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		this();
		
			
		for (Entry<String, String> entry : objectStrings.entrySet()) {
			String column = entry.getKey();
			String className = entry.getValue();
			
			PerColumnIteratorConfig pcic = PerColumnIteratorConfig.decodeColumns(column, className);
			
			Class<? extends K> clazz = AccumuloClassLoader.loadClass(className, c);
			
			if(pcic.getColumnQualifier() == null){
				addObject(pcic.getColumnFamily(), clazz.newInstance());
			}else{
				addObject(pcic.getColumnFamily(), pcic.getColumnQualifier(), clazz.newInstance());
			}
		}
	}

	protected void addObject(Text colf, K obj){
		objectsCF.put(new ColFamHashKey(new Text(colf)), obj);
	}
	
	protected void addObject(Text colf, Text colq, K obj){
		objectsCol.put(new ColHashKey(colf, colq), obj);
	}
	
	public K getObject(Key key) {
		K obj = null;
		
		//lookup column family and column qualifier
		if(objectsCol.size() > 0){
			lookupCol.set(key);
			obj = objectsCol.get(lookupCol);
			if(obj != null){
				return obj;
			}
		}
		
		//lookup just column family
		if(objectsCF.size() > 0){
			lookupCF.set(key);
			obj = objectsCF.get(lookupCF);
		}
				
		return obj;
	}
	
	public boolean isEmpty() {
		return objectsCol.size() == 0 && objectsCF.size() == 0;
	}
}