package com.initech.crossweb.cmpproxy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

public class Configurate {

	@Test
	public void make(){
		
		JSONObject config = new JSONObject();
		//JSONArray list = new JSONArray();
		
		config.put("Thread", 100);
		config.put("Logger", "logger");
		
		JSONObject ca1 = new JSONObject();
		ca1.put("ProxyPort","21");
		ca1.put("TargetIP","127.0.0.1");
		ca1.put("TargetPort","121");
		
		
		JSONObject ca2 = new JSONObject();
		ca2.put("ProxyPort","22");
		ca2.put("TargetIP","127.0.0.12");
		ca2.put("TargetPort","123");
		
		JSONObject ca_list = new JSONObject();
		ca_list.put("KSign", ca2);
		ca_list.put("YesSign", ca1);
		config.put("Targets", ca_list);

		try {
			FileWriter writer = new FileWriter(new File("./conf/config.json"));
			writer.write(config.toString(4));
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void load(){
		try {
			FileReader reader = new FileReader("./conf/config.json");
			JSONTokener  parser = new JSONTokener(reader);
			JSONObject config = new JSONObject(parser);

			System.out.println(config.toString(4));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
        
	}
}
