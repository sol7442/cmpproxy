package com.initech.crossweb.proxy.echo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoHandler implements Runnable {

	static final Logger logger = LoggerFactory.getLogger(EchoHandler.class);;
	
	private int index = 0;
	private Socket socket;
	private BufferedInputStream in;
    private BufferedOutputStream out;
     
	public EchoHandler(int index, Socket socket) {
		this.socket = socket;
		try {
			this.in = new BufferedInputStream(this.socket.getInputStream());
			this.out =new BufferedOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		try {
			while(true){
				byte[] buffer = new byte[2048];
				int read_len = in.read(buffer);
				if(read_len == -1){
					break;
				}
				
				logger.debug("Cleint recv [{}]: {}",read_len, new String(buffer,0,read_len));
					
				String out_str ="";
				out_str = "Echo 1("+this.index+")["+read_len+"]: " + new String(buffer,0,read_len);
				out.write(out_str.getBytes());
				out.flush();
				
				out_str = "Echo 2("+this.index+")["+read_len+"]: " + new String(buffer,0,read_len);
				out.write(out_str.getBytes());
				out.flush();
				
				logger.debug("Cleint send : {}", out_str);
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
