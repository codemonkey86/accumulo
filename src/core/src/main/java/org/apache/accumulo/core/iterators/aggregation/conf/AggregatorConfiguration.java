package org.apache.accumulo.core.iterators.aggregation.conf;

import org.apache.accumulo.core.iterators.conf.PerColumnIteratorConfig;
import org.apache.hadoop.io.Text;


public class AggregatorConfiguration extends PerColumnIteratorConfig {

	public AggregatorConfiguration(Text columnFamily, String aggClassName) {
		super(columnFamily, aggClassName);
	}

	public AggregatorConfiguration(Text columnFamily, Text columnQualifier, String aggClassName) {
		super(columnFamily, columnQualifier, aggClassName);
	}
}
