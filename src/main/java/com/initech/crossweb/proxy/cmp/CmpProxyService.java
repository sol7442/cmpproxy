package com.initech.crossweb.proxy.cmp;


import java.net.Socket;

import com.initech.crossweb.proxy.AbstractService;
import com.initech.crossweb.proxy.IWorker;
import com.initech.crossweb.proxy.conf.Target;

public class CmpProxyService extends AbstractService {

	private Target target;
	public CmpProxyService(String name) {
		this.type = "Proxy";
		this.name = name;
	}
	public void setTarget(Target target) {
		this.target = target;
	}
	
	@Override
	public void doWork(Socket socket) {
		// TODO Auto-generated method stub
	}


}
