package it.amattioli.springate;

import java.util.List;

import it.amattioli.applicate.browsing.ListBrowser;
import it.amattioli.dominate.RepositoryRegistry;
import it.amattioli.dominate.hibernate.HibernateSessionManager;
import junit.framework.TestCase;

public class SpringServiceFactoryTest extends TestCase {

	protected void setUp() throws Exception {
		HibernateSessionManager.setCfgResource("test-hibernate.cfg.xml");
		RepositoryRegistry.instance().setRepositoryFactoryClass(FakeRepositoryFactory.class);
	}

	public void testKnowsService() {	
		SpringServiceFactory factory = new SpringServiceFactory("test-config.xml");
		assertTrue(factory.knowsService("simpleService"));
	}
	
	public void testTwoConfigs() {	
		SpringServiceFactory factory = new SpringServiceFactory("test-config.xml","test-config2.xml");
		assertTrue(factory.knowsService("simpleService"));
		assertTrue(factory.knowsService("simpleService2"));
	}
	
	public void testCreateSimpleService() {
		SpringServiceFactory factory = new SpringServiceFactory("test-config.xml");
		SimpleService srv = (SimpleService)factory.createService("simpleService");
	}
	
	public void testCreateThreadLocalService() {
		SpringServiceFactory factory = new SpringServiceFactory("test-config.xml");
		SimpleService srv = (SimpleService)factory.createService("threadLocalSimpleService");
	}
	
	public void testCreateLongRunningService() {
		SpringServiceFactory factory = new SpringServiceFactory("test-config.xml");
		SimpleService srv = (SimpleService)factory.createService("longRunningSimpleService");
	}
	
	public void testCreateTransactionalService() {
		SpringServiceFactory factory = new SpringServiceFactory("test-config.xml");
		SimpleCommand srv = (SimpleCommand)factory.createService("transactionalSimpleCommand");
	}

	public void testCreateServiceWithRepository() {
		SpringServiceFactory factory = new SpringServiceFactory("test-config.xml");
		ListBrowser<Long, FakeEntity> srv = (ListBrowser<Long, FakeEntity>)factory.createService("listBrowser");
		List<FakeEntity> list = srv.getList();
		assertEquals(1, list.size());
		assertEquals(new Long(1), list.get(0).getId());
	}
}
