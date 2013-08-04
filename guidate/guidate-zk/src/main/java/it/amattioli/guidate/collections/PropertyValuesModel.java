package it.amattioli.guidate.collections;

import it.amattioli.dominate.properties.PropertyChangeEmitter;
import it.amattioli.dominate.properties.ValuesLister;
import it.amattioli.dominate.properties.ValuesListerImpl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.event.ListDataEvent;

public class PropertyValuesModel extends AbstractListModel {
	private static final long serialVersionUID = 1L;
	private Object backBean;
	private String propertyName;
	private boolean nullValue;
	private List<?> values;
	private ValuesLister valuesLister;
	private PropertyChangeListener propertyChangeListener;

	public PropertyValuesModel(Object bean, String propName, boolean nullValue) {
		this.setBackBean(bean);
		this.propertyName = propName;
		this.nullValue = nullValue;
		if (backBean instanceof PropertyChangeEmitter) {
    		propertyChangeListener = new PropertyChangeListener() {
    
    			@Override
    			public void propertyChange(PropertyChangeEvent evt) {
    				if (ValuesListerImpl.valuesProperty(propertyName).equals(evt.getPropertyName())) {
    					values = null;
    					fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
    				}
    			}
            	
            };
			((PropertyChangeEmitter)backBean).addPropertyChangeListener(propertyChangeListener);
		}
	}

	private void setBackBean(Object backBean) {
		this.backBean = backBean;
		if (backBean == null) {
			values = Collections.emptyList();
		} else if (backBean instanceof ValuesLister) {
			valuesLister = (ValuesLister)backBean;
		} else {
			valuesLister = new ValuesListerImpl(backBean);
		}
	}

	private Object getBackBean() {
		return backBean;
	}
    
	@Override
	public Object getElementAt(int idx) {
		return getValues().get(idx);
	}

	@Override
	public int getSize() {
		return getValues().size();
	}
	
	public List<?> getValues() {
		if (this.values == null) {
    		ArrayList<?> result = new ArrayList<Object>(valuesLister.getPropertyValues(propertyName));
    		if (nullValue && !result.contains(null)) {
    			result.add(0, null);
    		}
    		this.values = result;
		}
		return this.values;
	}

}
