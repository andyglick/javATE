package it.amattioli.springate;

import java.util.ArrayList;
import java.util.Arrays;

import it.amattioli.applicate.serviceFactory.ServiceFactory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringServiceFactory implements ServiceFactory, ApplicationContextAware {
	private static final String BASE_CONFIG = "/it/amattioli/springate/base-config.xml";
	private ApplicationContext ctx;
	
	public SpringServiceFactory() {
	}

	public SpringServiceFactory(String... springConfig) {
		ArrayList<String> configs = new ArrayList<String>(springConfig.length + 1);
		configs.add(BASE_CONFIG);
		configs.addAll(Arrays.asList(springConfig));
		ctx = new ClassPathXmlApplicationContext(configs.toArray(new String[configs.size()]));
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		if (this.ctx == null) {
			this.ctx = ctx;
		}
	}

	public Object createService(String serviceName) {
		return ctx.getBean(serviceName);
	}

	public boolean knowsService(String serviceName) {
		return ctx.containsBean(serviceName);
	}

}
