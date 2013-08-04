package it.amattioli.dominate;

import java.util.Locale;

/**
 * This interface can be implemented by model classes (entities or values) that
 * publish a localized description.
 * User interfaces can use this description to render the object, for example,
 * in a list.
 * 
 * @author giacomo
 *
 */
public interface LocalDescribed {

	/**
	 * The localized description of this object.
	 * 
	 * @param locale the specified locale
	 * @return the localized description of this object
	 */
	public String getDescription(Locale locale);
	
}
