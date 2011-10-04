package org.apache.accumulo.core.client.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.accumulo.core.Constants;
import org.apache.accumulo.core.client.AccumuloException;
import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.Instance;
import org.apache.accumulo.core.client.TableNotFoundException;
import org.apache.accumulo.core.data.KeyExtent;
import org.apache.accumulo.core.data.Mutation;
import org.apache.accumulo.core.data.Range;
import org.apache.accumulo.core.util.UtilWaitThread;
import org.apache.hadoop.io.Text;


public class RootTabletLocator extends TabletLocator {

	private Instance instance;
	
	RootTabletLocator(Instance instance){
		this.instance = instance;
	}
	
	@Override
	public void binMutations(List<Mutation> mutations,
			Map<String, TabletServerMutations> binnedMutations,
			List<Mutation> failures) throws AccumuloException,
			AccumuloSecurityException, TableNotFoundException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Range> binRanges(List<Range> ranges, Map<String, Map<KeyExtent, List<Range>>> binnedRanges)
			throws AccumuloException, AccumuloSecurityException, TableNotFoundException {
		
		String rootTabletLocation = instance.getRootTabletLocation();
		if (rootTabletLocation != null) {
		    for (Range range : ranges) {
		        TabletLocatorImpl.addRange(binnedRanges, rootTabletLocation, Constants.ROOT_TABLET_EXTENT, range);
		    }
		}
		return Collections.emptyList();
	}

	@Override
	public void invalidateCache(KeyExtent failedExtent) {}

	@Override
	public void invalidateCache(Collection<KeyExtent> keySet) {}

	@Override
	public void invalidateCache(String server) {}
	
	@Override
	public void invalidateCache() {}

	@Override
	public TabletLocation locateTablet(Text row, boolean skipRow, boolean retry) throws AccumuloException, AccumuloSecurityException, TableNotFoundException {
		if(skipRow){
			row = new Text(row);
			row.append(new byte[]{0}, 0, 1);
		}
		if(!Constants.ROOT_TABLET_EXTENT.contains(row)){
			throw new AccumuloException("Tried to locate row out side of root tablet "+row);
		}
		String location = instance.getRootTabletLocation();
		// Always retry when finding the root tablet
		while (retry && location == null) {
		    UtilWaitThread.sleep(500);
		    location = instance.getRootTabletLocation();
		}
		if (location != null)
		    return new TabletLocation(Constants.ROOT_TABLET_EXTENT, location);
		return null;
	}
	
}