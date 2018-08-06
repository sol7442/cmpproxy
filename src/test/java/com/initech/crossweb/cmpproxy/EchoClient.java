package com.initech.crossweb.cmpproxy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

public class EchoClient {

	
	@Test
	public void call() {
		
		try {
			Socket socket = new Socket("127.0.0.1",6200);
			
			
			OutputStream output = socket.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
			
			writer.write("hellow echo server---");
			writer.flush();

			
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			String line = "";
			while( (line = reader.readLine()) != null) {
				System.out.println(line);
			}
			
			writer.close();
			reader.close();
			socket.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		
	}
}
