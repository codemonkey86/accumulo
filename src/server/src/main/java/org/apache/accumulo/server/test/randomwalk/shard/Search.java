package org.apache.accumulo.server.test.randomwalk.shard;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.accumulo.core.Constants;
import org.apache.accumulo.core.client.BatchScanner;
import org.apache.accumulo.core.client.Scanner;
import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Range;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.iterators.IntersectingIterator;
import org.apache.accumulo.server.test.randomwalk.State;
import org.apache.accumulo.server.test.randomwalk.Test;
import org.apache.hadoop.io.Text;


public class Search extends Test {

	@Override
	public void visit(State state, Properties props) throws Exception {
		String indexTableName = (String)state.get("indexTableName");
		String dataTableName = (String)state.get("docTableName");
		
		Random rand = (Random) state.get("rand");
		
		Entry<Key, Value> entry = findRandomDocument(state, dataTableName, rand);
		if(entry == null)
			return;
		
		Text docID = entry.getKey().getRow();
		String doc = entry.getValue().toString();
		
		String[] tokens = doc.split("\\W+");
		int numSearchTerms = rand.nextInt(6);
		if(numSearchTerms < 2)
			numSearchTerms = 2;
		
		HashSet<String> searchTerms = new HashSet<String>();
		while(searchTerms.size() < numSearchTerms)
			searchTerms.add(tokens[rand.nextInt(tokens.length)]);
		
		Text columns[] = new Text[searchTerms.size()];
		int index = 0;
		for (String term : searchTerms) {
			columns[index++] = new Text(term);
		}
		
		log.debug("Looking up terms "+searchTerms+" expect to find "+docID);
		
		BatchScanner bs = state.getConnector().createBatchScanner(indexTableName, Constants.NO_AUTHS, 10);
		
		bs.setScanIterators(20, IntersectingIterator.class.getName(), "ii");
		bs.setScanIteratorOption("ii", IntersectingIterator.columnFamiliesOptionName, IntersectingIterator.encodeColumns(columns));
		bs.setRanges(Collections.singleton(new Range()));
			
		boolean sawDocID = false;
		
		for (Entry<Key, Value> entry2 : bs) {
			if(entry2.getKey().getColumnQualifier().equals(docID)){
				sawDocID = true;
				//TODO breaking w/o reading all data causes batch reader to spew exceptions
				//break;
			}
		}
		
		bs.close();
		
		if(!sawDocID)
			throw new Exception("Did not see doc "+docID+" in index.  terms:"+searchTerms+" "+indexTableName+" "+dataTableName);
	}

	static Entry<Key, Value> findRandomDocument(State state, String dataTableName, Random rand) throws Exception {
		Scanner scanner = state.getConnector().createScanner(dataTableName, Constants.NO_AUTHS);
		scanner.setBatchSize(1);
		scanner.setRange(new Range(Integer.toString(rand.nextInt(0xfffffff), 16), null));
		
		Iterator<Entry<Key, Value>> iter = scanner.iterator();
		if(!iter.hasNext())
			return null;
		
		return iter.next();
	}
	
}
