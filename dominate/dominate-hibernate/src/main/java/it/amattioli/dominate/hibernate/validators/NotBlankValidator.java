package it.amattioli.dominate.hibernate.validators;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotBlankValidator implements Validator<NotBlank> {
	private static final Logger logger = LoggerFactory.getLogger(NotBlankValidator.class);

	public void initialize(NotBlank arg0) {
		// Nothing to do
	}

	public boolean isValid(Object value) {
		logger.debug("NotBlank validation for #"+value+"#");
		return (value instanceof String) && !StringUtils.isBlank((String)value);
	}

}
