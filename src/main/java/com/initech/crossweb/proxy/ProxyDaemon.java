package com.initech.crossweb.proxy;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.apache.commons.daemon.support.DaemonLoader.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.initech.crossweb.proxy.cmp.CmpProxyService;
import com.initech.crossweb.proxy.conf.Configuration;
import com.initech.crossweb.proxy.conf.Target;
import com.initech.crossweb.proxy.control.ControllerService;
import com.initech.crossweb.proxy.echo.EchoService;

public class ProxyDaemon implements Daemon {
    static final Logger logger = LoggerFactory.getLogger(ProxyDaemon.class);;

    private static final ProxyDaemon daemon = new ProxyDaemon();
    private Configuration config;
    private Set<AbstractService> services = new HashSet<AbstractService>();

    
	@Override
	public void init(DaemonContext context) throws DaemonInitException, Exception {
		//String[] args = context.getArguments();
		
		String config_file = System.getProperty("conf.path") + "/config.json";
		this.config = Configuration.load(config_file);

		OpenControllerService();
		OpenEchoService();
		OpenProxyServices();
	}

	private void OpenEchoService() throws IOException {
		EchoService echo_service = new EchoService();
		echo_service.open(config.getEchoPort());
		
		this.services.add(echo_service);
	}

	private void OpenProxyServices() {
		
		Map<String,Target> targets = config.getTargets();
		for (Entry<String,Target> entry : targets.entrySet()) {
			try {
				
				Target target = entry.getValue();
				CmpProxyService proxy_service = new CmpProxyService(entry.getKey());
				proxy_service.setTarget(target);
				proxy_service.open(target.getProxyPort());
				
				services.add(proxy_service);
			} catch (Exception e) {
				logger.error("Proxy Service Open Error",e);
			}
		}
	}

	private void OpenControllerService() throws IOException {
		ControllerService controller_service = new ControllerService(this.services);
		controller_service.open(config.getAdminPort());
		
		this.services.add(controller_service);
	}

	@Override
	public void start() throws Exception {
		for (AbstractService service : services) {
			service.start();
		}
		
//		String slef_test = System.getProperty("self.test");
//		if( "true".equals(slef_test)){
//			self_test();
//		}
	}

	@Override
	public void stop() throws Exception {
		for (AbstractService service : services) {
			service.close();
		}
		
		destroy();
	}

	@Override
	public void destroy() {
		logger.info("CmpProxyServer destroy");
	}

	static void start(String [] args){
		try {

            /* Create context */
            final Context context = new Context();
            context.setArguments(args);
            
			daemon.init(context);
			daemon.start();
		} catch (DaemonInitException e) {
			logger.error("{}",e);
		} catch (Exception e) {
			logger.error("{}",e);
		}
	}
	static void stop(String [] args){
		try {
			daemon.stop();
		} catch (Exception e) {
			logger.error("{}",e);
		}
    }
	
	public static void main(String[] args) {
		
		if(System.getProperty("log.mode") == null){
			System.setProperty("log.mode","INFO");
		}
		
		if(args == null || args.length == 0) {
			String[] main_args = new String[3]; 
        	main_args[0] = System.getProperty("conf.path") +  "/config.json";
        	start(main_args);
		}else {
			if ("start".equals(args[0])) {
	            start(args);
	        } else if ("stop".equals(args[0])) {
	            stop(args);
	        }
		}
    }
}
