package it.amattioli.guidate.properties;

import it.amattioli.guidate.browsing.BrowserListCell;
import static it.amattioli.guidate.containers.BackBeans.*;

import org.apache.commons.beanutils.PropertyUtils;
import org.zkoss.zk.ui.Component;

public class PropertyNameRetriever {
	public static final String PROPERTY_NAME = "propertyName";
	public static final String PROPERTY_INDEX = "propertyIndex";
	private Component comp;
	
	public PropertyNameRetriever(Component comp) {
		this.comp = comp;
	}
	
	public String retrieve() {
		if (comp instanceof BrowserListCell) {
			return ((BrowserListCell)comp).getPropertyName();
		}
		String propertyName = (String)comp.getAttribute(PROPERTY_NAME);
		if (propertyName == null) {
			propertyName = comp.getId();
		}
		return propertyName;
	}
	
	public String retrieveComplete() {
		String result = retrieve();
		Integer propertyIndex = findPropertyIndex(comp);
		if (propertyIndex != null) {
			result = result + "[" + propertyIndex + "]";
		}
		return result;
	}
	
	public static Integer findPropertyIndex(Component comp) {
		try {
			Object propertyIndexAttribute = comp.getAttribute(PROPERTY_INDEX);
			Integer propertyIndex = null;
			if (propertyIndexAttribute instanceof Integer) {
				propertyIndex = (Integer)propertyIndexAttribute;
			} else if (propertyIndexAttribute instanceof String) {
				propertyIndex = Integer.parseInt(((String)propertyIndexAttribute));
			} else if (propertyIndexAttribute instanceof Long) {
				propertyIndex = ((Long)propertyIndexAttribute).intValue();
			}
			if (propertyIndex != null) {
				return propertyIndex;
			}
			Object stopBackBeanFinding = comp.getAttribute(STOP_FINDING);
			if (Boolean.TRUE.equals(stopBackBeanFinding) 
				|| Boolean.TRUE.toString().equals(stopBackBeanFinding)) {
				return null;
			}
			Object backBean = PropertyUtils.getProperty(comp, BACK_BEAN_ATTRIBUTE);
			if (backBean != null) {
				return null;
				 
			}
			backBean = comp.getAttribute(BACK_BEAN_ATTRIBUTE);
			if (backBean != null) {
				return null;
			}
		} catch(Exception e) {
			Object backBean = comp.getAttribute(BACK_BEAN_ATTRIBUTE);
			if (backBean != null) {
				return null;
			}
		}
		if (comp.getParent() != null) {
			return findPropertyIndex(comp.getParent());
		}
		return null;
	}
}
