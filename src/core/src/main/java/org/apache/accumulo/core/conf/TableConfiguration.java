package org.apache.accumulo.core.conf;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.accumulo.core.Constants;
import org.apache.accumulo.core.zookeeper.ZooCache;
import org.apache.accumulo.core.zookeeper.ZooUtil;
import org.apache.log4j.Logger;


public class TableConfiguration extends AccumuloConfiguration
{
    private static final Logger log = Logger.getLogger(TableConfiguration.class);

    private static ZooCache tablePropCache = null;
    private static String instanceId = null;
    private static AccumuloConfiguration parent = null;
    
    private String table = null;
    private Set<ConfigurationObserver> observers;
    
    public TableConfiguration(String instanceId, String table, AccumuloConfiguration parent) {
    	TableConfiguration.instanceId = instanceId;
        this.table = table;
    	TableConfiguration.parent = parent;
    	
        this.observers = Collections.synchronizedSet(new HashSet<ConfigurationObserver>());
    }

    private static ZooCache getTablePropCache()
    {
        if (instanceId == null)
            throw new IllegalStateException("Attempt to get per-table properties without an instanceId");
        if (tablePropCache == null)
            synchronized (TableConfiguration.class) {
                if (tablePropCache == null)
                    tablePropCache = new ZooCache(new TableConfWatcher(instanceId));
            }
        return tablePropCache;
    }

    public void addObserver(ConfigurationObserver co)
    {
        if (table == null) {
            String err = "Attempt to add observer for non-table configuration";
            log.error(err);
            throw new RuntimeException(err);
        }
        iterator();
        observers.add(co);
    }

    public void removeObserver(ConfigurationObserver configObserver)
    {
        if (table == null) {
            String err = "Attempt to remove observer for non-table configuration";
            log.error(err);
            throw new RuntimeException(err);
        }
        observers.remove(configObserver);
    }
    
    public void expireAllObservers() {
    	Collection<ConfigurationObserver> copy = Collections.unmodifiableCollection(observers);
    	for (ConfigurationObserver co : copy)
    		co.sessionExpired();
    }

    public void propertyChanged(String key)
    {
        Collection<ConfigurationObserver> copy = Collections.unmodifiableCollection(observers);
        for (ConfigurationObserver co : copy)
            co.propertyChanged(key);
    }

    public void propertiesChanged(String key)
    {
        Collection<ConfigurationObserver> copy = Collections.unmodifiableCollection(observers);
        for (ConfigurationObserver co : copy)
            co.propertiesChanged();
    }

    public String get(Property property)
    {
    	String key = property.getKey();
        String value = get(key);

        if (value == null || !property.getType().isValidFormat(value)) {
            if (value != null)
                log.error("Using default value for " + key + " due to improperly formatted " + property.getType() + ": " + value);
            value = parent.get(property);
        }
        return value;
    }
    
    private String get(String key) {
        String zPath = ZooUtil.getRoot(instanceId) + Constants.ZTABLES + "/" + table + Constants.ZTABLE_CONF + "/" + key;
        byte[] v = getTablePropCache().get(zPath);
        String value = null;
        if (v != null)
            value = new String(v);
        return value;
    }

    public static void invalidateCache()
    {
        if (tablePropCache != null)
            tablePropCache.clear();
    }
    
    @Override
    public Iterator<Entry<String, String>> iterator()
    {
        TreeMap<String, String> entries = new TreeMap<String, String>();
        
        for (Entry<String, String> parentEntry : parent)
        	entries.put(parentEntry.getKey(), parentEntry.getValue());

        List<String> children = getTablePropCache().getChildren(ZooUtil.getRoot(instanceId) + Constants.ZTABLES + "/" + table + Constants.ZTABLE_CONF);
        if (children != null) {
            for (String child : children) {
                String value = get(child);
                if (child != null && value != null)
                    entries.put(child, value);
            }
        }

        return entries.entrySet().iterator();
    }
}
