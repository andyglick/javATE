package it.amattioli.encapsulate.money.hibernate;

import java.util.Currency;

import it.amattioli.dominate.Repository;
import it.amattioli.dominate.hibernate.ClassHibernateRepository;
import it.amattioli.dominate.hibernate.HibernateSessionManager;
import it.amattioli.encapsulate.money.FakeMoneyEntity;
import it.amattioli.encapsulate.money.Money;
import junit.framework.TestCase;

public class MoneyHibernateTypeTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		HibernateTestInit.init();
	}

	public void testLoad() {
		Repository<Long, FakeMoneyEntity> rep = new ClassHibernateRepository<Long, FakeMoneyEntity>(FakeMoneyEntity.class);
		FakeMoneyEntity e = rep.get(1L);
		assertEquals(new Money(0L,Currency.getInstance("EUR")),e.getMoney());
	}
	
	@Override
	protected void tearDown() throws Exception {
		HibernateSessionManager.disconnectAll();
	}
	
}
