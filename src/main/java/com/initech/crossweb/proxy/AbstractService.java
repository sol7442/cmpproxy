package com.initech.crossweb.proxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class AbstractService extends Thread{

	static final Logger logger = LoggerFactory.getLogger(AbstractService.class);;
	
	
	protected String type;
	protected String name;
	
	private int port;
    private ServerSocket listener;
     

	
	public void open(int port) throws IOException {
		this.port = port;
		this.listener = new ServerSocket(this.port);
		logger.info("{}-{} Listener Open : {}",this.type,this.name,this.listener);
	}
	public void close() throws IOException {
		this.listener.close();
		this.interrupt();
	}
	
	public void run() {
		logger.info("{}-{} Service Star : {}",this.type,this.name,this.listener);
		
		while(!Thread.interrupted()) {
			try {
				Socket socket = this.listener.accept();
				
				logger.debug("socket accepted : {}", socket);
				
				doWork(socket);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	abstract public void doWork(Socket socket);
}
