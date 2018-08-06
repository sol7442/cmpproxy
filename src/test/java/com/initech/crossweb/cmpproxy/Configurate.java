package com.initech.crossweb.cmpproxy;


import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import com.initech.crossweb.proxy.conf.Configuration;
import com.initech.crossweb.proxy.conf.Target;

public class Configurate {

	
	//@Test
	public void make(){
		
		Configuration config = new Configuration();
		config.setAdminPort(5100);
		config.setEchoPort(5200);
		
		Target t_ksign = new Target();
		t_ksign.setTargetIp("127.0.0.1");
		t_ksign.setTargetPort(5200);//echo port
		t_ksign.setProxyPort(5301);
		
		config.addTarget("KSign", t_ksign);
		
		Target t_yessign = new Target();
		t_yessign.setTargetIp("127.0.0.1");
		t_yessign.setTargetPort(5200);//echo port
		t_yessign.setProxyPort(5302);
		
		config.addTarget("YesSign", t_yessign);
		
		
		Target t_signkor = new Target();
		t_signkor.setTargetIp("127.0.0.1");
		t_signkor.setTargetPort(5200);//echo port
		t_signkor.setProxyPort(5303);
		
		config.addTarget("SignKorea", t_signkor);
		
		
		try {
			config.save("./conf/config.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void load(){
		try {
			
//			Gson gson = new GsonBuilder().setPrettyPrinting().create();
//			JsonReader reader = new JsonReader(new FileReader("./conf/config.json"));
//			Configuration config = gson.fromJson(reader,Configuration.class);
//			
//			System.out.println(gson.toJson(config));
			Configuration config = Configuration.load("./conf/config.json");
			System.out.println(config);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
}
