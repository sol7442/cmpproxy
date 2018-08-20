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
import com.initech.crossweb.proxy.control.WatchDoc;

public class ProxyManager implements Daemon{
	static final Logger logger = LoggerFactory.getLogger(ProxyManager.class);;
	
	private static final ProxyManager manager = new ProxyManager();
    private Configuration config;
    
	@Override
	public void init(DaemonContext context) throws DaemonInitException, Exception {
		//String[] args = context.getArguments();
		
		String config_file = System.getProperty("conf.path") + "/config.json";
		this.config = Configuration.load(config_file);
	}

	@Override
	public void start() throws Exception {
		try {
			while(true) {
				run_proc();
				Socket socket = wait_for_start();
				start_watch_doc(socket);
			}
		}catch(Exception e) {
			logger.error("{}",e);
		}
		
		logger.info("Manager End.....");
	}

	private void start_watch_doc(Socket socket) {
		new WatchDoc(socket).run();
	}


	private void run_proc() throws IOException {
		Process proc = Runtime.getRuntime().exec(config.getRunScript());
		logger.info("daemon run : {}", proc);
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
					Thread.sleep(1000);
					break;
				} catch (ConnectException e) {
					logger.debug("Daemon is not started .. {}", e.getMessage());
				} 
			}
		}
		catch (InterruptedException e) {
			logger.error("{}",e);
		}
		catch (UnknownHostException e) {
			logger.error("{}",e);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("connect : {} ", socket);
		return socket;
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
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
        manager.start(args);
	}
}
