package it.amattioli.applicate.sessions;

import it.amattioli.applicate.util.ApplicateException;

public class UnknownServiceException extends ApplicateException {

	public UnknownServiceException(String serviceName) {
		super(UnknownServiceException.class, serviceName);
	}
}
