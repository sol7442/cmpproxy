package com.initech.crossweb.proxy.control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ControlHandler implements Runnable {
	static final Logger logger = LoggerFactory.getLogger(ControlHandler.class);;

	private Controller controller;
	private Socket socket;
    
	private InputStreamReader input;
	private OutputStreamWriter output;
    
	public ControlHandler(Controller controller, Socket socket) {
		this.socket = socket;
		this.controller = controller;
	}
	
	@Override
	public void run() {
		try {
			logger.debug("ControlHandler start ");
			
			input = new InputStreamReader(this.socket.getInputStream());
			output = new OutputStreamWriter(this.socket.getOutputStream());
			
			char[] buffer = new char[128];
			int read = input.read(buffer);
			String command = new String(buffer,0,read);
			logger.debug("Controller command : {} ", command);
			
			if("start".equals(command)){
				
				this.controller.setHandler(this);
				while(this.controller.isRun()){
					output.write("start");
					output.flush();
					Thread.sleep(5000);
				}
			}else{
				this.controller.getHandler().stop();
				Thread.sleep(500);
				controller.setRun(false);
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

	synchronized private void stop() {
		try {
			this.output.write("stop");
			this.output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
