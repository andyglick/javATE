package it.amattioli.applicate.sessions;

import it.amattioli.applicate.util.ApplicateException;

public class ServiceCreationException extends ApplicateException {

	public ServiceCreationException(Throwable cause) {
		super(ServiceCreationException.class, cause, cause.getMessage());
	}
	
}
