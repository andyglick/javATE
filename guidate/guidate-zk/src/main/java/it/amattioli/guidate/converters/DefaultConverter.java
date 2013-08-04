package it.amattioli.guidate.converters;

import it.amattioli.dominate.Described;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class DefaultConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object val, Component comp) {
		return val;
	}

	@Override
	public Object coerceToUi(Object val, Component comp) {
		if (val == null) {
			return null;
		} else if (val instanceof String) {
			return (String)val;
		} else if (val instanceof Described) {
			return ((Described)val).getDescription();
		} else {
			return val.toString();
		}
	}

}