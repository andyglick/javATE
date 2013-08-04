package it.amattioli.workstate.exceptions;

import java.util.*;

/**
 * A message identified by a key inside a bundle.
 * You can also pass a set of parameters that can be substituted inside
 * the message.
 * 
 */
public class KeyedMessage {
	private String key;
	private Map<String, String> parameters = new HashMap<String, String>();

	/**
	 * Construct a message identified by the given key.
	 * 
	 * @param key the key that identifies the message inside a message bundle
	 * @throws NullPointerException if the parameter is null
	 */
	public KeyedMessage(String key) {
		if (key == null) {
			throw new NullPointerException();
		}
		this.key = key;
	}

	/**
	 * Construct a message identified by the given key and with the given
	 * parameters.
	 *  
	 * @param key the key that identifies the message inside a message bundle
	 * @param parameters the parameters to be substituted inside a message
	 */
	public KeyedMessage(String key, Map<String, String> parameters) {
		this.key = key;
		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			this.parameters.put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Add a parameter to the message.
	 * 
	 * @param tag the parameter key
	 * @param value the value to be substituted inside the message
	 * @throws IllegalArgumentException if the message already contains a
	 *         parameter with the same key 
	 */
	public void addParameter(String tag, String value) {
		if (parameters.containsKey(tag)) {
			throw new IllegalArgumentException();
		}
		parameters.put(tag, value);
	}

	/**
	 * Returns the message key.
	 * 
	 * @return the message key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Return an immutable map containing the message parameters.
	 * 
	 * @return an immutable map containing the message parameters
	 */
	public Map<String, String> getParameters() {
		return Collections.unmodifiableMap(parameters);
	}

	/**
	 * Two messages are equal if they have the same key and the same
	 * parameters.
	 * 
	 * @param o the object to be compared to this for equality
	 * @return true if and only if the parameter is a message with the same
	 *         key and the same parameters of this one
	 */
	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof KeyedMessage) {
			KeyedMessage msg = (KeyedMessage) o;
			result = this.key.equals(msg.getKey());
			result = result && this.parameters.equals(msg.parameters);
		}
		return result;
	}

}
