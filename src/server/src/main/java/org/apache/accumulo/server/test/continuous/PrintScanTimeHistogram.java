package org.apache.accumulo.server.test.continuous;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PrintScanTimeHistogram {

	public static void main(String[] args) throws Exception {
		Histogram<String> srqHist = new Histogram<String>();
		Histogram<String> fsrHist = new Histogram<String>();
		
		processFile(System.in, srqHist, fsrHist);
		
		System.out.println();
		System.out.println(" *** Single row queries histogram *** ");
		System.out.println();
		srqHist.print();
		
		System.out.println();
		System.out.println(" *** Find start rows histogram *** ");
		System.out.println();
		fsrHist.print();
	}

	private static void processFile(InputStream ins, Histogram<String> srqHist, Histogram<String> fsrHist) throws FileNotFoundException, IOException {
		String line;
		BufferedReader in = new BufferedReader(new InputStreamReader(ins));
		
		while((line = in.readLine()) != null){
			
			try{
				String[] tokens = line.split(" ");

				String type = tokens[0];
				if(type.equals("SRQ")){
					long delta = Long.parseLong(tokens[3]);
					String point = generateHistPoint(delta);
					srqHist.addPoint(point);
				}else if(type.equals("FSR")){
					long delta = Long.parseLong(tokens[3]);
					String point = generateHistPoint(delta);
					fsrHist.addPoint(point);
				}
			}catch(Exception e){
				System.err.println("Failed to process line : "+line);
				e.printStackTrace();
			}
		}
		
		in.close();
	}

	private static String generateHistPoint(long delta) {
		String point;
		
		if(delta/1000.0 < .1){
			point = String.format("%07.2f", delta/1000.0);
			if(point.equals("0000.10")) point = "0000.1x";
		} else if(delta/1000.0 < 1.0) {
			point = String.format("%06.1fx", delta/1000.0);
			if(point.equals("0001.0x")) point = "0001.xx";
		} else {
			point = String.format("%04.0f.xx", delta/1000.0);
		}
		return point;
	}

}
