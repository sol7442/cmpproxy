package com.initech.crossweb.proxy.echo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EchoWorker implements Runnable {

	static final Logger logger = LoggerFactory.getLogger(EchoWorker.class);;
	
	private Socket socket;

	public EchoWorker(Socket socket) {
		this.socket =  socket;
	}

	@Override
	public void run() {

		
		try {
			
			logger.debug("echo call recived -- {}", this.socket);
			
			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
			
			char[] buffer = new char[2048];
			int read_len = reader.read(buffer);
			
			logger.debug("echo - [{}] : {}",read_len, new String(buffer));
			
			writer.write("echo-["+read_len+"] : ");
			writer.write(buffer);
			writer.flush();
			
			writer.close();
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				this.socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
