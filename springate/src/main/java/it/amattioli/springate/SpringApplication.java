package it.amattioli.springate;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import it.amattioli.applicate.sessions.ApplicateSession;
import it.amattioli.applicate.sessions.Application;
import it.amattioli.dominate.RepositoryRegistry;

public class SpringApplication implements Application {
	private static final String APPLICATE_SESSION_BEAN = "applicateSession";
	private static final String REPOSITORY_REGISTRY_BEAN = "repositoryRegistry";
	private ApplicationContext ctx;
	
	public SpringApplication(String springConfig) {
		ctx = new ClassPathXmlApplicationContext(springConfig);
	}
	
	@Override
	public void init() {
		if (ctx.containsBeanDefinition(REPOSITORY_REGISTRY_BEAN)) {
			RepositoryRegistry registry = (RepositoryRegistry)ctx.getBean(REPOSITORY_REGISTRY_BEAN);
			RepositoryRegistry.setInstance(registry);
		}
	}

	@Override
	public ApplicateSession createSession() {
		return (ApplicateSession)ctx.getBean(APPLICATE_SESSION_BEAN);
	}

}
