package com.initech.crossweb.proxy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.apache.commons.daemon.support.DaemonLoader.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.initech.crossweb.proxy.conf.Configuration;

public class ProxyManager implements Daemon{
	static final Logger logger = LoggerFactory.getLogger(ProxyManager.class);;
	
	private static final ProxyManager manager = new ProxyManager();
    private Configuration config;
    private String command;
    
	@Override
	public void init(DaemonContext context) throws DaemonInitException, Exception {
		String[] args = context.getArguments();
		
		String config_file = System.getProperty("conf.path") + "/config.json";
		this.config = Configuration.load(config_file);
		this.command = args[0];
	}

	@Override
	public void start() throws Exception {
		
		if("start".equals(this.command)){
			boolean is_stop = false;
			do{
				logger.info("start deamon");
				is_stop = wait_for_stop(wait_for_start());
				logger.info("deamon stoped : {} ", is_stop);
			}while(!is_stop);
			
		}else if("stop".equals(this.command)){
			stop();
		}else{
			System.out.println("command need...");
		}
		
		
//		Socket socket = new Socket("127.0.0.1",this.config.getAdminPort());
//		BufferedReader reader = new BufferedReader(new InputStreamReader( socket.getInputStream()));
//		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//
//		writer.write("start");
//		writer.flush();
//		
//		String line;
//		while((line = reader.readLine()) != null){
//			logger.debug("state : {} : {} ",line, new Date());
//			if("stop".equals(line)){
//				break;
//			}
//		}
//		
//		socket.close();
	}

	@Override
	public void stop() throws Exception {
		Socket socket = new Socket("127.0.0.1",this.config.getAdminPort());
		BufferedReader reader = new BufferedReader(new InputStreamReader( socket.getInputStream()));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		
		writer.write("stop");
		writer.flush();
		
		String line;
		while((line = reader.readLine()) != null){
			logger.debug("state : {} : {} ",line, new Date());
		}
		socket.close();
	}

	
	private Socket wait_for_start(){
		Socket socket = null;
		try{
			while(true){
				try {
					socket = new Socket("127.0.0.1",this.config.getAdminPort());
					Thread.sleep(500);
					break;
				} catch (ConnectException e) {
					logger.debug("Daemon is not started .. {}", e.getMessage());
				} 
			}
		}
		catch (UnknownHostException  | InterruptedException e) {
			logger.error("{}",e);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("connect : {} ", socket);
		return socket;
	}
	private boolean wait_for_stop(Socket socket){
		boolean is_stop = false;
		try {
			
			OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
			out.write("start");
			out.flush();
			
			InputStreamReader input = new InputStreamReader( socket.getInputStream());
			
			while(true){
				char[] buffer = new char[128];
				int read = input.read(buffer);
				if(read == -1){
					break;
				}
				
				String status = new String(buffer,0,read);
				logger.debug("Deamon status : {}", status);
				if("stop".equals(status)){
					is_stop = true;
					break;
				}
			}
			
		} catch (IOException e) {
			logger.info("{}",e);
		}
		return is_stop;
	}
	
	@Override
	public void destroy() {
	}
	
	static void start(String [] args){
		try {
            final Context context = new Context();
            context.setArguments(args);
            
            manager.init(context);
            manager.start();
		} catch (DaemonInitException e) {
			logger.error("{}",e);
		} catch (Exception e) {
			logger.error("{}",e);
		}
	}
	static void stop(String [] args){
		try {
			manager.stop();
		} catch (Exception e) {
			logger.error("{}",e);
		}
    }
	
	public static void main(String[] args) {
        manager.start(args);
	}
}
