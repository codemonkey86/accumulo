package org.apache.accumulo.server.logger;

import static org.apache.accumulo.server.logger.LogEvents.COMPACTION_FINISH;
import static org.apache.accumulo.server.logger.LogEvents.COMPACTION_START;
import static org.apache.accumulo.server.logger.LogEvents.DEFINE_TABLET;
import static org.apache.accumulo.server.logger.LogEvents.MANY_MUTATIONS;
import static org.apache.accumulo.server.logger.LogEvents.MUTATION;
import static org.apache.accumulo.server.logger.LogEvents.OPEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.accumulo.core.data.ColumnUpdate;
import org.apache.accumulo.core.data.KeyExtent;
import org.apache.accumulo.core.data.Mutation;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.server.logger.LogEvents;
import org.apache.accumulo.server.logger.LogFileKey;
import org.apache.accumulo.server.logger.LogFileValue;
import org.apache.hadoop.io.DataInputBuffer;
import org.apache.hadoop.io.DataOutputBuffer;
import org.apache.hadoop.io.Text;
import org.junit.Test;


public class LogFileTest {
    
    static private void readWrite(LogEvents event,
                                  long seq,
                                  int tid,  
                                  String filename,
                                  KeyExtent tablet,
                                  Mutation[] mutations, 
                                  LogFileKey keyResult,
                                  LogFileValue valueResult) throws IOException
    {
        LogFileKey key = new LogFileKey();
        key.event = event;
        key.seq = seq;
        key.tid = tid;
        key.filename = filename;
        key.tablet = tablet;       
        key.tserverSession = keyResult.tserverSession;
        LogFileValue value = new LogFileValue();
        value.mutations = mutations != null ? mutations : new Mutation[0];
        DataOutputBuffer out = new DataOutputBuffer();
        key.write(out);
        value.write(out);
        out.flush();
        DataInputBuffer in = new DataInputBuffer();
        in.reset(out.getData(), out.size());
        keyResult.readFields(in);
        valueResult.readFields(in);
        assertTrue(key.compareTo(keyResult) == 0);
        assertTrue(Arrays.equals(value.mutations, valueResult.mutations));
        assertTrue(in.read() == -1);
    }
    
    static void assertEqualsMutations(Mutation[] a, Mutation[] b) {
        if (a.length == b.length)
            for (int i = 0; i < a.length; i++) {
                assertEquals(a[i],b[i]);
                Collection<ColumnUpdate> au = a[i].getUpdates();
                Collection<ColumnUpdate> bu = a[i].getUpdates();
                assertEquals(au, bu);
            }
    }

    @Test
    public void testReadFields() throws IOException {
        LogFileKey key = new LogFileKey();
        LogFileValue value = new LogFileValue();
        key.tserverSession = "";
        readWrite(OPEN, -1, -1, null, null, null, key, value);
        assertEquals(key.event, OPEN);
        readWrite(COMPACTION_FINISH, 1, 2, null, null, null, key, value);
        assertEquals(key.event, COMPACTION_FINISH);
        assertEquals(key.seq, 1);
        assertEquals(key.tid, 2);
        readWrite(COMPACTION_START, 3, 4, "some file", null, null, key, value);
        assertEquals(key.event, COMPACTION_START);
        assertEquals(key.seq, 3);
        assertEquals(key.tid, 4);
        assertEquals(key.filename, "some file");
        KeyExtent tablet = new KeyExtent(new Text("table"), new Text("bbbb"), new Text("aaaa"));
        readWrite(DEFINE_TABLET, 5, 6, null, tablet, null, key, value);
        assertEquals(key.event, DEFINE_TABLET);
        assertEquals(key.seq, 5);
        assertEquals(key.tid, 6);
        assertEquals(key.tablet, tablet);
        Mutation m = new Mutation(new Text("row"));
        m.put(new Text("cf"), new Text("cq"), new Value("value".getBytes()));
        readWrite(MUTATION, 7, 8, null, null, new Mutation[]{m}, key, value);
        assertEquals(key.event, MUTATION);
        assertEquals(key.seq, 7);
        assertEquals(key.tid, 8);
        assertEqualsMutations(value.mutations, new Mutation[] {m});
        readWrite(MANY_MUTATIONS, 9, 10, null, null, new Mutation[]{m, m}, key, value);
        assertEquals(key.event, MANY_MUTATIONS);
        assertEquals(key.seq, 9);
        assertEquals(key.tid, 10);
        assertEqualsMutations(value.mutations, new Mutation[] {m,m});
    }


    @Test
    public void testEventType() {
        assertEquals(LogFileKey.eventType(MUTATION), 
                     LogFileKey.eventType(MANY_MUTATIONS));
        assertEquals(LogFileKey.eventType(COMPACTION_START), 
                     LogFileKey.eventType(COMPACTION_FINISH));
        assertTrue(LogFileKey.eventType(DEFINE_TABLET) <
                   LogFileKey.eventType(COMPACTION_FINISH));
        assertTrue(LogFileKey.eventType(COMPACTION_FINISH) <
                   LogFileKey.eventType(MUTATION));
        
    }
 
}