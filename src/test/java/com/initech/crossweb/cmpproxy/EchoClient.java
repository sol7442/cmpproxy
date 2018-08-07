package com.initech.crossweb.cmpproxy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

public class EchoClient {

	
	@Test
	public void call() {
		
		try {
			Socket socket = new Socket("127.0.0.1",6200);
			socket.setSoLinger(true,1000);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    PrintWriter out   = new PrintWriter(socket.getOutputStream(),true); 
			
			int count = 100;
			while(count > 0) {
				
				out.println("hellow echo server---["+count+"]");
				String line = in.readLine();
				System.out.println(line);
				
				count--;
			}
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		
	}
}
