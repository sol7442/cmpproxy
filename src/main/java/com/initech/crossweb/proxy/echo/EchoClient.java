package com.initech.crossweb.proxy.echo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class EchoClient implements Runnable {

	private int index = 0;
	private int loop = 0;
	private Socket socket;
//	private BufferedInputStream in   ;
//	private BufferedOutputStream out ; 
	
	private InputStream in   ;
	private OutputStream out ;
	
	public EchoClient() {
		this.loop = 1;
	}
	public EchoClient(int i,int loop) {
		this.index = i;
		this.loop = loop;
	}
	
	public void connect(String ip, int port) throws UnknownHostException, IOException {
		this.socket = new Socket(ip,port);
		//this.socket.setSoTimeout(1000);
		//this.socket.setSoLinger(true,1000);
		
		this.in   = socket.getInputStream() ;//new BufferedInputStream(socket.getInputStream());
		this.out = socket.getOutputStream() ;//new BufferedOutputStream(socket.getOutputStream());
	}
	
	public void run() {
		try {
			while(this.loop > 0) {
				
				String out_str = "hellow echo server - i am ("+this.index+")---["+this.loop+"]";
				System.out.println("["+this.loop+"] call : " + out_str);
				out.write(out_str.getBytes());
				out.flush();
				
				byte[] read_buffer = new byte[2048];
				int read_len = in.read(read_buffer);

				System.out.println("["+this.loop+"] recv ["+read_len+"]" + new String(read_buffer,0,read_len));
				this.loop--;
				
				Thread.sleep(1000);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			System.out.println("echo clinet end ("+this.index+")("+this.loop+")");
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
