package it.amattioli.guidate.converters;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class NullConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object val, Component comp) {
		return val;
	}

	@Override
	public Object coerceToUi(Object val, Component comp) {
		return val;
	}

}
