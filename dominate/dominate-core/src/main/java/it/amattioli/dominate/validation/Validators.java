package it.amattioli.dominate.validation;

public class Validators {

	private Validators() {
		
	}
	
	public static Validator validatorFor(Object bean) {
		if (bean instanceof Validator) {
			return (Validator)bean;
		} else {
			return new DefaultValidator(bean);
		}
	}

}
