package it.amattioli.applicate.editors;

import static it.amattioli.dominate.validation.ValidationResult.ResultType.VALID;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.Collections;

import it.amattioli.applicate.editors.BeanEditor;
import it.amattioli.dominate.properties.PropertyChangeEmitter;
import it.amattioli.dominate.validation.Constraint;
import it.amattioli.dominate.validation.DefaultValidator;
import it.amattioli.dominate.validation.ValidationResult;
import it.amattioli.dominate.validation.Validator;

/**
 * Abstract implementation of the {@link BeanEditor} interface with validation and
 * property change support.<p>
 * When the editing bean is set:
 * <ul>
 * 	<li> the editor set a listener on the editing bean so each time a property change
 *       event is fired on the editing bean, the editor will fire a similar event
 *  </li> 
 *  <li> the editor will become a {@link Validator} for the editing bean. When a call
 *       to a validating method is performed the editing bean itself will be validated
 *  </li>
 * </ul>
 * @author andrea
 *
 * @param <T>
 */
public abstract class AbstractBeanEditor<T> implements BeanEditor<T>, Validator, PropertyChangeEmitter {
	private Validator validator;
	private PropertyChangeSupport pChange = new PropertyChangeSupport(this);
	private T editingBean;
	private PropertyChangeListener editingBeanListener;
	
	@Override
	public T getEditingBean() {
		return editingBean;
	}

	@Override
	public void setEditingBean(T editingBean) {
		this.editingBean = editingBean;
		if (editingBean != null) {
			if (editingBean instanceof PropertyChangeEmitter) {
				editingBeanListener = new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
					}

				};
				((PropertyChangeEmitter)editingBean).addPropertyChangeListener(editingBeanListener);
			}
			this.validator = new DefaultValidator(editingBean);
		} else {
			this.validator = null;
		}
	}

	@Override
	public Constraint getPropertyConstraint(String propertyName, String constraintName) {
		if (validator == null) {
			return null;
		}
		return validator.getPropertyConstraint(propertyName, constraintName);
	}

	@Override
	public Collection<Constraint> getPropertyConstraints(String propertyName) {
		if (validator == null) {
			return Collections.emptyList();
		}
		return validator.getPropertyConstraints(propertyName);
	}

	@Override
	public ValidationResult validateBean() {
		if (validator != null) {
			return validator.validateBean();
		}
		return new ValidationResult(VALID, null);
	}

	@Override
	public ValidationResult validateProperty(String propertyName, Object value) {
		if (validator != null) {
			return validator.validateProperty(propertyName, value);
		}
		return new ValidationResult(VALID, null);
	}
	
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		pChange.firePropertyChange(propertyName, oldValue, newValue);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
	    pChange.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
	    pChange.removePropertyChangeListener(listener);
	}
}
