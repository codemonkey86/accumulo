package org.apache.accumulo.server.test.functional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.accumulo.core.Constants;
import org.apache.accumulo.core.client.BatchWriter;
import org.apache.accumulo.core.client.Scanner;
import org.apache.accumulo.core.conf.Property;
import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Mutation;
import org.apache.accumulo.core.data.Range;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.util.UtilWaitThread;
import org.apache.hadoop.io.Text;


public class DeleteEverythingTest extends FunctionalTest {

	@Override
	public void cleanup() throws Exception {}

	@Override
	public Map<String, String> getInitialConfig() {
		Map<String, String> props = new HashMap<String, String>();
		props.put(Property.TSERV_MAJC_DELAY.getKey(), "1s");
		return props;
	}

	@Override
	public List<TableSetup> getTablesToCreate() {
		return Collections.singletonList(new TableSetup("de"));
	}

	@Override
	public void run() throws Exception {
		BatchWriter bw = getConnector().createBatchWriter("de", 1000000, 60000l, 1);
		Mutation m = new Mutation(new Text("foo"));
		m.put(new Text("bar"), new Text("1910"), new Value("5".getBytes()));
		bw.addMutation(m);
		bw.flush();
		
		getConnector().tableOperations().flush("de", null, null, true);
		
		checkMapFiles("de", 1, 1, 1, 1);
		
		m = new Mutation(new Text("foo"));
		m.putDelete(new Text("bar"), new Text("1910"));
		bw.addMutation(m);
		bw.flush();
		
		Scanner scanner = getConnector().createScanner("de", Constants.NO_AUTHS);
		scanner.setRange(new Range());
		
		int count = 0;
		for (@SuppressWarnings("unused") Entry<Key, Value> entry : scanner) {
			count++;
		}
		
		if(count != 0) throw new Exception("count == "+count);
		
		getConnector().tableOperations().flush("de", null, null, true);
		
		getConnector().tableOperations().setProperty("de", Property.TABLE_MAJC_RATIO.getKey(), "1.0");
		UtilWaitThread.sleep(4000);
		
		checkMapFiles("de", 1, 1, 0, 0);
		
		bw.close();
		
		count = 0;
		for (@SuppressWarnings("unused") Entry<Key, Value> entry : scanner) {
			count++;
		}
		
		if(count != 0) throw new Exception("count == "+count);
	}
}