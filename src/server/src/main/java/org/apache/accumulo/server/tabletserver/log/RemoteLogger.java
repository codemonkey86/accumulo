package org.apache.accumulo.server.tabletserver.log;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.accumulo.core.conf.AccumuloConfiguration;
import org.apache.accumulo.core.conf.Property;
import org.apache.accumulo.core.data.KeyExtent;
import org.apache.accumulo.core.data.Mutation;
import org.apache.accumulo.core.security.thrift.ThriftSecurityException;
import org.apache.accumulo.core.tabletserver.thrift.LogFile;
import org.apache.accumulo.core.tabletserver.thrift.MutationLogger;
import org.apache.accumulo.core.tabletserver.thrift.NoSuchLogIDException;
import org.apache.accumulo.core.tabletserver.thrift.TabletMutations;
import org.apache.accumulo.core.util.ThriftUtil;
import org.apache.accumulo.server.security.SecurityConstants;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.transport.TTransportException;


/**
 * Wrap a connection to a logger. 
 *
 */
public class RemoteLogger {
    private static Logger log = Logger.getLogger(RemoteLogger.class);
    
    @Override
    public boolean equals(Object obj) {
        // filename is unique
        if (obj == null) return false;
        return getFileName().equals( ((RemoteLogger)obj).getFileName() );
    }

    @Override
    public int hashCode() {
        // filename is unique
        return getFileName().hashCode();
    }

    private final String logger;
    private final LogFile logFile;
    private final UUID tserverSession;
    private MutationLogger.Iface client;
    
    public RemoteLogger(String address, UUID tserverUUID) throws ThriftSecurityException, TException, IOException {
        
    	logger = address;
    	tserverSession = tserverUUID;
        try{
        	client = ThriftUtil.getClient(new MutationLogger.Client.Factory(), address, Property.LOGGER_PORT, Property.TSERV_LOGGER_TIMEOUT, AccumuloConfiguration.getSystemConfiguration());
        	logFile = client.create(null, SecurityConstants.systemCredentials, tserverSession.toString());
        	log.debug("Got new write-ahead log: " + this);
        }catch(ThriftSecurityException tse){
        	ThriftUtil.returnClient(client);
        	client = null;
        	throw tse;
        }catch(TException te){
            ThriftUtil.returnClient(client);
            client = null;
            throw te;
        }
    }

    public RemoteLogger(String address) throws IOException {
    	logger = address;
    	tserverSession = null;
    	logFile = null;
    	try {
    	    client = ThriftUtil.getClient(new MutationLogger.Client.Factory(), address, Property.LOGGER_PORT, Property.TSERV_LOGGER_TIMEOUT, AccumuloConfiguration.getSystemConfiguration());
    	} catch (TTransportException e) {
    	    throw new IOException(e);
    	}
    }
    
    // Fake placeholder for logs used during recovery
    public RemoteLogger(String logger, String filename, UUID tserverUUID) {
        this.client = null;
        this.logger = logger;
        this.logFile = new LogFile(filename, -1);
        this.tserverSession = null;
    }
    
    @Override
    public String toString() {
        return getLogger() + "/" + getFileName();
    }
    
    public String getLogger() {
        return logger;
    }
    
    public String getFileName() {
        return logFile.name;
    }

    public synchronized void close() throws NoSuchLogIDException, TException {
        try{
        	client.close(null, logFile.id);
        }finally{
            TServiceClient c = (TServiceClient)client;
        	c.getInputProtocol().getTransport().close();
        }
    }
    
    public synchronized void defineTablet(int seq, int tid, KeyExtent tablet) 
            throws NoSuchLogIDException, TException {
        client.defineTablet(null, logFile.id, seq, tid, tablet.toThrift());
    }

    public synchronized void log(int seq, int tid, Mutation mutation)
            throws NoSuchLogIDException, TException {
        client.log(null, logFile.id, seq, tid, mutation.toThrift());
    }

    public synchronized void logManyTablets(List<TabletMutations> mutations)
    throws NoSuchLogIDException, TException {
        client.logManyTablets(null, logFile.id, mutations);
    }

    public synchronized void minorCompactionFinished(int seq, int tid, String fqfn)
    throws NoSuchLogIDException, TException {
        client.minorCompactionFinished(null, logFile.id, seq, tid, fqfn);
    }

    public synchronized void minorCompactionStarted(int seq, int tid, String fqfn)
            throws NoSuchLogIDException, TException {
        client.minorCompactionStarted(null, logFile.id, seq, tid, fqfn);
    }
    
    public synchronized long startCopy(String name, String fullyQualifiedFileName, boolean sort) throws ThriftSecurityException, TException {
        return client.startCopy(null, SecurityConstants.systemCredentials, name, fullyQualifiedFileName, sort);
    }
    
    public synchronized List<String> getClosedLogs() throws ThriftSecurityException, TException {
        return client.getClosedLogs(null, SecurityConstants.systemCredentials);
    }
    
    public synchronized void removeFile(List<String> files) throws ThriftSecurityException, TException {
        client.remove(null, SecurityConstants.systemCredentials, files);
    }
    
}
