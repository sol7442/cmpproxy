package com.initech.crossweb.proxy.cmp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.initech.crossweb.proxy.conf.Target;

public class ProxyHandler implements Runnable {

	static final Logger logger = LoggerFactory.getLogger(ProxyHandler.class);;
	
	private Socket source_socket;
	private Socket target_socket;
	
	private BufferedInputStream source_in;
	private BufferedInputStream target_in;
	private BufferedOutputStream source_out;
	private BufferedOutputStream target_out;
	
	private Target target;
	public ProxyHandler(Socket socket, Target target) {
		this.source_socket = socket;
		this.target = target;
	}

	@Override
	public void run() {
		
		try {
			logger.debug("proxy recived -- {} : {}", this.target, this.source_socket);
			target_socket = new Socket(this.target.getTargetIp(),this.target.getTargetPort());
			logger.debug("proxy connect -- {} : {}", this.target, this.target_socket);
			
			
			this.source_in = new BufferedInputStream(this.source_socket.getInputStream());
			this.source_out = new BufferedOutputStream(this.source_socket.getOutputStream());
			
			this.target_in = new BufferedInputStream(this.target_socket.getInputStream());
			this.target_out = new BufferedOutputStream(this.target_socket.getOutputStream());
			
			int source_read = 0;
			int target_read = 0;
			
			while(true){
				byte[] source_buffer = new byte[2048];
				source_read = source_in.read(source_buffer);
				if(source_read == -1){
					break;
				}
				
				logger.debug("source read [{}]: {}",source_read, new String(source_buffer,0,source_read));
					
				target_out.write(source_buffer,0,source_read);
				target_out.flush();
				
				
				byte[] target_buffer = new byte[2048];
				target_read = target_in.read(target_buffer);
				if(target_read == -1){
					break;
				}
				
				source_out.write(target_buffer,0,target_read);
				source_out.flush();
			}
		}catch (IOException e) {
			logger.error("Proxy Handler Work Error",e);
		}finally {
			logger.debug("Proxy Work End {}-{}",this.source_socket,this.target_socket);
			try {
				this.source_socket.close();
				this.target_socket.close();
			} catch (IOException e) {
				logger.error("Proxy Handler Close Error",e);
			}
		}
	}
}
