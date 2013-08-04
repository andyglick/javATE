package it.amattioli.guidate.validators;

import it.amattioli.dominate.validation.DefaultValidator;
import it.amattioli.dominate.validation.ValidationResult;
import static it.amattioli.dominate.validation.ValidationResult.ResultType.*;
import it.amattioli.dominate.validation.Validator;
import it.amattioli.guidate.containers.BackBeans;
import it.amattioli.guidate.converters.Converters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.impl.InputElement;

public class PropertyValidator implements Constraint {
	private static final Logger logger = LoggerFactory.getLogger(PropertyValidator.class);
	private String propertyName;
		
	public PropertyValidator(String propertyName, Class<?> propertyType) {
		this.propertyName = propertyName;
	}
	
	public PropertyValidator(String propertyName, String propertyType) {
		this.propertyName = propertyName;
	}
	
	public void validate(Component comp, Object value) throws WrongValueException {
		if (!isReadOnly(comp)) {
			Object bean = getBackBean(comp);
			Validator validator;
			if (bean instanceof Validator) {
				validator = (Validator)bean;
			} else {
				validator = new DefaultValidator(bean);
			}
			Object realValue = convertValue(comp, value);
			ValidationResult result = null;
			try {
				result = validator.validateProperty(propertyName, realValue);
			} catch(Exception e) {
				logger.error("Errore",e);
			}
			if (result != null && INVALID.equals(result.getType())) {
				throw new WrongValueException(comp, result.getMessage());
			}
		}
	}

	private boolean isReadOnly(Component comp) {
		return comp instanceof InputElement && ((InputElement)comp).isReadonly();
	}
	
    private Object convertValue(Component comp, Object value) {
    	TypeConverter converter = Converters.getFromComponent(comp);
		return converter.coerceToBean(value, comp);
	}

	private Object getBackBean(Component comp) {
        return BackBeans.findBackBean(comp);
    }
	
}
