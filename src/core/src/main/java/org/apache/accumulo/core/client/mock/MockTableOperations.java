package org.apache.accumulo.core.client.mock;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.accumulo.core.Constants;
import org.apache.accumulo.core.client.AccumuloException;
import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.TableExistsException;
import org.apache.accumulo.core.client.TableNotFoundException;
import org.apache.accumulo.core.client.admin.TableOperations;
import org.apache.accumulo.core.client.admin.TimeType;
import org.apache.accumulo.core.data.Range;
import org.apache.accumulo.core.iterators.aggregation.conf.AggregatorConfiguration;
import org.apache.accumulo.core.util.BulkImportHelper.AssignmentStats;
import org.apache.commons.lang.NotImplementedException;
import org.apache.hadoop.io.Text;


public class MockTableOperations extends TableOperations {
    
    final private MockAccumulo acu;
    final private String username;
    
    MockTableOperations(MockAccumulo acu, String username) {
        this.acu = acu;
        this.username = username;
    }

    @Override
    public SortedSet<String> list() {
        return new TreeSet<String>(acu.tables.keySet());
    }

    @Override
    public boolean exists(String tableName) {
        return acu.tables.containsKey(tableName);
    }

    @Override
    public void create(String tableName) throws AccumuloException,
            AccumuloSecurityException, TableExistsException {
    	if (!tableName.matches(Constants.VALID_TABLE_NAME_REGEX)){
			throw new IllegalArgumentException();
    	}
        acu.createTable(username, tableName);
    }

    @Override
    public void create(String tableName, TimeType timeType)
            throws AccumuloException, AccumuloSecurityException, TableExistsException {
    	if (!tableName.matches(Constants.VALID_TABLE_NAME_REGEX)){
			throw new IllegalArgumentException();
    	}
        acu.createTable(username, tableName);
    }

	public void addAggregators(String tableName, List<AggregatorConfiguration> aggregators) throws AccumuloSecurityException, TableNotFoundException, AccumuloException {
		acu.addAggregators(tableName, aggregators);
	}

    
    @Override
    public void addSplits(String tableName, SortedSet<Text> partitionKeys)
            throws TableNotFoundException, AccumuloException, AccumuloSecurityException {
    }

    @Override
    public Collection<Text> getSplits(String tableName) {
        return Collections.emptyList();
    }

    @Override
    public Collection<Text> getSplits(String tableName, int maxSplits) {
        return Collections.emptyList();
    }

    @Override
    public void delete(String tableName) throws AccumuloException,
            AccumuloSecurityException, TableNotFoundException {
        acu.tables.remove(tableName);
    }

    @Override
    public void rename(String oldTableName, String newTableName)
            throws AccumuloSecurityException, TableNotFoundException, AccumuloException,
            TableExistsException {
        MockTable t = acu.tables.remove(oldTableName);
        acu.tables.put(newTableName, t);
    }

    @Override
    public void flush(String tableName) throws AccumuloException, AccumuloSecurityException {
    }

    @Override
    public void setProperty(String tableName, String property, String value)
            throws AccumuloException, AccumuloSecurityException {
        acu.tables.get(tableName).settings.put(property, value);
    }

    @Override
    public void removeProperty(String tableName, String property)
            throws AccumuloException, AccumuloSecurityException {
        acu.tables.get(tableName).settings.remove(property);
    }

    @Override
    public Iterable<Entry<String, String>> getProperties(String tableName)
            throws TableNotFoundException {
        return acu.tables.get(tableName).settings.entrySet();
    }

    @Override
    public void setLocalityGroups(String tableName, Map<String, Set<Text>> groups)
            throws AccumuloException, AccumuloSecurityException, TableNotFoundException {
    }

    @Override
    public Map<String, Set<Text>> getLocalityGroups(String tableName)
            throws AccumuloException, TableNotFoundException {
        return null;
    }

    @Override
    public Set<Range> splitRangeByTablets(String tableName, Range range, int maxSplits)
            throws AccumuloException, AccumuloSecurityException, TableNotFoundException {
        return Collections.singleton(range);
    }

    @Override
    public AssignmentStats importDirectory(String tableName, String dir, String failureDir, int numThreads, int numAssignThreads, boolean disableGC)
            throws IOException, AccumuloException, AccumuloSecurityException {
        throw new NotImplementedException();
    }

    @Override
    public void offline(String tableName) throws AccumuloSecurityException,
            AccumuloException {
    }

    @Override
    public void online(String tableName) throws AccumuloSecurityException,
            AccumuloException {
    }

    @Override
    public void clearLocatorCache(String tableName)
            throws TableNotFoundException {
    }

    @Override
    public Map<String, String> tableIdMap() {
        Map<String, String> result = new HashMap<String, String>();
        for (String table : acu.tables.keySet()) {
            result.put(table, table);
        }
        return result;
    }
}
