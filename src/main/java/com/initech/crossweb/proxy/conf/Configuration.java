package com.initech.crossweb.proxy.conf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class Configuration implements Serializable{
	private static final long serialVersionUID = 5736060471229351415L;

	private int adminPort;
	private int echoPort;
	private Map<String,Target> targets = new HashMap<String,Target>();
	public int getAdminPort() {
		return adminPort;
	}
	public void setAdminPort(int adminPort) {
		this.adminPort = adminPort;
	}
	public int getEchoPort() {
		return echoPort;
	}
	public void setEchoPort(int echoPort) {
		this.echoPort = echoPort;
	}
	public Map<String,Target> getTargets() {
		return targets;
	}
	public void setTargets(Map<String,Target> targets) {
		this.targets = targets;
	}
	public void addTarget(String key,Target target) {
		this.targets.put(key,target);
	}
	
	public static Configuration load(String file_name) throws FileNotFoundException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonReader reader = new JsonReader(new FileReader("./conf/config.json"));
		return gson.fromJson(reader,Configuration.class);
	}
	public void save(String file_name) throws IOException {
		
		FileWriter writer = new FileWriter(new File("./conf/config.json"));
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		gson.toJson(this,writer);
		writer.flush();
		writer.close();
	}
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}

}
