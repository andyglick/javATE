package it.amattioli.encapsulate.money.specifications.beans;

import junit.framework.TestCase;

import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;

import it.amattioli.dominate.specifications.beans.BeanSpecification;
import it.amattioli.encapsulate.money.Money;
import it.amattioli.encapsulate.range.GenericContinousRange;
import it.amattioli.encapsulate.range.Range;

public class BeanSpecificationTest extends TestCase {

	public void testMoneySpecificationPropertyType() {
		BeanSpecification<MyEntity> spec = new BeanSpecification<MyEntity>(MyEntity.class);
		DynaProperty dynaProperty = spec.getDynaClass().getDynaProperty("cost");
		assertEquals(Range.class, dynaProperty.getType());
	}
	
	public void testMoneySpecificationPropertyValue() throws Exception {
		BeanSpecification<MyEntity> spec = new BeanSpecification<MyEntity>(MyEntity.class);
		Range<Money> moneyRange = new GenericContinousRange<Money>(Money.euro(0), Money.euro(100));
		PropertyUtils.setProperty(spec, "cost", moneyRange);
		assertEquals(moneyRange, PropertyUtils.getProperty(spec, "cost"));
	}
	
	public void testMoneySpecificationSatisfied() throws Exception {
		BeanSpecification<MyEntity> spec = new BeanSpecification<MyEntity>(MyEntity.class);
		Range<Money> moneyRange = new GenericContinousRange<Money>(Money.euro(0), Money.euro(100));
		PropertyUtils.setProperty(spec, "cost", moneyRange);
		MyEntity e = new MyEntity();
		e.setCost(Money.euro(10));
		assertTrue(spec.isSatisfiedBy(e));
	}

}
