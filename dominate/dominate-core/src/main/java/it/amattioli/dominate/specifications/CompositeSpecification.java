package it.amattioli.dominate.specifications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Specification;

public abstract class CompositeSpecification<T extends Entity<?>> extends AbstractSpecification<T> {
	private Collection<Specification<T>> specifications = new ArrayList<Specification<T>>();
	private boolean autoFilteringEnabled = false;
	private SpecificationChangeListener subSpecificationListener = new SpecificationChangeListener() {
		
		@Override
		public void specificationChange(SpecificationChangeEvent event) {
			if (isAutoFilteringEnabled()) {
				fireSpecificationChange();
			}
		}
	};

	public CompositeSpecification() {
		
	}
	
	public CompositeSpecification(Specification<T>... specs) {
		specifications.addAll(Arrays.asList(specs));
		for (Specification<T> curr: specs) {
			curr.addSpecificationChangeListener(subSpecificationListener);
		}
	}
	
	public void addSpecification(Specification<T> spec) {
		specifications.add(spec);
		spec.addSpecificationChangeListener(subSpecificationListener);
	}
	
	public void removeSpecification(Specification<T> spec) {
		specifications.remove(spec);
		spec.removeSpecificationChangeListener(subSpecificationListener);
	}

	protected Collection<Specification<T>> getSpecifications() {
		return specifications;
	}
	
	@Override
	public boolean supportsAssembler(Assembler assembler) {
		for (Specification<T> spec : specifications) {
			if (!spec.supportsAssembler(assembler)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean wasSet() {
		for (Specification<T> spec : specifications) {
			if (spec.wasSet()) {
				return true;
			}
		}
		return false;
	}

	public boolean isAutoFilteringEnabled() {
		return autoFilteringEnabled;
	}

	public void setAutoFilteringEnabled(boolean autoFilteringEnabled) {
		this.autoFilteringEnabled = autoFilteringEnabled;
	}
	
}
