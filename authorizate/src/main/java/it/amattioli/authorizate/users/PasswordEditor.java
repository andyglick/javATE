package it.amattioli.authorizate.users;

import it.amattioli.dominate.hibernate.validators.NotBlank;
import it.amattioli.dominate.validation.ValidationResult;

import org.apache.commons.lang.StringUtils;

public class PasswordEditor implements PasswordSupplier {
	private String password1;
	private String password2;
	
	public String getPassword() {
		return password1;
	}
	
	@NotBlank
	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	
	public ValidationResult validatePassword2(String password2) {
		if (!StringUtils.equals(password1, password2)) {
			return new ValidationResult(ValidationResult.ResultType.INVALID, "WRONG_PASSWORD");
		}
		return null;
	}

}
