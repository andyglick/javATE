package it.amattioli.dominate.validation;

import it.amattioli.dominate.FakeEntity;

import java.util.Collection;

import junit.framework.TestCase;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.WrapDynaBean;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

import static it.amattioli.dominate.validation.ValidationResult.ResultType.*;

public class DefaultValidatorTest extends TestCase {

	public void testValidatePropertyByAnnotation() {
		FakeEntity e = new FakeEntity();
		DefaultValidator v = new DefaultValidator(e);
		
		ValidationResult result1 = v.validateProperty("description", "validDescription");
		assertEquals(VALID, result1.getType());
		
		ValidationResult result2 = v.validateProperty("description", "");
		assertEquals(INVALID, result2.getType());
		assertEquals("NOT NULL", result2.getMessage());
	}
	
	public void testValidatePropertyByMethod() {
		FakeEntity e = new FakeEntity();
		DefaultValidator v = new DefaultValidator(e);
		
		ValidationResult result1 = v.validateProperty("description", "validDescription");
		assertEquals(VALID, result1.getType());
		
		ValidationResult result2 = v.validateProperty("description", "wrongDescription");
		assertEquals(INVALID, result2.getType());
		assertEquals("WRONG DESCRIPTION", result2.getMessage());
	}
	
	public void testPropertyConstraints() {
		FakeEntity e = new FakeEntity();
		DefaultValidator v = new DefaultValidator(e);
		Collection<Constraint> constraints = v.getPropertyConstraints("description");
		assertEquals(1, constraints.size());
		assertEquals(NotEmpty.class.getName(), constraints.iterator().next().getName());
	}
	
	public void testPropertyConstraint() {
		FakeEntity e = new FakeEntity();
		DefaultValidator v = new DefaultValidator(e);
		Constraint c = v.getPropertyConstraint("constrainted",Length.class.getName());
		assertNotNull(c);
		assertEquals(Length.class.getName(), c.getName());
		assertEquals(20, c.getParameter("max"));
	}

	public void testBeanValidation() {
		FakeEntity e = new FakeEntity();
		e.setDescription("bean");
		e.setConstrainted("IsValid");
		DefaultValidator v = new DefaultValidator(e);
		
		ValidationResult result1 = v.validateBean();
		assertEquals(VALID, result1.getType());
		
		e.setDescription("object");
		ValidationResult result2 = v.validateBean();
		assertEquals(INVALID, result2.getType());
	}

}
