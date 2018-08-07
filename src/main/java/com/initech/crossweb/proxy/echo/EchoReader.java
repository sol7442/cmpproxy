package com.initech.crossweb.proxy.echo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EchoReader implements Runnable {

	static final Logger logger = LoggerFactory.getLogger(EchoReader.class);;
	
	private Socket socket;
	private BlockingQueue<char[]> queue;
	private BufferedReader reader = null;
	
	
	public EchoReader(Socket socket, BlockingQueue<char[]> msg_queue) {
		this.socket =  socket;
		this.queue = msg_queue;
		try {
			this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			
			logger.debug("echo call recived -- {}", this.socket);
			
			while(!Thread.interrupted()) {
				try {
					
					logger.debug("echo server read 1-- {}", this.socket);
					this.queue.put(read());
					logger.debug("echo server read 2-- {}", this.socket);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {

				this.reader.close();
				this.socket.close();
				
			} catch (IOException e) {
				System.err.println("-----");
				e.printStackTrace();
			}
		}
	}

	private char[] read() throws IOException {
		char[] buffer = new char[2048];
		int read_len = this.reader.read(buffer);
		logger.debug("echo - [{}] : {}",read_len, new String(buffer));
		return buffer;
	}
}
