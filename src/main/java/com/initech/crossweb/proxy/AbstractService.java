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
	protected ExecutorService execService;
	
	private int port;
    private ServerSocket listener;
    
    

	
	public void open(int port) throws IOException {
		try {
			this.port = port;
			this.listener = new ServerSocket(this.port);
			logger.info("{}-{} Listener Open : {}",this.type,this.name,this.listener);
		
		}catch(IOException e) {
			logger.error("{}-{} Listener Open : {}",this.type,this.name,e);
			throw e;
		}
	}
	public void close() throws IOException {
		this.execService.shutdownNow();
		this.interrupt();
		this.listener.close();
		logger.info("{}-{} Listener close : {}",this.type,this.name,this.listener);
	}
	
	public void run() {
		logger.info("{}-{} Service Star : {}",this.type,this.name,this.listener);
		
		while(!Thread.interrupted()) {
			try {
				Socket socket = this.listener.accept();
				
				logger.info("socket accepted {} : {}", this.name, socket);
				
				doWork(socket);
				
			} catch (IOException e) {
				logger.info("Service Stoped {} : {}",this.name , e.getMessage());
			}
		}
		logger.info("Service Thread end : {}", this.name);
	}
	abstract public void doWork(Socket socket);
}
