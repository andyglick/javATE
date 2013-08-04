package it.amattioli.guidate.util;

public class GuidateException extends RuntimeException {

	public GuidateException() {
		super();
	}

	public GuidateException(String msg, Throwable t) {
		super(msg, t);
	}

	public GuidateException(String msg) {
		super(msg);
	}

	public GuidateException(Throwable t) {
		super(t);
	}

}
