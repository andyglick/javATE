package it.amattioli.applicate.selection;

import java.io.Serializable;
import java.util.Collection;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Specification;

public interface FilteredSelector<I extends Serializable, T extends Entity<I>> extends Selector<T> {

	public void setSpecification(Specification<T> spec);
	
	public void select(T object);
	
	public void selectSatisfied();
	
	public Collection<T> getSelectables();
	
}
