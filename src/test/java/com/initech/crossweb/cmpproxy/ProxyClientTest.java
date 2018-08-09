package com.initech.crossweb.cmpproxy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.initech.crossweb.proxy.echo.EchoClient;

public class ProxyClientTest {
	
	private final String ip = "127.0.0.1";
	private final int port  = 6303;
	
	private ExecutorService exec;

	@Test
	public void call10_10() throws UnknownHostException, IOException {
		 exec = Executors.newFixedThreadPool(10);
		 for(int i=0; i<10; i++) {
			 EchoClient client = new EchoClient(i,1);
			 client.connect(this.ip,this.port);
			 exec.execute(client);
		 }
		 try {
			exec.awaitTermination(60,TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Test End-----");
	}
	
	//@Test
	public void call() {
		
		try {
			Socket socket = new Socket("127.0.0.1",6303);
			socket.setSoLinger(true,1000);
			BufferedInputStream in   = new BufferedInputStream(socket.getInputStream());
			BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
			int count = 100;
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
