package org.apache.accumulo.server.util;

import java.io.IOException;

import org.apache.accumulo.core.Constants;
import org.apache.accumulo.core.zookeeper.ZooUtil.NodeMissingPolicy;
import org.apache.accumulo.server.client.HdfsZooInstance;
import org.apache.accumulo.server.zookeeper.ZooReaderWriter;
import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;


public class CleanZookeeper {

    private static final Logger log = Logger.getLogger(CleanZookeeper.class);

    /**
     * @param args should contain one element: the address of a zookeeper node
     * @throws IOException error connecting to accumulo or zookeeper
     */
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: " + CleanZookeeper.class.getName() + " hostname[:port]");
            System.exit(1);
        }
        String root = Constants.ZROOT;
        ZooReaderWriter zk = ZooReaderWriter.getInstance();
        try {
            for (String child : zk.getChildren(root) ) {
            	if (Constants.ZINSTANCES.equals("/"+child))
            	{
            		for (String instanceName : zk.getChildren(root+Constants.ZINSTANCES))
            		{
            			String instanceNamePath = root+Constants.ZINSTANCES+"/"+instanceName;
            			byte[] id = zk.getData(instanceNamePath, null);
            			if (id != null && !new String(id).equals(HdfsZooInstance.getInstance().getInstanceID())) {
            			    try {
            			        zk.recursiveDelete(instanceNamePath, NodeMissingPolicy.SKIP);
            			    } catch (KeeperException.NoAuthException ex) {
            			        log.warn("Unable to delete " + instanceNamePath);
            			    }
            			}
            		}
            	}
            	else if (!child.equals(HdfsZooInstance.getInstance().getInstanceID()))
            	{
            	    String path = root + "/" + child;
            	    try {
            	        zk.recursiveDelete(path, NodeMissingPolicy.SKIP);
            	    } catch (KeeperException.NoAuthException ex) {
            	        log.warn("Unable to delete " + path);
            	    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Error Occurred: " + ex);
        }
    }

}