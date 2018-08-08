package com.initech.crossweb.cmpproxy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

public class ProxyClient {
	
	@Test
	public void call() {
		
		try {
			Socket socket = new Socket("127.0.0.1",6303);
			socket.setSoLinger(true,1000);
			BufferedInputStream in   = new BufferedInputStream(socket.getInputStream());
			BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
			int count = 3;
			while(count > 0) {
				
				String out_str = "hellow echo server---["+count+"]";
				System.out.println("["+count+"] call : " + out_str);
				out.write(out_str.getBytes());
				out.flush();
				
				byte[] read_buffer = new byte[2048];
				int read_len = in.read(read_buffer);

				System.out.println("["+count+"] recv ["+read_len+"]" + new String(read_buffer,0,read_len));
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
