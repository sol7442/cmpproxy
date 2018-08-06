package com.initech.crossweb.proxy.conf;

import java.io.Serializable;

public class Target implements Serializable {
	private static final long serialVersionUID = -1811285818051303804L;

	private String targetIp;
	private int targetPort;
	private int proxyPort;
	public String getTargetIp() {
		return targetIp;
	}
	public void setTargetIp(String targetIp) {
		this.targetIp = targetIp;
	}
	public int getTargetPort() {
		return targetPort;
	}
	public void setTargetPort(int targetPort) {
		this.targetPort = targetPort;
	}
	public int getProxyPort() {
		return proxyPort;
	}
	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}
}
