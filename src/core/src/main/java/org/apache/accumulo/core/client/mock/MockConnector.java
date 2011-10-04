package org.apache.accumulo.core.client.mock;

import org.apache.accumulo.core.client.BatchDeleter;
import org.apache.accumulo.core.client.BatchScanner;
import org.apache.accumulo.core.client.BatchWriter;
import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.Instance;
import org.apache.accumulo.core.client.MultiTableBatchWriter;
import org.apache.accumulo.core.client.Scanner;
import org.apache.accumulo.core.client.TableNotFoundException;
import org.apache.accumulo.core.client.admin.InstanceOperations;
import org.apache.accumulo.core.client.admin.SecurityOperations;
import org.apache.accumulo.core.client.admin.TableOperations;
import org.apache.accumulo.core.security.Authorizations;

public class MockConnector extends Connector {
    
    String username;
    private final MockAccumulo acu;
    
    MockConnector(String username) {
    	this(username, new MockAccumulo());
    }
    
    @SuppressWarnings("deprecation")
    MockConnector(String username, MockAccumulo acu) {
    	this.username = username;
    	this.acu = acu;
    }

    @Override
    public BatchScanner createBatchScanner(String tableName, Authorizations authorizations, int numQueryThreads)
            throws TableNotFoundException {
        return acu.createBatchScanner(tableName, authorizations);
    }

    @Override
    public BatchDeleter createBatchDeleter(String tableName, Authorizations authorizations, int numQueryThreads, long maxMemory, long maxLatency, int maxWriteThreads)
            throws TableNotFoundException {
        return null;
    }

    @Override
    public BatchWriter createBatchWriter(String tableName, long maxMemory, long maxLatency, int maxWriteThreads)
            throws TableNotFoundException {
        return new MockBatchWriter(acu, tableName);
    }

    @Override
    public MultiTableBatchWriter createMultiTableBatchWriter(long maxMemory, int maxLatency, int maxWriteThreads) {
        return new MockMultiTableBatchWriter(acu);
    }

    @Override
    public Scanner createScanner(String tableName, Authorizations authorizations)
            throws TableNotFoundException {
        MockTable table = acu.tables.get(tableName);
        if (table == null)
            throw new TableNotFoundException(tableName, tableName, "no such table");
        return new MockScanner(table, authorizations);
    }

    @Override
    public Instance getInstance() {
        return new MockInstance();
    }

    @Override
    public String whoami() {
        return username;
    }

    @Override
    public TableOperations tableOperations() {
        return new MockTableOperations(acu, username);
    }

    @Override
    public SecurityOperations securityOperations() {
        return new MockSecurityOperations(acu);
    }

    @Override
    public InstanceOperations instanceOperations() {
        return null;
    }

}