package it.amattioli.encapsulate.money.specifications;

import static org.mockito.Mockito.*;
import it.amattioli.dominate.specifications.SpecificationChangeEvent;
import it.amattioli.dominate.specifications.SpecificationChangeListener;
import it.amattioli.encapsulate.money.Money;
import it.amattioli.encapsulate.range.GenericContinousRange;

import java.util.Currency;

import junit.framework.TestCase;

public class MoneySpecificationTest extends TestCase {

	public void testSatisfied() {
		MyEntity e = new MyEntity();
		e.setMyMoney(new Money(10, Currency.getInstance("USD")));
		MoneySpecification<MyEntity> spec = MoneySpecification.newInstance("myMoney");
		spec.setValue(new GenericContinousRange<Money>(new Money(0, Currency.getInstance("USD")), new Money(100, Currency.getInstance("USD"))));
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testSpecificationChange() {
		MoneySpecification<MyEntity> spec = MoneySpecification.newInstance("myMoney");
		SpecificationChangeListener listener = mock(SpecificationChangeListener.class);
		spec.addSpecificationChangeListener(listener);
		spec.setValue(new GenericContinousRange<Money>(new Money(0, Currency.getInstance("USD")), new Money(100, Currency.getInstance("USD"))));
		verify(listener).specificationChange(any(SpecificationChangeEvent.class));
	}
	
}
