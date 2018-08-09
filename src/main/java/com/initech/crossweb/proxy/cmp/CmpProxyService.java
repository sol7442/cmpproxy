package com.initech.crossweb.proxy.cmp;


import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.initech.crossweb.proxy.AbstractService;
import com.initech.crossweb.proxy.conf.Target;

public class CmpProxyService extends AbstractService {

	static final Logger logger = LoggerFactory.getLogger(CmpProxyService.class);;

	
	private ExecutorService execService = Executors.newCachedThreadPool();
	
	private Target target;
	public CmpProxyService(String name) {
		this.type = "Proxy";
		this.name = name;
	}
	public void setTarget(Target target) {
		this.target      = target;
	}
	
	@Override
	public void doWork(Socket source_socket) {
		try {
			/**[alpa version]*********/
			Socket target_socket = connectTarget();
			execService.execute(new ProxyHandler(source_socket, target_socket));
			execService.execute(new ProxyHandler(target_socket,source_socket));
			
			/**[beta version]********************/
			//execService.execute(new ProxyHandler2(0,source_socket,this.target));
			
		} catch (Exception e) {
			logger.error("Proxy Work Error {}",e);
		} 
	}
	private  Socket connectTarget() throws UnknownHostException, IOException {
		Socket target_socket = new Socket(this.target.getTargetIp(),this.target.getTargetPort());
		target_socket.setTcpNoDelay(true);
		target_socket.setSoLinger(true,100);
		target_socket.setSoTimeout(3000);
		return target_socket;
	}


}
