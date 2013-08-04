package it.amattioli.workstate.config;

import it.amattioli.workstate.core.*;

/**
 * A builder that implements this interface builds objects with associated
 * attributes
 * 
 */
public interface AttributeOwnerBuilder {

	/**
	 * Add an attribute definition to the building object.
	 * 
	 * @param attr the attribute definition
	 *            
	 */
	public void addAttribute(MetaAttribute attr);

}
