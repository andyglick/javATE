package it.amattioli.applicate.editors;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import it.amattioli.dominate.properties.CheckPropertyChangeListener;
import it.amattioli.dominate.properties.PropertyAvailabilityRetriever;
import it.amattioli.dominate.properties.PropertyAvailabilityRetrieverImpl;
import it.amattioli.dominate.properties.PropertyChangeSupport;
import it.amattioli.dominate.properties.PropertyClass;
import it.amattioli.dominate.properties.PropertyClassRetriever;
import it.amattioli.dominate.properties.PropertyClassRetrieverImpl;
import it.amattioli.dominate.properties.PropertyWritabilityRetriever;
import it.amattioli.dominate.properties.PropertyWritabilityRetrieverImpl;
import it.amattioli.dominate.properties.ValuesLister;
import it.amattioli.dominate.properties.ValuesListerImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import it.amattioli.dominate.properties.PropertyChangeEmitter;
import it.amattioli.dominate.validation.Constraint;
import it.amattioli.dominate.validation.DefaultValidator;
import it.amattioli.dominate.validation.ValidationResult;
import static it.amattioli.dominate.validation.ValidationResult.ResultType.*;
import it.amattioli.dominate.validation.Validator;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.lang.StringUtils;

public class BeanEditorImpl<T> implements BeanEditor<T>, DynaBean, PropertyChangeEmitter, PropertyClassRetriever, Validator, ValuesLister, PropertyWritabilityRetriever, PropertyAvailabilityRetriever {
	private T editingBean;
	private DynaBean wrapper;
	private PropertyChangeSupport pChange = new PropertyChangeSupport(this);
	private ValuesLister valuesLister;
	private Validator validator;
	private PropertyChangeListener editingBeanListener;
	private PropertyWritabilityRetriever writabilityRetriever;
	private PropertyAvailabilityRetriever availabilityRetriever;

	public BeanEditorImpl() {
	}

	public BeanEditorImpl(T editingBean) {
		setEditingBean(editingBean);
		
	}

	public T getEditingBean() {
		return editingBean;
	}

	public void setEditingBean(T editingBean) {
		this.editingBean = editingBean;
		if (editingBean != null) {
			this.wrapper = new EditingBeanWrapper(editingBean);
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
			this.valuesLister = new ValuesListerImpl(editingBean);
			this.availabilityRetriever = new PropertyAvailabilityRetrieverImpl(editingBean);
			this.writabilityRetriever = new PropertyWritabilityRetrieverImpl(editingBean);
		} else {
			this.wrapper = null;
			this.validator = null;
			this.valuesLister = null;
			this.availabilityRetriever = null;
			this.writabilityRetriever = null;
		}
	}

	@Override
	public PropertyClass retrievePropertyClass(String propertyName) {
		return new PropertyClassRetrieverImpl(getEditingBean()).retrievePropertyClass(propertyName);
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

	private void checkNullWrapper() {
		if (wrapper == null) {
			throw new IllegalStateException();
		}
	}

	protected DynaBean getWrapper() {
		return wrapper;
	}

	public boolean contains(String name, String key) {
		checkNullWrapper();
		return wrapper.contains(name, key);
	}

	public Object get(String name, int index) {
		checkNullWrapper();
		if (existsCustomGetter(name)) {
			return customGet(name, index);
		} else {
			return wrapper.get(name, index);
		}
	}

	public Object get(String name, String key) {
		checkNullWrapper();
		if (existsCustomGetter(name)) {
			return customGet(name, key);
		} else {
			return wrapper.get(name, key);
		}
	}

	public Object get(String name) {
		checkNullWrapper();
		if (existsCustomGetter(name)) {
			return customGet(name);
		} else {
			return wrapper.get(name);
		}
	}

	public DynaClass getDynaClass() {
		checkNullWrapper();
		return wrapper.getDynaClass();
	}

	public void remove(String name, String key) {
		checkNullWrapper();
		wrapper.remove(name, key);
	}

	public void set(String name, int index, Object value) {
		checkNullWrapper();
		CheckPropertyChangeListener listener = new CheckPropertyChangeListener(name);
		if (editingBean instanceof PropertyChangeEmitter) {
			((PropertyChangeEmitter)editingBean).addPropertyChangeListener(listener);
		}
		wrapper.set(name, index, value);
		if (!listener.isPropertyChangeNotified()) {
			firePropertyChange(name, null, value);
		}
		if (editingBean instanceof PropertyChangeEmitter) {
			((PropertyChangeEmitter)editingBean).removePropertyChangeListener(listener);
		}
	}

	public void set(String name, Object value) {
		checkNullWrapper();
		CheckPropertyChangeListener listener = new CheckPropertyChangeListener(name);
		if (editingBean instanceof PropertyChangeEmitter) {
			((PropertyChangeEmitter)editingBean).addPropertyChangeListener(listener);
		}
		if (existsCustomSetter(name)) {
			customSet(name, value);
		} else {
			wrapper.set(name, value);
		}
		if (!listener.isPropertyChangeNotified()) {
			firePropertyChange(name, null, value);
		}
		if (editingBean instanceof PropertyChangeEmitter) {
			((PropertyChangeEmitter)editingBean).removePropertyChangeListener(listener);
		}
	}

	public void set(String name, String key, Object value) {
		checkNullWrapper();
		CheckPropertyChangeListener listener = new CheckPropertyChangeListener(name);
		if (editingBean instanceof PropertyChangeEmitter) {
			((PropertyChangeEmitter)editingBean).addPropertyChangeListener(listener);
		}
		wrapper.set(name, key, value);
		if (!listener.isPropertyChangeNotified()) {
			firePropertyChange(name, null, value);
		}
		if (editingBean instanceof PropertyChangeEmitter) {
			((PropertyChangeEmitter)editingBean).removePropertyChangeListener(listener);
		}
	}

	private Method findCustomSetter(String property) {
		String setterName = "set" + StringUtils.capitalize(property);
		for (Method curr : getClass().getMethods()) {
			if (curr.getName().equals(setterName)) {
				return curr;
			}
		}
		return null;
	}

	private boolean existsCustomGetter(String property) {
		try {
			getClass().getMethod("get" + StringUtils.capitalize(property));
			return true;
		} catch (NoSuchMethodException e) {
			return false;
		}
	}

	private boolean existsCustomSetter(String property) {
		return findCustomSetter(property) != null;
	}

	private Object customGet(String property) {
		try {
			return getClass().getMethod("get" + StringUtils.capitalize(property)).invoke(this);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	private Object customGet(String property, int i) {
		Object[] arr = (Object[])customGet(property);
		return arr[i];
	}

	private Object customGet(String property, String key) {
		Map<String, ?> map = (Map<String, ?>)customGet(property);
		return map.get(key);
	}

	private void customSet(String property, Object value) {
		try {
			findCustomSetter(property).invoke(this, value);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
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

	@Override
	public Collection<?> getPropertyValues(String propertyName) {
		if (valuesLister == null) {
			return Collections.emptyList();
		}
		return valuesLister.getPropertyValues(propertyName);
	}

	@Override
	public boolean isPropertyWritable(String propertyName) {
		if (writabilityRetriever == null) {
			return false;
		}
		return writabilityRetriever.isPropertyWritable(propertyName);
	}

	@Override
	public boolean isPropertyAvailable(String propertyName) {
		if (availabilityRetriever == null) {
			return false;
		}
		return availabilityRetriever.isPropertyAvailable(propertyName);
	}
}
