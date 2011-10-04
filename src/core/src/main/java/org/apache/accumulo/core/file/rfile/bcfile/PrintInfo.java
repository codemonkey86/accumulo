package org.apache.accumulo.core.file.rfile.bcfile;

import java.io.PrintStream;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.accumulo.core.conf.AccumuloConfiguration;
import org.apache.accumulo.core.file.FileUtil;
import org.apache.accumulo.core.file.rfile.bcfile.BCFile.MetaIndexEntry;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class PrintInfo {
	public static void printMetaBlockInfo(Configuration conf, FileSystem fs, Path path) throws Exception {
		FSDataInputStream fsin = fs.open(path);
		BCFile.Reader bcfr = new BCFile.Reader(fsin, fs.getFileStatus(path).getLen(), conf);
		
		Set<Entry<String, MetaIndexEntry>> es = bcfr.metaIndex.index.entrySet();
		
		for (Entry<String, MetaIndexEntry> entry : es) {
			PrintStream out = System.out;
			out.println("Meta block     : "+entry.getKey());
			out.println("      Raw size             : "+String.format("%,d",entry.getValue().getRegion().getRawSize())+" bytes");
			out.println("      Compressed size      : "+String.format("%,d",entry.getValue().getRegion().getCompressedSize())+" bytes");
			out.println("      Compression type     : "+entry.getValue().getCompressionAlgorithm().getName());
			out.println();
		}
	}
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		@SuppressWarnings("deprecation")
        FileSystem fs = FileUtil.getFileSystem(conf, AccumuloConfiguration.getSiteConfiguration());
		Path path = new Path(args[0]);
		printMetaBlockInfo(conf, fs, path);
	}
}