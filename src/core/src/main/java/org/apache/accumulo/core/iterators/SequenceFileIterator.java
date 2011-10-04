package org.apache.accumulo.core.iterators;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.accumulo.core.data.ByteSequence;
import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Range;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.file.FileSKVIterator;
import org.apache.accumulo.core.file.map.MySequenceFile;
import org.apache.accumulo.core.file.map.MySequenceFile.Reader;


/**
 * @deprecated since 1.3
 */
public class SequenceFileIterator implements FileSKVIterator {

	private Reader reader;
	private Value top_value;
	private Key top_key;
	private boolean readValue;
	
	public SequenceFileIterator deepCopy(IteratorEnvironment env)
	{
		throw new UnsupportedOperationException("SequenceFileIterator does not yet support cloning");
	}

	@Override
	public void closeDeepCopies() throws IOException {
		throw new UnsupportedOperationException();
	}	
	
	public SequenceFileIterator(MySequenceFile.Reader reader, boolean readValue) throws IOException{
		this.reader = reader;
		this.readValue = readValue;
		
		top_key = new Key();
		
		if(readValue)
			top_value = new Value();
		
		next();
	}
	
	public Key getTopKey() {
		return top_key;
	}

	public Value getTopValue() {
		return top_value;
	}

	public boolean hasTop() {
		return top_key != null;
	}

	public void next() throws IOException {
		boolean valid;
		if(readValue)
			valid = reader.next(top_key, top_value);
		else
			valid = reader.next(top_key);
		
		if(!valid){
			top_key = null;
			top_value = null;
		}
		
	}

	@Override
	public void seek(Range range, Collection<ByteSequence> columnFamilies, boolean inclusive) throws IOException {
		throw new UnsupportedOperationException("seek() not supported");
	}

	public void init(SortedKeyValueIterator<Key, Value> source,
			Map<String, String> options, IteratorEnvironment env) throws IOException {
		throw new UnsupportedOperationException();
		
	}

	@Override
	public void close() throws IOException {
		reader.close();
	}

	@Override
	public Key getFirstKey() throws IOException {
		throw new UnsupportedOperationException("getFirstKey() not supported");
	}
	
	@Override
	public Key getLastKey() throws IOException {
		throw new UnsupportedOperationException("getLastKey() not supported");
	}

	@Override
	public DataInputStream getMetaStore(String name) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setInterruptFlag(AtomicBoolean flag) {
		throw new UnsupportedOperationException();
	}
}
