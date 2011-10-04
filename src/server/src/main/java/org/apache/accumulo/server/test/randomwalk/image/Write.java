package org.apache.accumulo.server.test.randomwalk.image;

import java.security.MessageDigest;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import org.apache.accumulo.core.client.BatchWriter;
import org.apache.accumulo.core.client.MultiTableBatchWriter;
import org.apache.accumulo.core.data.Mutation;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.server.test.randomwalk.State;
import org.apache.accumulo.server.test.randomwalk.Test;
import org.apache.hadoop.io.Text;


public class Write extends Test {

	static final Text UUID_COLUMN_QUALIFIER = new Text("uuid");
	static final Text COUNT_COLUMN_QUALIFIER = new Text("count");
	static final Text SHA1_COLUMN_QUALIFIER = new Text("sha1");
	static final Text IMAGE_COLUMN_QUALIFIER = new Text("image");
	static final Text META_COLUMN_FAMILY = new Text("meta");
	static final Text CONTENT_COLUMN_FAMILY = new Text("content");

	@Override
	public void visit(State state, Properties props) throws Exception {
		
		MultiTableBatchWriter mtbw = state.getMultiTableBatchWriter();
		
		BatchWriter imagesBW = mtbw.getBatchWriter(state.getString("imageTableName"));
		BatchWriter indexBW = mtbw.getBatchWriter(state.getString("indexTableName"));
		
		String uuid = UUID.randomUUID().toString();
		Mutation m = new Mutation(new Text(uuid));
		
		// create a fake image between 4KB and 1MB
		int maxSize = Integer.parseInt(props.getProperty("maxSize"));
		int minSize = Integer.parseInt(props.getProperty("minSize"));

		Random rand = new Random();
		int numBytes = rand.nextInt((maxSize-minSize)) + minSize;
		byte[] imageBytes = new byte[numBytes];
		rand.nextBytes(imageBytes);
		m.put(CONTENT_COLUMN_FAMILY, IMAGE_COLUMN_QUALIFIER, new Value(imageBytes));
		
		// store size
		m.put(META_COLUMN_FAMILY, new Text("size"), new Value(String.format("%d", numBytes).getBytes()));
		
		// store hash
		MessageDigest alg = MessageDigest.getInstance("SHA-1");
		alg.update(imageBytes);
		byte[] hash = alg.digest();
		m.put(META_COLUMN_FAMILY, SHA1_COLUMN_QUALIFIER, new Value(hash));

		// update write counts
		state.set("numWrites", state.getInteger("numWrites")+1);
		Integer totalWrites = state.getInteger("totalWrites")+1;
		state.set("totalWrites", totalWrites);
		
		// set count
		m.put(META_COLUMN_FAMILY, COUNT_COLUMN_QUALIFIER, new Value(String.format("%d", totalWrites).getBytes()));
		
		// add mutation
		imagesBW.addMutation(m);
		
		// now add mutation to index
		Text row = new Text(hash);
		m = new Mutation(row);
		m.put(META_COLUMN_FAMILY, UUID_COLUMN_QUALIFIER, new Value(uuid.getBytes()));
		
		indexBW.addMutation(m);
		
		Text lastRow = (Text) state.get("lastIndexRow");
		if (lastRow.compareTo(row) < 0) {
			state.set("lastIndexRow", new Text(row));
		}
	}
}