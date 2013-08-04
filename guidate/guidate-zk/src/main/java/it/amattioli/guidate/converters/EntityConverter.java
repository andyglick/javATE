package it.amattioli.guidate.converters;

import it.amattioli.dominate.Described;
import it.amattioli.dominate.LocalDescribed;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class EntityConverter implements TypeConverter {
	private static final String DEFAULT_DESCRIPTION_PROPERTY = "description";
	private static final String DESCRIPTION_PROPERTY_ATTRIBUTE = "descriptionProperty";
	private String propertyName;
	
	public EntityConverter() {
		
	}
	
	public EntityConverter(String propertyName) {
		setPropertyName(propertyName);
	}

	@Override
	public Object coerceToBean(Object val, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object coerceToUi(Object val, Component comp) {
		if (val == null) {
			return "";
		}
		if (val instanceof LocalDescribed) {
			return ((LocalDescribed)val).getDescription(Locales.getCurrent());
		}
		if (val instanceof Described) {
			return ((Described)val).getDescription();
		}
		if (getPropertyName() != null) {
			try {
				return BeanUtils.getProperty(val, getPropertyName(comp));
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e);
			}
		}
		return val.toString();
	}
	
	public String getPropertyName(Component comp) {
		if (propertyName == null) {
			propertyName = (String)comp.getAttribute(DESCRIPTION_PROPERTY_ATTRIBUTE);
			if (propertyName == null) {
				propertyName = DEFAULT_DESCRIPTION_PROPERTY;
			}
		}
		return getPropertyName();
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

}
