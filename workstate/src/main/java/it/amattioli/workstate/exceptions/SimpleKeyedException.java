package it.amattioli.workstate.exceptions;

public class SimpleKeyedException extends Exception implements KeyedException {
	private KeyedMessage keyedMessage;
	private MessageBundle bundle;

	public SimpleKeyedException(MessageBundle bundle, KeyedMessage keyedMessage) {
		this.keyedMessage = keyedMessage;
		this.bundle = bundle;
	}

	public SimpleKeyedException(KeyedMessage keyedMessage) {
		this(null, keyedMessage);
	}

	public SimpleKeyedException(MessageBundle bundle, String key) {
		this(bundle, new KeyedMessage(key));
	}

	public SimpleKeyedException(String key) {
		this(null, key);
	}

	public KeyedMessage getKeyedMessage() {
		return this.keyedMessage;
	}

	public void addParameter(String tag, String value) {
		this.keyedMessage.addParameter(tag, value);
	}

	@Override
	public String getMessage() {
		if (bundle == null) {
			return getKeyedMessage().getKey();
		} else {
			return bundle.getErrorMessage(getKeyedMessage());
		}
	}

	@Override
	public String getLocalizedMessage() {
		return getMessage();
	}

}
