package com.sk83rsplace.arkane.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadCreditsTest {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("res/credits.txt"));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			
			while (line != null) {
			    sb.append(line);
			    sb.append("\n");
			    line = br.readLine();
			    
			    if(line != null) {
			    	String[] chunks = line.split(" ");
			    
			    	if(chunks[0].equals("LARGE_HEADER"))
			    		line = line.replace(chunks[0] + " ", "");
			    	else if(chunks[0].equals("MEDIUM_HEADER"))
			    		line = line.replace(chunks[0] + " ", "");
			    
			    	System.out.println(line);
				}
			}
		} finally {
		    br.close();
		}
	}
}
