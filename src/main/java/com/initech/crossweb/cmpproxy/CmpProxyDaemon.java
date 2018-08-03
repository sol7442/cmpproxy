package com.initech.crossweb.cmpproxy;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonController;
import org.apache.commons.daemon.DaemonInitException;
import org.apache.commons.daemon.support.DaemonLoader;
import org.apache.commons.daemon.support.DaemonLoader.Context;
import org.apache.commons.daemon.support.DaemonLoader.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmpProxyDaemon implements Daemon {
    static final Logger logger = LoggerFactory.getLogger(CmpProxyDaemon.class);;

    private static final CmpProxyDaemon daemon = new CmpProxyDaemon();
    private CmpProxyServer server = null;
    
	@Override
	public void init(DaemonContext context) throws DaemonInitException, Exception {
		server = new CmpProxyServer();
		String[] args = context.getArguments();
		for(int i=0; i<args.length;i++) {
			System.out.println(args[i]);
		}
		
		
		
	}

	@Override
	public void start() throws Exception {
		server.start();
	}

	@Override
	public void stop() throws Exception {
		server.interrupt();
		destroy();
	}

	@Override
	public void destroy() {
		logger.info("CmpProxyServer destroy");
	}

	static void start(String [] args){
		try {

            /* Create context */
            final Context context = new Context();
            context.setArguments(args);
            
			daemon.init(context);
			daemon.start();
		} catch (DaemonInitException e) {
			logger.error("{}",e);
		} catch (Exception e) {
			logger.error("{}",e);
		}
	}
	static void stop(String [] args){
		try {
			daemon.stop();
		} catch (Exception e) {
			logger.error("{}",e);
		}
    }
	
	public static void main(String[] args) {
		
		if(args == null || args.length == 0) {
			String[] main_args = new String[3]; 
        	main_args[0] = "config.path";
        	main_args[1] = "logger.console"; // logger.file
        	//main_args[2] = "logback.appenders";
        	start(main_args);
		}else {
			if ("start".equals(args[0])) {
	            start(args);
	        } else if ("stop".equals(args[0])) {
	            stop(args);
	        }
		}
    }
}
