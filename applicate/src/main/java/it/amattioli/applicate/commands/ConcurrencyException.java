package it.amattioli.applicate.commands;

//import it.amattioli.common.exceptions.MessageBundle;

import java.text.MessageFormat;
import java.util.ResourceBundle;

//import org.hibernate.StaleObjectStateException;

public class ConcurrencyException extends RuntimeException {
	private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("it.amattioli.applicate.util.ErrorMessages");
	private static String key = ConcurrencyException.class.getName();
	
	public ConcurrencyException(String entityName) {
		super(MessageFormat.format(MESSAGES.getString(key),entityName));
//		super(new MessageBundle(MESSAGES),key);
//		addParameter(entityNameParam, entityName);
	}
	
//	@Deprecated
//	public ConcurrencyException(StaleObjectStateException e) {
//		this(e.getEntityName());
//	}

}
