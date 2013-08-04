package it.amattioli.encapsulate.dates.specifications.beans;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import junit.framework.TestCase;

import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;

import it.amattioli.dominate.specifications.beans.BeanSpecification;
import it.amattioli.encapsulate.dates.Month;
import it.amattioli.encapsulate.dates.TimeInterval;

public class BeanSpecificationTest extends TestCase {

	public void testDateSpecificationPropertyType() {
		BeanSpecification<MyEntity> spec = new BeanSpecification<MyEntity>(MyEntity.class);
		DynaProperty dynaProperty = spec.getDynaClass().getDynaProperty("birthdate");
		assertEquals(TimeInterval.class, dynaProperty.getType());
	}
	
	public void testDateSpecificationPropertyValue() throws Exception {
		BeanSpecification<MyEntity> spec = new BeanSpecification<MyEntity>(MyEntity.class);
		PropertyUtils.setProperty(spec, "birthdate", new Month(Calendar.APRIL, 2011));
		assertEquals(new Month(Calendar.APRIL, 2011), PropertyUtils.getProperty(spec, "birthdate"));
	}
	
	public void testDateSpecificationSatisfied() throws Exception {
		BeanSpecification<MyEntity> spec = new BeanSpecification<MyEntity>(MyEntity.class);
		PropertyUtils.setProperty(spec, "birthdate", new Month(Calendar.APRIL, 2011));
		MyEntity e = new MyEntity();
		e.setBirthdate(new SimpleDateFormat("dd/MM/yyyy").parse("04/04/2011"));
		assertTrue(spec.isSatisfiedBy(e));
	}

}
