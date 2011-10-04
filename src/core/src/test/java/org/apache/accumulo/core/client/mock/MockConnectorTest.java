package org.apache.accumulo.core.client.mock;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.accumulo.core.Constants;
import org.apache.accumulo.core.client.BatchScanner;
import org.apache.accumulo.core.client.BatchWriter;
import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.IteratorSetting;
import org.apache.accumulo.core.client.Scanner;
import org.apache.accumulo.core.client.mock.MockConnector;
import org.apache.accumulo.core.client.mock.MockInstance;
import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Mutation;
import org.apache.accumulo.core.data.Range;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.iterators.Combiner;
import org.apache.accumulo.core.iterators.SortedKeyValueIterator;
import org.apache.accumulo.core.iterators.user.SummingCombiner;
import org.apache.hadoop.io.Text;
import org.junit.Test;


public class MockConnectorTest  {
    Random random = new Random();
    
    static Text asText(int i) {
        return new Text(Integer.toHexString(i));
    }
    
    @Test
    public void testSunnyDay() throws Exception {
        Connector c = new MockConnector("root");
        c.tableOperations().create("test");
        BatchWriter bw = c.createBatchWriter("test", 10000L, 1000L, 4);
        for (int i = 0; i < 100; i++) {
            int r = random.nextInt();
            Mutation m = new Mutation(asText(r));
            m.put(asText(random.nextInt()), asText(random.nextInt()), new Value(Integer.toHexString(r).getBytes()));
            bw.addMutation(m);
        }
        bw.close();
        BatchScanner s = c.createBatchScanner("test", Constants.NO_AUTHS, 2);
        s.setRanges(Collections.singletonList(new Range()));
        Key key = null;
        int count = 0;
        for (Entry<Key, Value> entry : s) {
            if (key != null)
                assertTrue(key.compareTo(entry.getKey()) < 0);
            assertEquals(entry.getKey().getRow(), new Text(entry.getValue().get()));
            key = entry.getKey();
            count++;
        }
        assertEquals(100, count);
    }
    
    @Test
    public void testAggregation() throws Exception {
        MockInstance mockInstance = new MockInstance();
        Connector c = mockInstance.getConnector("root", new byte[]{});
        String table = "perDayCounts";
        c.tableOperations().create(table);
        Class<? extends SortedKeyValueIterator<Key, Value>> clazz = SummingCombiner.class;
        IteratorSetting is = new IteratorSetting(10, "String Summation", clazz);
        Combiner.addColumn(new Text("day"), null, is);
        is.addOption(SummingCombiner.TYPE, SummingCombiner.Type.STRING.name());
        c.tableOperations().attachIterator(table, is);
        String keys[][] = {
                { "foo", "day", "20080101" },
                { "foo", "day", "20080101" },
                { "foo", "day", "20080103" },
                { "bar", "day", "20080101" },
                { "bar", "day", "20080101" },
        };
        BatchWriter bw = c.createBatchWriter("perDayCounts", 1000L, 1000L, 1);
        for (String elt[] : keys) {
            Mutation m = new Mutation(new Text(elt[0]));
            m.put(new Text(elt[1]), new Text(elt[2]), new Value("1".getBytes()));
            bw.addMutation(m);
        }
        bw.close();
        
        Scanner s = c.createScanner("perDayCounts", Constants.NO_AUTHS);
        Iterator<Entry<Key, Value>> iterator = s.iterator();
        assertTrue(iterator.hasNext()); 
        checkEntry(iterator.next(), "bar", "day", "20080101", "2");
        assertTrue(iterator.hasNext());
        checkEntry(iterator.next(), "foo", "day", "20080101", "2");
        assertTrue(iterator.hasNext());
        checkEntry(iterator.next(), "foo", "day", "20080103", "1");
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testDelete() throws Exception {
    	Connector c = new MockConnector("root");
        c.tableOperations().create("test");
        BatchWriter bw = c.createBatchWriter("test", 10000L, 1000L, 4);
        
        Mutation m1 = new Mutation("r1");
        
        m1.put("cf1", "cq1", 1, "v1");
        
        bw.addMutation(m1);
        bw.flush();
        
        Mutation m2 = new Mutation("r1");
        
        m2.putDelete("cf1", "cq1", 2);
        
        bw.addMutation(m2);
        bw.flush();
        
        Scanner scanner = c.createScanner("test", Constants.NO_AUTHS);
        
        int count = 0;
        for (@SuppressWarnings("unused") Entry<Key, Value> entry : scanner) {
			count++;
		}
        
        assertEquals(0, count);
        
        try{
        	c.tableOperations().create("test_this_$tableName");
        	assertTrue(false);
        
        }
        catch(IllegalArgumentException iae){
        	
        }
    }
    
    @Test
    public void testCMod() throws Exception {
    	//test writing to a table that the is being scanned
    	Connector c = new MockConnector("root");
        c.tableOperations().create("test");
        BatchWriter bw = c.createBatchWriter("test", 10000L, 1000L, 4);
       
        for(int i = 0; i < 10; i++){
        	Mutation m1 = new Mutation("r"+i);
        	m1.put("cf1", "cq1", 1, "v"+i);
        	bw.addMutation(m1);
        }
        
        bw.flush();
        
        int count = 10;
        
        Scanner scanner = c.createScanner("test", Constants.NO_AUTHS);
        for (Entry<Key, Value> entry : scanner) {
        	Key key = entry.getKey();
			Mutation m = new Mutation(key.getRow());
			m.put(key.getColumnFamily().toString(), key.getColumnQualifier().toString(), key.getTimestamp()+1, "v"+(count));
			count++;
			bw.addMutation(m);
		}
        
        bw.flush();
        
        count = 10;
        
        for (Entry<Key, Value> entry : scanner) {
        	assertEquals(entry.getValue().toString(), "v"+(count++));
        }
        
        assertEquals(count, 20);

        try{
        	c.tableOperations().create("test_this_$tableName");
        	assertTrue(false);
        
        }
        catch(IllegalArgumentException iae){
        	
        }
    }
    
    private void checkEntry(Entry<Key, Value> next, String row, String cf, String cq, String value) {
        assertEquals(row, next.getKey().getRow().toString());
        assertEquals(cf, next.getKey().getColumnFamily().toString());
        assertEquals(cq, next.getKey().getColumnQualifier().toString());
        assertEquals(value, next.getValue().toString());
    }
    

}