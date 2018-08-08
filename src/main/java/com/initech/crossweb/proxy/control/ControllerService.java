package com.initech.crossweb.proxy.control;

import java.net.Socket;

import com.initech.crossweb.proxy.AbstractService;

public class ControllerService extends AbstractService{

	public ControllerService() {
		this.type = "Controller";
		this.name = "Admin";
	}

	@Override
	public void doWork(Socket socket) {
		// TODO Auto-generated method stub
	}
}
