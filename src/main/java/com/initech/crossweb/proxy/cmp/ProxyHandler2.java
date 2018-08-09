package com.initech.crossweb.proxy.cmp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.initech.crossweb.proxy.conf.Target;

public class ProxyHandler2 implements Runnable {

	static final Logger logger = LoggerFactory.getLogger(ProxyHandler2.class);;
	
	private int index = 0;
	private Socket source_socket;
	private Socket target_socket;
	
	private BufferedInputStream source_in;
	private BufferedInputStream target_in;
	private BufferedOutputStream source_out;
	private BufferedOutputStream target_out;
	
	private final Target target;
	public ProxyHandler2(int index, Socket socket, Target target) {
		this.index = index;
		this.source_socket = socket;
		this.target = target;
	}

	@Override
	public void run() {
		
		try {
			logger.debug("proxy recived {}-- {} : {}",this.index, this.target, this.source_socket);
			target_socket = new Socket(this.target.getTargetIp(),this.target.getTargetPort());
			target_socket.setSoLinger(true,100);
			logger.debug("proxy connect {}-- {} : {}",this.index,  this.target, this.target_socket);
			
			
			this.source_in = new BufferedInputStream(this.source_socket.getInputStream());
			this.source_out = new BufferedOutputStream(this.source_socket.getOutputStream());
			
			this.target_in = new BufferedInputStream(this.target_socket.getInputStream());
			this.target_out = new BufferedOutputStream(this.target_socket.getOutputStream());
			
			int source_read = 0;
			int target_read = 0;
			int remain_read = 0;
			
			while(true){
				byte[] source_buffer = new byte[4096];
				source_read = source_in.read(source_buffer);
				if(source_read == -1){
					break;
				}
				
				logger.debug("source read [{}]: {}",source_read, new String(source_buffer,5,source_read - 1));
					
				target_out.write(source_buffer,0,source_read);
				target_out.flush();
				
				
				byte[] target_buffer = new byte[4096];
				target_read = target_in.read(target_buffer);
				if(target_read == -1){
					break;
				}
				
				logger.debug("target read [{}]: {}",target_read, new String(target_buffer,0,target_read));
				
				source_out.write(target_buffer,0,target_read);
				source_out.flush();
				
				byte[] remain_buffer = new byte[4096];
				remain_read = target_in.read(remain_buffer);
				if(remain_read == -1){
					break;
				}
				
				logger.debug("remain read [{}]: {}",target_read, new String(remain_buffer,0,remain_read));
				
				source_out.write(remain_buffer,0,remain_read);
				source_out.flush();
			}
		}catch (Exception e) {
			logger.error("Proxy Handler Work Error {}",this.index,e);
		}finally {
			logger.debug("Proxy Work End {}--{}-{}",this.index, this.source_socket,this.target_socket);
			try {
				this.source_socket.close();
				this.target_socket.close();
			} catch (IOException e) {
				logger.error("Proxy Handler Close Error",e);
			}
		}
	}
}
