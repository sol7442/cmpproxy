package com.initech.crossweb.proxy.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WatchDoc implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(WatchDoc.class);
	
	private Socket socket ;
	public WatchDoc(Socket socket) {
		this.socket = socket;
	}
	@Override
	public void run() {
		try {
			logger.debug("Start Watch Doc : {} ", new Date());
			BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			String line;
			while((line = reader.readLine()) != null) {
				logger.debug("Watch : {} : {}", line , new Date());
			}
			
		} catch (IOException e) {
			logger.info("Watch Doc Catched {}", e.getMessage());
		}
	}

}
