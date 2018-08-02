package com.initech.crossweb.cmpproxy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

public class Configurate {

	@Test
	public void make(){
		
		
		JSONObject config = new JSONObject();
		JSONArray list = new JSONArray();
		
		config.put("Thread", 100);
		config.put("Logger", "logger");
		
		JSONObject ca1 = new JSONObject();
		ca1.put("Name","YesSign");
		ca1.put("IP","127.0.0.1");
		ca1.put("Port","121");
		ca1.put("ProxyPort","21");
		
		
		JSONObject ca2 = new JSONObject();
		ca2.put("Name","KSign");
		ca2.put("IP","127.0.0.12");
		ca2.put("Port","123");
		ca2.put("ProxyPort","22");
		
		list.add(ca1);
		list.add(ca2);
		
		config.put("CA", list);
		

		try {
			FileWriter writer = new FileWriter(new File("./conf/config.json"));
			writer.write(config.toJSONString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
