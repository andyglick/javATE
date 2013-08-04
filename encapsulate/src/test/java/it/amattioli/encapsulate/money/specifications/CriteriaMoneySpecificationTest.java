package it.amattioli.encapsulate.money.specifications;

import java.util.Currency;
import java.util.List;

import it.amattioli.dominate.Repository;
import it.amattioli.dominate.hibernate.ClassHibernateRepository;
import it.amattioli.dominate.hibernate.HibernateSessionManager;
import it.amattioli.encapsulate.money.FakeMoneyEntity;
import it.amattioli.encapsulate.money.Money;
import it.amattioli.encapsulate.money.hibernate.HibernateTestInit;
import it.amattioli.encapsulate.range.GenericContinousRange;
import junit.framework.TestCase;

public class CriteriaMoneySpecificationTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		HibernateTestInit.init();
	}
	
	public void testLowerBound() {
		Repository<Long, FakeMoneyEntity> rep = new ClassHibernateRepository<Long, FakeMoneyEntity>(FakeMoneyEntity.class);
		CriteriaMoneySpecification<FakeMoneyEntity> spec = new CriteriaMoneySpecification<FakeMoneyEntity>("money");
		Money lowerBound = new Money(100, Currency.getInstance("EUR"));
		spec.setValue(new GenericContinousRange<Money>(lowerBound, null));
		List<FakeMoneyEntity> result = rep.list(spec);
		assertFalse(result.isEmpty());
		for (FakeMoneyEntity curr: result) {
			assertTrue(!curr.getMoney().isLessThan(lowerBound));
		}
	}
	
	public void testHigherBound() {
		Repository<Long, FakeMoneyEntity> rep = new ClassHibernateRepository<Long, FakeMoneyEntity>(FakeMoneyEntity.class);
		CriteriaMoneySpecification<FakeMoneyEntity> spec = new CriteriaMoneySpecification<FakeMoneyEntity>("money");
		Money higherBound = new Money(100, Currency.getInstance("EUR"));
		spec.setValue(new GenericContinousRange<Money>(null, higherBound));
		List<FakeMoneyEntity> result = rep.list(spec);
		assertFalse(result.isEmpty());
		for (FakeMoneyEntity curr: result) {
			assertTrue(curr.getMoney().isLessThan(higherBound));
		}
	}
	
	public void testDoubleBound() {
		Repository<Long, FakeMoneyEntity> rep = new ClassHibernateRepository<Long, FakeMoneyEntity>(FakeMoneyEntity.class);
		CriteriaMoneySpecification<FakeMoneyEntity> spec = new CriteriaMoneySpecification<FakeMoneyEntity>("money");
		Money lowerBound = new Money(10, Currency.getInstance("EUR"));
		Money higherBound = new Money(100, Currency.getInstance("EUR"));
		spec.setValue(new GenericContinousRange<Money>(lowerBound, higherBound));
		List<FakeMoneyEntity> result = rep.list(spec);
		assertFalse(result.isEmpty());
		for (FakeMoneyEntity curr: result) {
			assertTrue(curr.getMoney().isLessThan(higherBound) && !curr.getMoney().isLessThan(lowerBound));
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		HibernateSessionManager.disconnectAll();
	}
}
