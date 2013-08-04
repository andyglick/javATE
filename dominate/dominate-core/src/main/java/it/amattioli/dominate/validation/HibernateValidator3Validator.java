package it.amattioli.dominate.validation;

import static it.amattioli.dominate.validation.ValidationResult.ResultType.INVALID;
import static it.amattioli.dominate.validation.ValidationResult.ResultType.VALID;

import it.amattioli.dominate.properties.AnnotationsRetriever;
import it.amattioli.dominate.properties.AnnotationsRetrieverImpl;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

public class HibernateValidator3Validator extends ChainedValidator {

	public HibernateValidator3Validator() {
		super();
	}

	public HibernateValidator3Validator(Object validatingBean, Validator nextInChain) {
		super(validatingBean, nextInChain);
	}

	public HibernateValidator3Validator(Object validatingBean) {
		super(validatingBean);
	}

	@Override
	protected Collection<Constraint> itselfGetPropertyConstraints(String propertyName) {
		AnnotationsRetriever retriever = new AnnotationsRetrieverImpl(getValidatingBean());
		Collection<Constraint> result = new ArrayList<Constraint>();
		for (Annotation curr: retriever.retrieveAnnotations(propertyName)) {
			result.add(new AnnotatedConstraint(curr));
		}
		return result;
	}

	@Override
	protected ValidationResult itselfValidateBean() {
		Class<?> validatingClass = getValidatingBean().getClass();
		ClassValidator validator = ClassValidatorPool.getInstance().getValidator( validatingClass );
		InvalidValue[] validationMessages = validator.getInvalidValues(getValidatingBean());
		for (InvalidValue validationMessage: validationMessages) {
			if (validationMessage.getPropertyName() == null) {
				String msg = validationMessage.getMessage();
				return new ValidationResult(INVALID, msg);
			}
		}
		return new ValidationResult(VALID, null);
	}

	@Override
	protected ValidationResult itselfValidateProperty(String propertyName, Object value) {
		Class<?> validatingClass = getValidatingBean().getClass();
		ClassValidator<?> validator = ClassValidatorPool.getInstance().getValidator( validatingClass );
		InvalidValue[] validationMessages = validator.getPotentialInvalidValues(propertyName,value);
		if (validationMessages.length > 0) {
			String msg = validationMessages[0].getMessage();
			return new ValidationResult(INVALID, msg);
		}
		return new ValidationResult(VALID, null);
	}

}
