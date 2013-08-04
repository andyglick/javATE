package it.amattioli.guidate.collections;

import it.amattioli.dominate.properties.PropertyChangeEmitter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.event.ListDataEvent;

public class CollectionPropertyModel extends AbstractListModel {
	private static final long serialVersionUID = 1L;
	private Object backBean;
	private String propertyName;
	private PropertyChangeListener propertyChangeListener;

	public CollectionPropertyModel(final Object bean, final String propertyName) {
		this.setBackBean(bean);
		this.propertyName = propertyName;
		if (backBean instanceof PropertyChangeEmitter) {
    		propertyChangeListener = new PropertyChangeListener() {
    
    			@Override
    			public void propertyChange(PropertyChangeEvent evt) {
    				if (evt.getPropertyName() == null
    						|| propertyName.equals(evt.getPropertyName())
    						|| propertyName.startsWith(evt.getPropertyName()+".")) {
    					fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
    				}
    			}
            	
            };
			((PropertyChangeEmitter)backBean).addPropertyChangeListener(propertyChangeListener);
		}
	}

	private void setBackBean(Object backBean) {
		this.backBean = backBean;
	}

	private Object getBackBean() {
		return backBean;
	}
    
	@Override
	public Object getElementAt(int idx) {
		return getModelValues().get(idx);
	}

	@Override
	public int getSize() {
		return getModelValues().size();
	}

	protected List<?> getModelValues() {
		if (getBackBean() == null) {
			return Collections.emptyList();
		}
		try {
			Collection<?> values = (Collection<?>)PropertyUtils.getProperty(getBackBean(), propertyName);
			if (values instanceof List) {
				return (List<?>)values;
			} else {
				return new ArrayList(values);
			}
		} catch (IllegalAccessException e) {
			// TODO Handle exception
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			// TODO Handle exception
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			return Collections.emptyList();
		}
	}
	
}
