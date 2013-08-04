package it.amattioli.dominate.properties;

import it.amattioli.dominate.proxies.ProxyUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

public class PropertyClassRetrieverImpl implements PropertyClassRetriever {
	private Object propertyBean;

	public PropertyClassRetrieverImpl(Object bean) {
		this.propertyBean = bean;
	}

	@Override
	public PropertyClass retrievePropertyClass(String propertyName) {
		if (propertyBean == null) {
			return null;
		}
		if (propertyBean instanceof PropertyClassRetriever) {
			return ((PropertyClassRetriever)propertyBean).retrievePropertyClass(propertyName);
		}
		boolean multiple = false;
		Class<?> propertyClass = null;
		try {
			propertyClass = PropertyUtils.getPropertyType(propertyBean, propertyName);
		} catch (IllegalAccessException e) {
			// TODO Handle exception
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			// TODO Handle exception
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new PropertyNotFoundException(propertyName, propertyBean);
		}
		if (propertyClass == null) {
			throw new PropertyNotFoundException(propertyName, propertyBean);
		}
		if (Collection.class.isAssignableFrom(propertyClass)) {
			try {
				String lastPropertyName = propertyName;
				Object bean = propertyBean;
				while (lastPropertyName.contains(".")) {
					String beanName = lastPropertyName.substring(0, lastPropertyName.indexOf("."));
					bean = PropertyUtils.getProperty(bean, beanName);
					lastPropertyName = lastPropertyName.substring(lastPropertyName.indexOf(".") + 1);
				}
				Class<? extends Object> beanClass = ProxyUtils.unProxyClass(bean.getClass());
				Method m = beanClass.getMethod("get" + StringUtils.capitalize(lastPropertyName));
				Type genericReturnType = m.getGenericReturnType();
				if (genericReturnType instanceof ParameterizedType) {
					propertyClass = (Class<?>)((ParameterizedType)genericReturnType).getActualTypeArguments()[0];
				}
				multiple = true;
			} catch (SecurityException e) {
				// TODO Handle exception
				throw new RuntimeException(e);
			} catch (NoSuchMethodException e) {
				// TODO Handle exception
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				// TODO Handle exception
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				// TODO Handle exception
				throw new RuntimeException(e);
			}
		}
		return new PropertyClass(propertyClass, multiple);
	}

}
