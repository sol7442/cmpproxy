package com.initech.crossweb.proxy.echo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoWriter implements Runnable{
	static final Logger logger = LoggerFactory.getLogger(EchoReader.class);;
	
	private Socket socket;
	private BlockingQueue<char[]> queue;
	private BufferedWriter writer;

	public EchoWriter(Socket socket, BlockingQueue<char[]> msg_queue) {
		this.socket =  socket;
		this.queue = msg_queue;
		try {
			this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(!Thread.interrupted()) {
			try {
				
				logger.debug("echo - write1 : {}");
				write(this.queue.take());
				logger.debug("echo - write2 : {}");
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void write(char[] data) throws IOException {
		this.writer.write("echo-["+data.length+"] : ");
		this.writer.write(data);
		this.writer.flush();
		logger.debug("echo - flush : {}",new String(data));
	}
}
