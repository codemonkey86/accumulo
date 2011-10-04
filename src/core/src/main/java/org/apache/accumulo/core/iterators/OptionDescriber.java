package org.apache.accumulo.core.iterators;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

/*
 * The OptionDescriber interface allows you to set up iterator properties interactively in the accumulo shell.
 * If your iterator and/or filter must implement this interface for the interactive part.  The alternative would
 * be to manually set configuration options with the config -t tableName property=value.  If you go the manual
 * route, be careful to use the correct structure for the property and to set all the properties required for 
 * the iterator.
 * 
 * OptionDescribers will need to implement two methods:
 *      describeOptions() which returns an instance of IteratorOptions
 * 	and validateOptions(Map<String,String> options) which is intended to throw an exception or return false if the options are not acceptable.
 * 
 */
public interface OptionDescriber {
	public static class IteratorOptions {
		public LinkedHashMap<String,String> namedOptions;
		public ArrayList<String> unnamedOptionDescriptions;
		public String name;
		public String description;

		/*
		 * IteratorOptions requires the following:
		 * name is the distinguishing name for the iterator or filter
		 * description is a description of the iterator or filter
		 * namedOptions is a map from specifically named options to their descriptions (null if unused)
		 * 	     e.g., the AgeOffFilter requires a parameter called "ttl", so its namedOptions = Collections.singletonMap("ttl", "time to live (milliseconds)")
		 * unnamedOptionDescriptions is a list of descriptions of additional options that don't have fixed names (null if unused)
		 *       the descriptions are intended to describe a category, and the user will provide parameter names and values in that category
		 *       e.g., the FilteringIterator needs a list of Filters intended to be named by their priority numbers, so its
		 *            unnamedOptionDescriptions = Collections.singletonList("<filterPriorityNumber> <ageoff|regex|filterClass>")
		 */
		public IteratorOptions(String name, String description, Map<String,String> namedOptions, List<String> unnamedOptionDescriptions) {
			this.name = name;
			this.namedOptions = null;
			if (namedOptions!=null)
				this.namedOptions = new LinkedHashMap<String, String>(namedOptions);
			this.unnamedOptionDescriptions = null;
			if (unnamedOptionDescriptions!=null)
				this.unnamedOptionDescriptions = new ArrayList<String>(unnamedOptionDescriptions);
			this.description = description;
		}

		public Map<String, String> getNamedOptions() {
			return namedOptions;
		}

		public List<String> getUnnamedOptionDescriptions() {
			return unnamedOptionDescriptions;
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}

		public void setNamedOptions(Map<String, String> namedOptions) {
			this.namedOptions = new LinkedHashMap<String, String>(namedOptions);
		}

		public void setUnnamedOptionDescriptions(List<String> unnamedOptionDescriptions) {
			this.unnamedOptionDescriptions = new ArrayList<String>(unnamedOptionDescriptions);
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public void addNamedOption(String name, String description) {
			if (namedOptions==null) namedOptions = new LinkedHashMap<String, String>();
			namedOptions.put(name, description);
		}

		public void addUnnamedOption(String description) {
			if (unnamedOptionDescriptions==null) unnamedOptionDescriptions = new ArrayList<String>();
			unnamedOptionDescriptions.add(description);
		}
	}

	public IteratorOptions describeOptions();
	public boolean validateOptions(Map<String,String> options);
}