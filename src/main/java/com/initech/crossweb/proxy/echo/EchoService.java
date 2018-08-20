package com.initech.crossweb.proxy.echo;

import java.net.Socket;
import java.util.concurrent.Executors;

import com.initech.crossweb.proxy.AbstractService;

public class EchoService extends AbstractService {
	
	private int index = 0;
	
	public EchoService() {
		this.type = "Controller";
		this.name = "Echo";		
		this.execService = Executors.newCachedThreadPool();
	}
	
	@Override
	public void doWork(Socket socket) {
		
		
		execService.execute(new EchoHandler(index, socket));
		this.index++;
	}
}
