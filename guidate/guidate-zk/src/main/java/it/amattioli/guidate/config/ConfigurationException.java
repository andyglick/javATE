package it.amattioli.guidate.config;

import it.amattioli.guidate.util.GuidateException;

public class ConfigurationException extends GuidateException {

	public ConfigurationException() {
		super();
	}

	public ConfigurationException(String msg, Throwable t) {
		super(msg, t);
	}

	public ConfigurationException(String msg) {
		super(msg);
	}

	public ConfigurationException(Throwable t) {
		super(t);
	}

}
