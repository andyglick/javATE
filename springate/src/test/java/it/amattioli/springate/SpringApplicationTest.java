package it.amattioli.springate;

import it.amattioli.applicate.sessions.ApplicateSession;
import it.amattioli.dominate.RepositoryRegistry;
import junit.framework.TestCase;

public class SpringApplicationTest extends TestCase {

	public void testInit() {
		SpringApplication application = new SpringApplication("test-application.cfg.xml");
		application.init();
		assertTrue(RepositoryRegistry.instance().getRepositoryFactory() instanceof FakeRepositoryFactory);
	}
	
	public void testSession() {
		SpringApplication application = new SpringApplication("test-application.cfg.xml");
		application.init();
		ApplicateSession session = application.createSession();
		assertTrue(session instanceof FakeApplicateSession);
	}
	
}
