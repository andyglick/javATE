package it.amattioli.applicate.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ApplicateException extends RuntimeException {
	private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("it.amattioli.applicate.util.ErrorMessages");
	
	public ApplicateException(Class<? extends ApplicateException> exception, Object... parameters) {
		super(MessageFormat.format(MESSAGES.getString(exception.getName()), parameters));
	}
	
	public ApplicateException(Class<? extends ApplicateException> exception, Throwable cause, Object... parameters) {
		super(MessageFormat.format(MESSAGES.getString(exception.getName()), parameters), cause);
	}

}