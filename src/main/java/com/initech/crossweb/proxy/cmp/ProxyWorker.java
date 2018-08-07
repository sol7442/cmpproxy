package com.initech.crossweb.proxy.cmp;

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

import com.initech.crossweb.proxy.conf.Target;
import com.initech.crossweb.proxy.echo.EchoReader;

public class ProxyWorker implements Runnable {

	static final Logger logger = LoggerFactory.getLogger(EchoReader.class);;
	
	private Socket source_socket;
	private Socket target_socket;
	
	private Target target;
	public ProxyWorker(Socket socket, Target target) {
		this.source_socket = socket;
		this.target = target;
	}

	@Override
	public void run() {
		
		try {
			logger.debug("proxy recived -- {}", this.source_socket);
			target_socket = new Socket(this.target.getTargetIp(),this.target.getTargetPort());
			logger.debug("proxy connect -- {}", this.target_socket);
			
			while(source_socket.isConnected() && target_socket.isConnected()) {
				InputStream source_input   = source_socket.getInputStream();
				OutputStream target_output = source_socket.getOutputStream();

				int write_data = 0;
				while( (write_data = source_input.read()) != -1) {
					target_output.write(write_data);
				}
				target_output.flush();
				
				InputStream target_input   = target_socket.getInputStream();
				OutputStream source_output = source_socket.getOutputStream();
				
				int read_data = 0;
				while( (read_data = target_input.read()) != -1) {
					source_output.write(read_data);
				}
				source_output.flush();
			}
			
			logger.debug("proxy end -- {}{}", this.source_socket.isConnected(), this.target_socket.isConnected());
			
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				this.source_socket.close();
				this.target_socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
