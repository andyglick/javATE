package it.amattioli.dominate.properties;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

/**
 * An adapter that allow to access an indexed property of a bean as a regular
 * List instance.<p>
 * 
 * The property accessor methods must follow the following naming pattern:
 * <ul>
 *   <li>reading method: getPropertyName(int index)
 *   <li>add row method: addPropertyName(PropertyClass toBeAdded)
 *   <li>delete row method: removePropertyName(PropertyClass toBeRemoved)
 * </ul>
 * 
 * @author andrea
 *
 * @param <T>
 */
public class IndexedPropertyAdapter<T> extends AbstractList<T> {
	private Object bean;
	private String propertyName;
	private Class<T> elementClass;
	private Method addMethod;
	private Method removeMethod;

	public IndexedPropertyAdapter(Object bean, String propertyName) {
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
	public T get(int index) {
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
	
	public List<T> getEditingList() {
		List<T> result = new ArrayList<T>();
		int index = 0;
		while(true) {
			try {
				result.add(get(index));
			} catch(IndexOutOfBoundsException e) {
				return result;
			}
			index++;
		}
	}

	@Override
	public int size() {
		return getEditingList().size();
	}

	@Override
	public void add(int index, T element) {
		try {
			addMethod.invoke(bean, element);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T remove(int index) {
		T toBeDeleted = get(index);
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

}
