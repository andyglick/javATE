package it.amattioli.dominate.validation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

/**
 * A default implementation of the {@link Validator} interface.
 * <p>
 * This implementation can perform validation in two distinct ways:
 * <ul>
 *   <li> using Hibernate Validator
 *   <li> using validation methods
 * </ul>
 * 
 * To validate a property using Hibernate Validator simply annotate
 * one of its accessor methods with a Hibernate Validator compliant
 * annotation.
 * <p>
 * To validate a property using validation methods you have to create
 * a public method in the validating class whose name must be of the form:
 * <p>
 * <code>public ValidationResult validatePropertyName(PropertyClass validatingValue)</code>
 * <p>
 * 
 * @author andrea
 *
 */
public class DefaultValidator extends ChainedValidator {
	
	public DefaultValidator(Object validatingBean) {
		super(validatingBean,
			  new HibernateValidator3Validator(validatingBean));
		     
	}
	
	@Override
	protected ValidationResult itselfValidateBean() {
		try {
			Method validateMethod = getValidatingBean().getClass().getMethod("validate");
			ValidationResult result = (ValidationResult)validateMethod.invoke(getValidatingBean());
			if (result == null) {
				return new ValidationResult(ValidationResult.ResultType.VALID, null);
			}
			return result;
		} catch(NoSuchMethodException e) {
			// Se il metodo non esiste non occorre fare la validazione
			return new ValidationResult(ValidationResult.ResultType.VALID, null);
		} catch(InvocationTargetException ite) {
			throw new RuntimeException(findRootCause(ite));
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected ValidationResult itselfValidateProperty(String propertyName, Object value) {
		try {
			Method validateMethod = getValidatingBean().getClass().getMethod("validate"+StringUtils.capitalize(propertyName), getPropertyClass(propertyName));
			ValidationResult result = (ValidationResult)validateMethod.invoke(getValidatingBean(), value);
			if (result == null) {
				return new ValidationResult(ValidationResult.ResultType.VALID, null);
			}
			return result;
		} catch(NoSuchMethodException e) {
			// Se il metodo non esiste non occorre fare la validazione
			return new ValidationResult(ValidationResult.ResultType.VALID, null);
		} catch(InvocationTargetException ite) {
			throw new RuntimeException(findRootCause(ite));
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private Class<?> getPropertyClass(String propertyName) {
		try {
			return PropertyUtils.getPropertyType(getValidatingBean(), propertyName);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected Collection<Constraint> itselfGetPropertyConstraints(String propertyName) {
		return Collections.emptyList();
	}
	
	private Throwable findRootCause(Throwable e) {
		if (e.getCause() == null) {
			return e;
		} else {
			return findRootCause(e.getCause());
		}
	}

}
