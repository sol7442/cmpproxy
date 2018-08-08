package com.initech.crossweb.proxy.echo;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.initech.crossweb.proxy.AbstractService;

public class EchoService extends AbstractService {
	
	private ExecutorService execService = Executors.newFixedThreadPool(2);
	
	public EchoService() {
		this.type = "Controller";
		this.name = "Echo";		
	}
	
	@Override
	public void doWork(Socket socket) {
		
		
		execService.execute(new EchoHandler(socket));
		
		//execService.execute(new EchoReader(socket, msg_queue));
		//execService.execute(new EchoWriter(socket, msg_queue));
	}
}
