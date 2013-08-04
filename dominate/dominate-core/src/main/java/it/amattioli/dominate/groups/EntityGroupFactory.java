package it.amattioli.dominate.groups;

import it.amattioli.dominate.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Objects that implement this interface can be used to create a set of {@link EntityGroup}.
 * The rule used to actually create the groups is implementation dependent.
 * 
 * @author andrea
 *
 * @param <I>
 * @param <T>
 */
public interface EntityGroupFactory<I extends Serializable, T extends Entity<I>> {

	/**
	 * Create a set of group.
	 * 
	 * @return
	 */
	public List<EntityGroup<I,T>> createGroups();
	
}
