package com.initech.crossweb.proxy.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ControlHandler implements Runnable {
	static final Logger logger = LoggerFactory.getLogger(ControlHandler.class);;

	private Socket socket;
    
    
	public ControlHandler(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			logger.debug("ControlHandler start ");
			
			PrintWriter writer = new PrintWriter(this.socket.getOutputStream(),true);
			
			while(true) {
				writer.println("start");
				logger.debug("Controller write {}", new Date());
				Thread.sleep(5000);
			}
		}catch(Exception e){
			if (e instanceof InterruptedException) {
				//ignore
			}else{
				logger.error("{}",e);
			}
		}finally{
			try {
				this.socket.close();
			} catch (IOException e) {
				logger.error("{}",e);
			}
		}
	}
}
