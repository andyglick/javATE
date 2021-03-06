package it.amattioli.guidate.collections;

import it.amattioli.dominate.groups.EntityGroup;
import it.amattioli.dominate.properties.PropertyChangeEmitter;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.zkoss.zul.AbstractGroupsModel;
import org.zkoss.zul.event.GroupsDataEvent;

public class GroupPropertyModel extends AbstractGroupsModel {

	private static final long serialVersionUID = 1L;
	private Object backBean;
	private String propertyName;
	private PropertyChangeListener propertyChangeListener;

	public GroupPropertyModel(final Object bean, final String propertyName) {
		this.setBackBean(bean);
		this.propertyName = propertyName;
		if (backBean instanceof PropertyChangeEmitter) {
    		propertyChangeListener = new PropertyChangeListener() {
    
    			@Override
    			public void propertyChange(PropertyChangeEvent evt) {
    				if (evt.getPropertyName() == null
    						|| propertyName.equals(evt.getPropertyName())
    						|| propertyName.startsWith(evt.getPropertyName()+".")) {
    					fireEvent(GroupsDataEvent.GROUPS_CHANGED, -1, -1, -1);
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
    
	protected List<?> getModelValues() {
		if (getBackBean() == null) {
			return Collections.emptyList();
		}
		try {
			Collection<?> values = (Collection<?>)PropertyUtils.getProperty(getBackBean(), propertyName);
			if (values instanceof List<?>) {
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
	
	@Override
	public Object getChild(int groupIdx, int childIdx) {
		return getGroup(groupIdx).getMember(childIdx);
	}

	@Override
	public int getChildCount(int groupIdx) {
		return getGroup(groupIdx).size();
	}

	@Override
	public EntityGroup<?, ?> getGroup(int idx) {
		return (EntityGroup<?, ?>)getModelValues().get(idx);
	}

	@Override
	public int getGroupCount() {
		return getModelValues().size();
	}

	@Override
	public boolean isClose(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setClose(int arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getGroupfoot(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasGroupfoot(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
