package it.amattioli.guidate.properties;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Specification;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.SpecificationChangeListener;

import java.beans.PropertyChangeListener;

public class GroupSpecification implements Specification<Entity<?>> {
	@Override
	public void assembleQuery(Assembler assembler) {
		// TODO Auto-generated method stub
		
	}

	public boolean isSatisfiedBy(Entity<?> object) {
		return !(object instanceof Entity<?>);
	}

	@Override
	public void setSatisfiedIfNotSet(boolean value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean supportsAssembler(Assembler assembler) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean wasSet() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSpecificationChangeListener(SpecificationChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeSpecificationChangeListener(SpecificationChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fireSpecificationChange() {
		// TODO Auto-generated method stub
		
	}
}