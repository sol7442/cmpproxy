package com.initech.crossweb.proxy.control;

import java.io.IOException;
import java.util.Set;

import com.initech.crossweb.proxy.AbstractService;

public class Controller {

	private boolean run = true;
	private ControlHandler handler;
	private Set<AbstractService> services;
	
	public Controller(Set<AbstractService> services) {
		this.services = services;
	}
	synchronized public void setRun(boolean run){
		this.run = run;
		if(this.run == false){
			for (AbstractService service : services) {
				try {
					service.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	synchronized public boolean isRun() {
		return this.run;
	}
	public ControlHandler getHandler() {
		return this.handler;
	}
	public void setHandler(ControlHandler handler){
		this.handler = handler;
	}

}
