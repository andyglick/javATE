package it.amattioli.workstate.exceptions;

import java.util.*;
import java.text.*;
import org.apache.commons.lang.StringUtils;

/**
 * Abstract class for the rapid development of message bundle retrieving classes.
 * <p>
 * Messages can contains place-holders for parameter substitution.
 * Place holder syntax is the same as for {@link MessageFormat}: "{0}" is the
 * first parameter, "{1}" is the second one and so on.
 * 
 */
public class MessageBundle {
	private ResourceBundle messages;

	/**
	 * Construct a {@link MessageBundle} using the given {@link ResourceBundle}.
	 * 
	 * @param messages the {@link ResourceBundle} used to retrieve messages.
	 */
	public MessageBundle(ResourceBundle messages) {
		this.messages = messages;
	}

	/**
	 * Construct a {@link MessageBundle} using the {@link ResourceBundle} with
	 * the given name.
	 * 
	 * @param bundleName the full qualified name of the {@link ResourceBundle}
	 *            
	 */
	public MessageBundle(String bundleName) {
		messages = ResourceBundle.getBundle(bundleName);
	}

	/**
	 * Construct a {@link MessageBundle} using the {@link ResourceBundle} with
	 * the given name and the given {@link Locale}.
	 * 
	 * @param bundleName the full qualified name of the {@link ResourceBundle}
	 * @param locale the locale for the messages
	 */
	public MessageBundle(String bundleName, Locale locale) {
		messages = ResourceBundle.getBundle(bundleName, locale);
	}

	/**
	 * Return the message with the given key
	 * 
	 * @param key the message key
	 * @return the message with the given key
	 * @throws MissingResourceException if there is no resource in the bundle
	 *         with the given key
	 */

	public String getErrorMessage(String key) {
		return messages.getString(key);
	}

	public String getErrorMessage(String key, String... args) {
		String result = messages.getString(key);
		result = MessageFormat.format(result, (Object[]) args);
		return replaceResources(result);
	}

	public String getErrorMessage(KeyedMessage message) {
		String result = messages.getString(message.getKey());
		for (Map.Entry<String, String> param : message.getParameters()
				.entrySet()) {
			result = StringUtils.replace(result, "{" + param.getKey() + "}",
					param.getValue().toString());
		}
		return replaceResources(result);
	}

	private String replaceResources(String message) {
		StringBuffer buffer = new StringBuffer();
		int status = 0;
		boolean escape = false;
		StringBuffer resTag = null;
		CharacterIterator iter = new StringCharacterIterator(message);
		for (char c = iter.first(); c != CharacterIterator.DONE; c = iter
				.next()) {
			if (escape) {
				if (status == 0) {
					buffer.append(c);
				} else {
					resTag.append(c);
				}
			} else {
				if (c == '{') {
					if (status == 0) {
						resTag = new StringBuffer();
						status = 1;
					} else {
						resTag.append(c);
					}
				} else if (c == '}') {
					if (status == 1) {
						try {
							buffer
									.append(messages.getString(resTag
											.toString()));
						} catch (MissingResourceException e) {
							buffer.append('{').append(resTag.toString())
									.append('}');
						}
						resTag = null;
						status = 0;
					} else {
						buffer.append(c);
					}
				} else if (c == '\\') {
					escape = true;
				} else {
					if (status == 0) {
						buffer.append(c);
					} else {
						resTag.append(c);
					}
				}
			}
		}
		return buffer.toString();
	}

}
