package org.apache.accumulo.server.monitor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.accumulo.core.conf.AccumuloConfiguration;
import org.apache.accumulo.core.conf.Property;
import org.apache.accumulo.core.util.Daemon;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.net.SocketNode;
import org.apache.log4j.spi.LoggingEvent;


/**
 * Hijack log4j and capture log events for display. 
 *
 */
public class LogService extends org.apache.log4j.AppenderSkeleton {
    
    private static final Logger log = Logger.getLogger(LogService.class);
    
    /**
     * Read logging events forward to us over the net.
     *
     */
    static class SocketServer implements Runnable {
        private ServerSocket server = null;
        public SocketServer(int port) {
            try {
                server = new ServerSocket(port);
            } catch (IOException io) {
                throw new RuntimeException(io);
            }
        }
        public void run() {
            try {
                while (true) {
                    log.debug("Waiting for log message senders");
                    Socket socket = server.accept();
                    log.debug("Got a new connection");
                    Thread t = new Daemon(new SocketNode(socket, LogManager.getLoggerRepository()));
                    t.start();
                }
            } catch (IOException io) {
                log.error(io, io);
            }
        }
    }
    
    static void startLogListener() {
        try {
            new Daemon(new SocketServer(AccumuloConfiguration.getSystemConfiguration().getPort(Property.MONITOR_LOG4J_PORT))).start();
        } catch (Throwable t) {
            log.info("Unable to listen to cluster-wide ports", t);
        }
    }
    
    static private LogService instance = null;

    synchronized public static LogService getInstance() {
        if (instance == null)
            return new LogService();
        return instance;
    }
    
    private LinkedList<LoggingEvent> events = new LinkedList<LoggingEvent>();
    private int keep = 50;
    
    public LogService() { 
        synchronized(LogService.class) {
            instance = this;
        }
    }
    
    @Override
    synchronized protected void append(LoggingEvent ev) {
        Object application = ev.getMDC("application");
        if (application == null || application.toString().isEmpty())
            return;
        events.add(ev);
        if (events.size() > keep)
            events.removeFirst();
    }
    
    @Override
    public void close() {
        events = null;
    }
    
    @Override
    public synchronized void doAppend(LoggingEvent event) {
        super.doAppend(event);
    }
    
    @Override
    public boolean requiresLayout() {
        return false;
    }

    /** Allow the user to specify the maximum number of log events to keep/display using log4j */
    public int getKeep() {
        return keep;
    }
    
    /** Allow the user to specify the maximum number of log events to keep/display using log4j */
    public void setKeep(int count) {
        this.keep = count;
    }

    synchronized public List<LoggingEvent> getEvents() { return new ArrayList<LoggingEvent>(events); }
    
    synchronized public void clear() {
        events.clear();
    }
  

 }