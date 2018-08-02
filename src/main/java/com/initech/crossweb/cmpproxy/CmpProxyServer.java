package com.initech.crossweb.cmpproxy;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmpProxyServer extends Thread{
    static final Logger logger = LoggerFactory.getLogger(CmpProxyServer.class);

	
	public void run(){
		try {
			int count = 0;
			while(!Thread.currentThread().isInterrupted()){
				logger.debug("----{}:{}",count, new Date());
				count++;
				Thread.sleep(1000);
			}
		 } catch (InterruptedException e) {
			 e.printStackTrace();
		 } finally{
			 logger.debug("--CmpProxy-{}","stop");
		 }
	}
}
