package com.initech.crossweb.cmpproxy;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmpProxyDaemon implements Daemon {
    static final Logger logger = LoggerFactory.getLogger(CmpProxyDaemon.class);

    private static final CmpProxyDaemon daemon = new CmpProxyDaemon();
    private CmpProxyServer server = null;
    
	@Override
	public void init(DaemonContext context) throws DaemonInitException, Exception {
		server = new CmpProxyServer();
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
			daemon.init(null);
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
        if ("start".equals(args[0])) {
            start(args);
        } else if ("stop".equals(args[0])) {
            stop(args);
        }
    }
}
