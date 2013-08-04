package it.amattioli.dominate.validation;

import org.mockito.Mockito;

import junit.framework.TestCase;

public class ValidatorsTest extends TestCase {

	public void testValidatorValidateItself() {
		Validator validatorImpl = Mockito.mock(Validator.class);
		Validator validator = Validators.validatorFor(validatorImpl);
		assertSame(validatorImpl, validator);
	}
	
}
