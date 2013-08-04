package it.amattioli.dominate.hibernate.types;

import java.util.Properties;

import it.amattioli.dominate.hibernate.FakeEntity;
import it.amattioli.dominate.hibernate.FakeEnum;
import it.amattioli.dominate.hibernate.ClassHibernateRepository;
import it.amattioli.dominate.hibernate.HibernateSessionManager;
import it.amattioli.dominate.hibernate.HibernateTestInit;
import junit.framework.TestCase;

public class EnumUserTypeTest extends TestCase {
	
	@Override
	protected void setUp() throws Exception {
		HibernateTestInit.init();
	}
	
	public void testParameters() {
		Properties p = new Properties();
		p.setProperty(EnumUserType.ENUM_CLASS_PARAM, "it.amattioli.dominate.hibernate.FakeEnum");
		EnumUserType<FakeEnum> t = new EnumUserType<FakeEnum>();
		t.setParameterValues(p);
		assertEquals(FakeEnum.class, t.returnedClass());
	}
	
	public void testLoad() {
		ClassHibernateRepository<Long, FakeEntity> rep = new ClassHibernateRepository<Long, FakeEntity>(FakeEntity.class);
		FakeEntity e = rep.get(1L);
		assertEquals(FakeEnum.VALUE1, e.getEnumerated());
	}
	
	@Override
	protected void tearDown() throws Exception {
		HibernateSessionManager.disconnectAll();
	}
	
}
