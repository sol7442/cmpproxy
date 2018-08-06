package com.initech.crossweb.proxy.echo;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.initech.crossweb.proxy.AbstractService;

public class EchoService extends AbstractService {
	
	private ExecutorService execService = Executors.newSingleThreadExecutor();
	
	public EchoService() {
		this.type = "Controller";
		this.name = "Echo";		
	}
	
	@Override
	public void doWork(Socket socket) {
		execService.execute(new EchoWorker(socket));
	}
}
