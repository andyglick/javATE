package it.amattioli.dominate.specifications;

import java.util.EventListener;

/**
 * A listener that can be added to a specification to listen for its change.
 * It can be used, for example, by browsers to trigger content reload.
 *
 */
public interface SpecificationChangeListener extends EventListener {

	/**
	 * This method will be called every time a {@link SpecificationChangeEvent} is fired
	 * by the specification to which this listener has been added.
	 * 
	 * @param event the fired event
	 */
	public void specificationChange(SpecificationChangeEvent event);
	
}
