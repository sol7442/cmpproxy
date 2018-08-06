package com.initech.crossweb.proxy;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.apache.commons.daemon.support.DaemonLoader.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyManager implements Daemon{
	static final Logger logger = LoggerFactory.getLogger(ProxyManager.class);;
	
	private static final ProxyManager manager = new ProxyManager();
	
	@Override
	public void init(DaemonContext context) throws DaemonInitException, Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	static void start(String [] args){
		try {

            /* Create context */
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
		
	}
}
