package it.amattioli.dominate;

/**
 * This interface can be implemented by model classes (entities or values) that
 * publish a description.
 * User interfaces can use this description to render the object, for example,
 * in a list.
 * 
 * @author andrea
 *
 */
public interface Described {

	/**
	 * The description of this object.
	 * 
	 * @return the description of this object
	 */
	public String getDescription();
	
}
