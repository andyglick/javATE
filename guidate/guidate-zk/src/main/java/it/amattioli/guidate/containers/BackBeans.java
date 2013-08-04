package it.amattioli.guidate.containers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import it.amattioli.guidate.util.DesktopAttributes;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;

public class BackBeans {
	private static final String PARENT_BEAN_PREFIX = "parentBean.";
	public static final String STOP_FINDING = "stopBackBeanFinding";
	public static final String BACK_BEAN_ATTRIBUTE = "backBean";
	public static final String BACK_BEAN_ATTRIBUTE_PREFIX = "backBean.";
	public static final String BACK_BEAN_VAR = "it.amattioli.guidate.backbean";
	
	private BackBeans() {
		
	}
	
	public static Component findContainer(Component comp) {
		try {
			Object stopBackBeanFinding = comp.getAttribute(STOP_FINDING);
			if (Boolean.TRUE.equals(stopBackBeanFinding) 
				|| Boolean.TRUE.toString().equals(stopBackBeanFinding)) {
				return null;
			}
			Object backBean = PropertyUtils.getProperty(comp, BACK_BEAN_ATTRIBUTE);
			if (backBean != null) {
				return comp;
				 
			}
			backBean = comp.getAttribute(BACK_BEAN_ATTRIBUTE);
			if (backBean != null) {
				return comp;
			}
			if (comp.getParent() != null) {
				return findContainer(comp.getParent());
			}
			return null;
		} catch(Exception e) {
			Object backBean = comp.getAttribute(BACK_BEAN_ATTRIBUTE);
			if (backBean != null) {
				return comp;
			}
			if (comp.getParent() != null) {
				return findContainer(comp.getParent());
			}
			return null;
		}
	}
	
	public static Object findBackBean(Component comp) {
		Component container = findContainer(comp);
		if (container == null) {
			return null;
		}
		try {
			Object result = PropertyUtils.getProperty(container, BACK_BEAN_ATTRIBUTE);
			if (result instanceof String) {
				result = backBeanFromString(comp, (String)result);
				PropertyUtils.setProperty(container, BACK_BEAN_ATTRIBUTE, result);
			}
			if (result == null) {
				result = noPropertyBackBean(container);
			}
			return result;
		} catch(Exception e) {
			return noPropertyBackBean(container);
		}
	}
	
	public static Object findTargetBackBean(Event evt) {
		Component target = evt.getTarget();
		if (target == null) {
			throw new NullPointerException("Event has no target component");
		}
		return findBackBean(target);
	}
	
	public static Component findParentContainer(Component comp) {
		Component containerParent = findContainer(comp).getParent();
		if (containerParent == null) {
			return null;
		}
		return findContainer(containerParent);
	}
	
	public static Object findParentBean(Component comp) {
		return findBackBean(findParentContainer(comp));
	}
	
	private static Object noPropertyBackBean(Component comp) {
		Object result = comp.getAttribute(BACK_BEAN_ATTRIBUTE);
		if (result instanceof String) {
			result = backBeanFromString(comp, (String)result);
			comp.setAttribute(BACK_BEAN_ATTRIBUTE, result);
		}
		if (result == null) {
			result = comp.getVariable(BACK_BEAN_VAR, false);
			if (result instanceof String) {
				result = backBeanFromString(comp, (String)result);
				comp.setVariable(BACK_BEAN_ATTRIBUTE, result, false);
			}
		}
		return result;
	}
	
	private static Object backBeanFromString(Component comp, String serviceName) {
		if (serviceName.startsWith(PARENT_BEAN_PREFIX)) {
			Object parentBean = findBackBean(comp.getParent());
			if (parentBean == null) {
				return null;
			}
			String propertyName = serviceName.substring(PARENT_BEAN_PREFIX.length());
			Object result;
			try {
				result = PropertyUtils.getProperty(parentBean, propertyName);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
			return result;
		} else {
		    Object service = DesktopAttributes.getSession(comp).createService(serviceName);
		    setBackBeanAttributes(comp, service);
			return service;
		}
    }
	
	private static void setBackBeanAttributes(Component comp, Object backBean) {
		for (Object curr: comp.getAttributes().keySet()) {
			String attrName = (String)curr;
			if (attrName.startsWith(BACK_BEAN_ATTRIBUTE_PREFIX)) {
				String propertyName = attrName.substring(BACK_BEAN_ATTRIBUTE_PREFIX.length());
				Object propertyValue = comp.getAttributes().get(attrName);
				try {
					PropertyUtils.setProperty(backBean, propertyName, propertyValue);
				} catch (Exception e) {
					throw new RuntimeException(e);
					/*
					Method[] methods = backBean.getClass().getMethods();
					for (Method method : methods) {
						if (method.getName().equals("set"+StringUtils.capitalize(propertyName))) {
							try {
								method.invoke(backBean, propertyValue);
							} catch (IllegalArgumentException e1) {
								// TODO Handle exception
								throw new RuntimeException(e1);
							} catch (IllegalAccessException e1) {
								// TODO Handle exception
								throw new RuntimeException(e1);
							} catch (InvocationTargetException e1) {
								// TODO Handle exception
								throw new RuntimeException(e1);
							}
						}
					} 
					*/
				}
			}
		}
	}
}
