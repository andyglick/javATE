package it.amattioli.applicate.util;

public class ApplicateServiceException extends ApplicateException {

	public ApplicateServiceException(Object service, Throwable cause) {
		super(ApplicateServiceException.class, cause, service.toString());
	}
	
}
