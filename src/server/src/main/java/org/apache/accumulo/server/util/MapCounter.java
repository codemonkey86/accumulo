package org.apache.accumulo.server.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class MapCounter<KT> {
	
	static class MutableLong {
		long l = 0l;
	}
	
	private HashMap<KT, MutableLong> map;
	
	public MapCounter(){
		map = new HashMap<KT, MutableLong>();
	}
	
	public long increment(KT key, long l){
		MutableLong ml = map.get(key);
		if(ml == null){
			ml = new MutableLong();
			map.put(key, ml);
		}
		
		ml.l += l;
		
		if(ml.l == 0){
			map.remove(key);
		}
		
		return ml.l;
	}
	
	public long decrement(KT key, long l){
		return increment(key, -1*l);
	}
	
	public boolean contains(KT key){
		return map.containsKey(key);
	}
	
	public long get(KT key){
		MutableLong ml = map.get(key);
		if(ml == null){
			return 0;
		}
		
		return ml.l;
	}
	
	public Set<KT> keySet(){
		return map.keySet();
	}
	
	public Collection<Long> values(){
		Collection<MutableLong> vals = map.values();
		ArrayList<Long> ret = new ArrayList<Long>(vals.size());
		for (MutableLong ml : vals) {
			ret.add(ml.l);
		}
		
		return ret;
	}
	
	public int size(){
		return map.size();
	}
}