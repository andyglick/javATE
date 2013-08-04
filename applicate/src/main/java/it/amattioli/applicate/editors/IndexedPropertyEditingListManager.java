package it.amattioli.applicate.editors;

import it.amattioli.dominate.properties.PropertyChangeSupport;

import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

/**
 * An {@link EditingListManager} implementation that support the editing of a list contained
 * in another object.
 * 
 * The containing object must have accessor methods for the list that implements the following
 * pattern:
 * 
 * <ui>
 *   <li> a getPropertyName(int index) method for reading the elements of the list 
 *   <li> a addPropertyName(PropertyClass toBeAdded) method for adding a new element to the list
 *   <li> a removePropertyName(PropertyClass toBeRemoved) method for removing an element from the list
 * </ui>
 * 
 * @author andrea
 *
 * @param <T> the class of the objects contained in the list that is being edited
 */
public class IndexedPropertyEditingListManager<T> implements EditingListManager<T> {
	private PropertyChangeSupport pChange = new PropertyChangeSupport(this);
	private Object bean;
	private String propertyName;
	private Class<T> elementClass;
	private Method addMethod;
	private Method removeMethod;
	
	public IndexedPropertyEditingListManager(Object bean, String propertyName) {
		this.bean = bean;
		this.propertyName = propertyName;
		try {
			this.elementClass = (Class<T>)PropertyUtils.getPropertyType(bean, propertyName);
			this.addMethod = bean.getClass().getMethod("add"+StringUtils.capitalize(propertyName), elementClass);
			this.removeMethod = bean.getClass().getMethod("remove"+StringUtils.capitalize(propertyName), elementClass);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public List<T> getEditingList() {
		List<T> result = new ArrayList<T>();
		int index = 0;
		while(true) {
			try {
				result.add(getElement(index));
			} catch(IndexOutOfBoundsException e) {
				return result;
			}
			index++;
		}
	}

	protected T getElement(int index) {
		try {
			return (T)PropertyUtils.getIndexedProperty(bean, propertyName, index);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setEditingList(List<T> editingList) {
		throw new UnsupportedOperationException();
	}
	
	public BeanEditor<T> createElementEditor(T curr) {
		return new BeanEditorImpl<T>(curr);
	}
	
	protected T createObject() {
		try {
			return elementClass.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public BeanEditor<T> addRow(Object... param) {
		T newElement = createObject();
		try {
			addMethod.invoke(bean, newElement);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} 
		return createElementEditor(newElement);
	}
	
	@Override
	public boolean canDeleteRow(int idx) {
		return idx >= 0 && idx < getEditingList().size();
	}

	@Override
	public T deleteRow(int idx) {
		T toBeDeleted = getElement(idx);
		try {
			removeMethod.invoke(bean, toBeDeleted);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} 
		return toBeDeleted; 
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		pChange.firePropertyChange(propertyName, oldValue, newValue);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
	    pChange.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
	    pChange.removePropertyChangeListener(listener);
	}
}
