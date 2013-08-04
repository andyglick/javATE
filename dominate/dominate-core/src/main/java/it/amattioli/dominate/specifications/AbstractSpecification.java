package it.amattioli.dominate.specifications;

import java.beans.PropertyChangeListener;
import it.amattioli.dominate.properties.PropertyChangeSupport;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Specification;

public abstract class AbstractSpecification<T extends Entity<?>> implements Specification<T> {
	private SpecificationChangeSupport specChange = new SpecificationChangeSupport();
	private PropertyChangeSupport pChange = new PropertyChangeSupport(this);
	private boolean satisfiedIfNotSet = true;

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        pChange.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        pChange.removePropertyChangeListener(listener);
    }

    public void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue) {
        pChange.firePropertyChange(propertyName, oldValue, newValue);
    }

	@Override
	public void addSpecificationChangeListener(SpecificationChangeListener listener) {
		specChange.addListener(listener);
	}

	@Override
	public void removeSpecificationChangeListener(SpecificationChangeListener listener) {
		specChange.removeListener(listener);
	}
	
	@Override
	public void fireSpecificationChange() {
		specChange.fireSpecificationEvent(new SpecificationChangeEvent(this));
	}

	@Override
	public void setSatisfiedIfNotSet(boolean value) {
		this.satisfiedIfNotSet = value;
	}
	
	protected boolean isSatisfiedIfNotSet() {
		return satisfiedIfNotSet;
	}

}
