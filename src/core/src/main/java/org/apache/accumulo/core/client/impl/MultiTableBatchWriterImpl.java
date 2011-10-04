package org.apache.accumulo.core.client.impl;

import java.util.HashMap;

import org.apache.accumulo.core.client.AccumuloException;
import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.BatchWriter;
import org.apache.accumulo.core.client.Instance;
import org.apache.accumulo.core.client.MultiTableBatchWriter;
import org.apache.accumulo.core.client.MutationsRejectedException;
import org.apache.accumulo.core.client.TableNotFoundException;
import org.apache.accumulo.core.data.Mutation;
import org.apache.accumulo.core.security.thrift.AuthInfo;
import org.apache.accumulo.core.util.ArgumentChecker;


public class MultiTableBatchWriterImpl implements MultiTableBatchWriter {
	private boolean closed;
	private class TableBatchWriter implements BatchWriter {

		private String table;

		TableBatchWriter(String table) {
			this.table = table;
		}
		
		@Override
		public void addMutation(Mutation m) throws MutationsRejectedException {
			ArgumentChecker.notNull(m);
			bw.addMutation(table, m);
		}

		@Override
		public void addMutations(Iterable<Mutation> iterable) throws MutationsRejectedException {
			bw.addMutation(table, iterable.iterator());
		}

		@Override
		public void close() {
			throw new UnsupportedOperationException("Must close all tables, can not close an individual table");
		}


		@Override
		public void flush() {
			throw new UnsupportedOperationException("Must flush all tables, can not flush an individual table");
		}
		
	}
	
	private TabletServerBatchWriter bw;
	private HashMap<String, BatchWriter> tableWriters;
    private Instance instance;

	public MultiTableBatchWriterImpl(Instance instance, AuthInfo credentials, long maxMemory, int maxLatency, int maxWriteThreads){
		ArgumentChecker.notNull(instance, credentials);
	    this.instance = instance;
		this.bw = new TabletServerBatchWriter(instance, credentials, maxMemory, maxLatency, maxWriteThreads);
		tableWriters = new HashMap<String, BatchWriter>();
		this.closed = false;
	}
	public boolean isClosed(){
		return this.closed;
	}
	public void close() throws MutationsRejectedException {
		bw.close();
		this.closed=true;
	}

	@Override
	public synchronized BatchWriter getBatchWriter(String tableName)
	throws AccumuloException, AccumuloSecurityException, TableNotFoundException
	{
		ArgumentChecker.notNull(tableName);
		String tableId = Tables.getNameToIdMap(instance).get(tableName);
    	if(tableId == null)
    		throw new TableNotFoundException(tableId, tableName, null);
		BatchWriter tbw = tableWriters.get(tableId);
		if (tbw == null)
		{			
			tbw = new TableBatchWriter(tableId);
			tableWriters.put(tableId, tbw);
		}
		return tbw;
	}

	@Override
	public void flush() throws MutationsRejectedException {
		bw.flush();
	}

}
