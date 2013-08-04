package it.amattioli.guidate.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.amattioli.applicate.sessions.Application;
import it.amattioli.springate.SpringApplication;

public class Guidate {
	private static final Logger logger = LoggerFactory.getLogger(Guidate.class);
	private static final String SPRING_APP_CFG = "application.cfg.xml";
	public static final String GUIDATE_APP_ATTRIBUTE = "it.amattioli.guidate.init.GuidateApp";
	private static Application guidateApp;
	
	private Guidate() {
		
	}
	
	public static Application getApplication() {
		if (guidateApp == null) {
			if (Guidate.class.getResource("/"+SPRING_APP_CFG) != null) {
				logger.info("Found " + SPRING_APP_CFG + " : using spring configuration");
				guidateApp = new SpringApplication(SPRING_APP_CFG);
			} else {
				logger.info("Using default configuration");
				guidateApp = new GuidateApplication();
			}
		}
		return guidateApp;
	}
	
}
