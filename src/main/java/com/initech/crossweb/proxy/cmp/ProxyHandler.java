package com.initech.crossweb.proxy.cmp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProxyHandler implements Runnable {

	static final Logger logger = LoggerFactory.getLogger(ProxyHandler.class);;

	private Socket read_socket;
	private Socket wirte_socket;
	
	public ProxyHandler(Socket read_socket, Socket write_socket)  {
		this.read_socket = read_socket;
		this.wirte_socket = write_socket;	
	}

	@Override
	public void run() {
		
		try{
			BufferedInputStream in = new BufferedInputStream(this.read_socket.getInputStream());
			OutputStream out = this.wirte_socket.getOutputStream();
			while(true){
				byte[] read_buffer = new byte[4096];
				int read = in.read(read_buffer);
				
				if(read == -1){break;}
				logger.info("Proxy Handler read {} : {} - {}",read, this.read_socket, this.wirte_socket);
				out.write(read_buffer,0,read);
				out.flush();
			}
		}catch(IOException e){
			logger.debug("Proxy Handler  Close  {} - {}",this.read_socket.isConnected(), this.wirte_socket.isConnected());
		}finally{
			logger.debug("Proxy Handler  Close  {} - {}",this.read_socket, this.wirte_socket);
			try{
				if(this.read_socket.isConnected()){
					this.read_socket.close();
				}
			}catch(IOException e){
				logger.error("Proxy Handler Socket Close Error {} {}",this.read_socket, e);
			}
		}
	}


}
