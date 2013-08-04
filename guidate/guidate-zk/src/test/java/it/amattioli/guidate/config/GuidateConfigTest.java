package it.amattioli.guidate.config;

import junit.framework.TestCase;
import it.amattioli.applicate.serviceFactory.CompositeServiceFactory;
import it.amattioli.applicate.serviceFactory.VoidServiceFactory;
import it.amattioli.applicate.sessions.ApplicateSession;
import it.amattioli.dominate.hibernate.HibernateRepositoryFactory;

public class GuidateConfigTest extends TestCase {

	public void testRepositoryFactoryClassDefault() {
		assertEquals(GuidateConfig.instance.getRepositoryFactoryClass(),
				     HibernateRepositoryFactory.class);
	}
	
	public void testApplicateSessionClassDefault() {
		assertEquals(GuidateConfig.instance.getApplicateSessionClass(),
				     ApplicateSession.class);
	}
	
	public void testServiceFactoryClassDefault() {
		assertEquals(GuidateConfig.instance.getServiceFactoryClass(),
				     VoidServiceFactory.class);
	}
	
	public void testApplicateSessionVariableDefault() {
		assertEquals(GuidateConfig.instance.getApplicateSessionVariable(),
				     "applicateSession");
	}
	
	public void testRepositoryFactoryClass() {
		GuidateConfig.instance.setPropertiesFileName("/it/amattioli/guidate/config/testconfig.properties");
		assertEquals(GuidateConfig.instance.getRepositoryFactoryClass(),
				     HibernateRepositoryFactory.class);
	}
	
	public void testApplicateSessionClass() {
		GuidateConfig.instance.setPropertiesFileName("/it/amattioli/guidate/config/testconfig.properties");
		assertEquals(GuidateConfig.instance.getApplicateSessionClass(),
				     ApplicateSession.class);
	}
	
	public void testServiceFactoryClass() {
		GuidateConfig.instance.setPropertiesFileName("/it/amattioli/guidate/config/testconfig.properties");
		assertEquals(GuidateConfig.instance.getServiceFactoryClass(),
				     CompositeServiceFactory.class);
	}
	
	public void testApplicateSessionVariable() {
		GuidateConfig.instance.setPropertiesFileName("/it/amattioli/guidate/config/testconfig.properties");
		assertEquals(GuidateConfig.instance.getApplicateSessionVariable(),
				     "testSession");
	}
	
	public void testWrongRepositoryFactoryClass() {
		GuidateConfig.instance.setPropertiesFileName("/it/amattioli/guidate/config/errorconfig.properties");
		try {
			GuidateConfig.instance.getRepositoryFactoryClass();
			fail("Should throw exception");
		} catch(ConfigurationClassNotFound e) {
			assertEquals("it.error.UnknownRepositoryFactoryClass",e.getMessage());
		}
	}
	
	public void testWrongApplicateSessionClass() {
		GuidateConfig.instance.setPropertiesFileName("/it/amattioli/guidate/config/errorconfig.properties");
		try {
			GuidateConfig.instance.getApplicateSessionClass();
			fail("Should throw exception");
		} catch(ConfigurationClassNotFound e) {
			assertEquals("it.error.UnknownApplicateSessionClass",e.getMessage());
		}
	}
	
	public void testWrongServiceFactoryClass() {
		GuidateConfig.instance.setPropertiesFileName("/it/amattioli/guidate/config/errorconfig.properties");
		try {
			GuidateConfig.instance.getServiceFactoryClass();
			fail("Should throw exception");
		} catch(ConfigurationClassNotFound e) {
			assertEquals("it.error.UnknownServiceFactoryClass",e.getMessage());
		}
	}
	
}
