package com.initech.crossweb.proxy.control;

import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.initech.crossweb.proxy.AbstractService;

public class ControllerService extends AbstractService{

	private Controller controller;
	public ControllerService(Set<AbstractService> services) {
		this.type = "Controller";
		this.name = "Admin";
		this.controller = new Controller(services);
		
		this.execService = Executors.newFixedThreadPool(2);
	}

	@Override
	public void doWork(Socket socket) {
		execService.execute(new ControlHandler(controller, socket));
	}
}
