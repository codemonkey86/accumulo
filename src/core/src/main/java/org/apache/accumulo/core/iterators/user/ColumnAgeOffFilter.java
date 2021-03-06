/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.accumulo.core.iterators.user;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.iterators.Filter;
import org.apache.accumulo.core.iterators.IteratorEnvironment;
import org.apache.accumulo.core.iterators.OptionDescriber;
import org.apache.accumulo.core.iterators.SortedKeyValueIterator;
import org.apache.accumulo.core.iterators.conf.ColumnToClassMapping;
import org.apache.accumulo.core.iterators.conf.PerColumnIteratorConfig;

@SuppressWarnings("deprecation")
public class ColumnAgeOffFilter extends Filter implements OptionDescriber {
  
  public ColumnAgeOffFilter() {}
  
  public ColumnAgeOffFilter(SortedKeyValueIterator<Key,Value> iterator, TTLSet ttls, long currentTime) {
    super(iterator);
    this.ttls = ttls;
    this.currentTime = currentTime;
  }
  
  public static class TTLSet extends ColumnToClassMapping<Long> {
    public TTLSet(Map<String,String> objectStrings) {
      super();
      
      for (Entry<String,String> entry : objectStrings.entrySet()) {
        String column = entry.getKey();
        String ttl = entry.getValue();
        Long l = Long.parseLong(ttl);
        
        PerColumnIteratorConfig ac = PerColumnIteratorConfig.decodeColumns(column, ttl);
        
        if (ac.getColumnQualifier() == null) {
          addObject(ac.getColumnFamily(), l);
        } else {
          addObject(ac.getColumnFamily(), ac.getColumnQualifier(), l);
        }
      }
    }
  }
  
  TTLSet ttls;
  long currentTime = 0;
  
  @Override
  public boolean accept(Key k, Value v) {
    Long threshold = ttls.getObject(k);
    if (threshold == null) return true;
    if (currentTime - k.getTimestamp() > threshold) return false;
    return true;
  }
  
  @Override
  public void init(SortedKeyValueIterator<Key,Value> source, Map<String,String> options, IteratorEnvironment env) throws IOException {
    super.init(source, options, env);
    this.ttls = new TTLSet(options);
    currentTime = System.currentTimeMillis();
  }
  
  @Override
  public SortedKeyValueIterator<Key,Value> deepCopy(IteratorEnvironment env) {
    return new ColumnAgeOffFilter(getSource(), ttls, currentTime);
  }
  
  public void overrideCurrentTime(long ts) {
    this.currentTime = ts;
  }
  
  @Override
  public IteratorOptions describeOptions() {
    IteratorOptions io = super.describeOptions();
    io.setName("colageoff");
    io.setDescription("ColumnAgeOffFilter ages off columns at different rates given a time to live in milliseconds for each column");
    io.addUnnamedOption("<columnName> <Long>");
    return io;
  }
  
  @Override
  public boolean validateOptions(Map<String,String> options) {
    this.ttls = new TTLSet(options);
    return true;
  }
}
