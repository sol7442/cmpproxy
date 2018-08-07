package com.initech.crossweb.proxy.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoHandler implements Runnable {

	static final Logger logger = LoggerFactory.getLogger(EchoHandler.class);;
	
	private Socket socket;
	private BufferedReader in;
    private PrintWriter out;
     
	public EchoHandler(Socket socket) {
		this.socket = socket;
		try {
			this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.out = new PrintWriter(this.socket.getOutputStream(),true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		try {
			String input ;
			while( (input = in.readLine()) != null) {
				logger.debug("Cleint : {}",input);
				out.println("Echo : " + input);
			}
		}catch (Exception e) {
			logger.error("echo : {}",e);
		}finally {
			logger.debug("handler end - {}",this.socket.isConnected());
			if(this.socket.isConnected()) {
				try {
					this.socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
