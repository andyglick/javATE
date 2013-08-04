package it.amattioli.dominate.validation;

import static it.amattioli.dominate.validation.ValidationResult.ResultType.INVALID;

import java.util.ArrayList;
import java.util.Collection;

public abstract class ChainedValidator implements Validator {
	private Validator nextInChain;
	private Object validatingBean;
	
	public ChainedValidator() {
		nextInChain = null;
		validatingBean = null;
	}
	
	public ChainedValidator(Object validatingBean) {
		nextInChain = null;
		this.validatingBean = validatingBean;
	}
	
	public ChainedValidator(Object validatingBean, Validator nextInChain) {
		this.validatingBean = validatingBean;
		this.nextInChain = nextInChain;
	}
	
	protected Object getValidatingBean() {
		return validatingBean;
	}
	
	protected Validator getNextInChain() {
		return nextInChain;
	}

	@Override
	public Constraint getPropertyConstraint(String propertyName, String constraintName) {
		for (Constraint curr: getPropertyConstraints(propertyName)) {
			if (constraintName.equals(curr.getName())) {
				return curr;
			}
		}
		return null;
	}

	@Override
	public Collection<Constraint> getPropertyConstraints(String propertyName) {
		Collection<Constraint> result = new ArrayList<Constraint>();
		result.addAll(itselfGetPropertyConstraints(propertyName));
		if (getNextInChain() != null) {
			result.addAll(getNextInChain().getPropertyConstraints(propertyName));
		}
		return result;
	}
	
	protected abstract Collection<Constraint> itselfGetPropertyConstraints(String propertyName);

	@Override
	public ValidationResult validateBean() {
		ValidationResult result1 = itselfValidateBean();
		if (result1.getType().equals(INVALID) || getNextInChain() == null) {
			return result1;
		} else {
			return getNextInChain().validateBean();
		}
	}
	
	protected abstract ValidationResult itselfValidateBean();

	@Override
	public ValidationResult validateProperty(String propertyName, Object value) {
		ValidationResult result1 = itselfValidateProperty(propertyName, value);
		if (result1.getType().equals(INVALID) || getNextInChain() == null) {
			return result1;
		} else {
			return getNextInChain().validateProperty(propertyName, value);
		}
	}
	
	protected abstract ValidationResult itselfValidateProperty(String propertyName, Object value);

}
