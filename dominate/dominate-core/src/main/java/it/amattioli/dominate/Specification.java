package it.amattioli.dominate;

import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.SpecificationChangeListener;
import it.amattioli.dominate.properties.PropertyChangeEmitter;

/**
 * An object that implements this interface can check if an entity satisfies or
 * not certain conditions.
 * A specification can also be used to assemble a query object (using an {@link Assembler})
 * that can be used to filter the results returned by {@link Repository#list(Specification)}
 * 
 * @author andrea
 *
 * @param <T> the class of the entities this specification can check
 */
public interface Specification <T extends Entity<?>> extends PropertyChangeEmitter {

	/**
	 * Check if an object satisfies this specification.
	 * 
	 * @param object
	 * @return
	 */
	public boolean isSatisfiedBy(T object);
	
	/**
	 * Assemble a query object that can be used with a repository to filter
	 * the objects returned by {@link Repository#list(Specification)}
	 * 
	 * @param assembler
	 */
	public void assembleQuery(Assembler assembler);
	
	/**
	 * Check if the passed query assembler can be passed to the {@link #assembleQuery(Assembler)}
	 * of thie specification
	 * 
	 * @param assembler
	 * @return
	 */
	public boolean supportsAssembler(Assembler assembler);
	
	/**
	 * If this specification is parametric check if the parameter(s) has been set.
	 * If this specification is not parametric always returns true.
	 *  
	 * @return
	 */
	public boolean wasSet();
	
	/**
	 * For a parametric specification sets the value to be returned by {@link #isSatisfiedBy(Entity)}
	 * when the parameter(s) has not been set.
	 * 
	 * @param value
	 */
	public void setSatisfiedIfNotSet(boolean value);
	
	/**
	 * Add a listener for specification change events.
	 * 
	 * @param listener the listener to be added
	 */
	public void addSpecificationChangeListener(SpecificationChangeListener listener);
    
	/**
	 * Remove a listener previously added using 
	 * {@link #addSpecificationChangeListener(SpecificationChangeListener)}
	 * 
	 * @param listener the listener to be removed
	 */
    public void removeSpecificationChangeListener(SpecificationChangeListener listener);
    
    public void fireSpecificationChange();
}
