package it.amattioli.dominate.specifications;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import it.amattioli.dominate.Entity;

public abstract class ExclusionListSpecification<T extends Entity<?>> extends ChainedSpecification<T> {
	private Collection<T> exclusionList = new HashSet<T>();
	
	public static <T extends Entity<?>> ExclusionListSpecification<T> newInstance() {
		ExclusionListSpecification<T> result = ChainedSpecification.createChain(ExclusionListSpecification.class);
		return result;
	}
	
	public ExclusionListSpecification() {
		
	}
	
	public ExclusionListSpecification(ExclusionListSpecification<T> chained) {
		super(chained);
	}
	
	public Collection<T> getExclusionList() {
		return Collections.unmodifiableCollection(exclusionList);
	}
	
	public void addToExclusionList(T entity) {
		exclusionList.add(entity);
		if (getNextInChain() != null) {
			((ExclusionListSpecification<T>)getNextInChain()).addToExclusionList(entity);
		}
		fireSpecificationChange();
	}
	
	public void removeFromExclusionList(T entity) {
		exclusionList.remove(entity);
		if (getNextInChain() != null) {
			((ExclusionListSpecification<T>)getNextInChain()).removeFromExclusionList(entity);
		}
		fireSpecificationChange();
	}
	
	@Override
	public boolean isSatisfiedBy(T entity) {
		if (!wasSet()) {
			return isSatisfiedIfNotSet();
		}
		return !exclusionList.contains(entity);
	}
	
	public boolean wasSet() {
		return !getExclusionList().isEmpty();
	}
}
