package it.amattioli.guidate.converters;

import java.util.Locale;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class LocaleConverter implements TypeConverter {

	public Object coerceToBean(Object val, Component comp) {
		// Should not be used 
		throw new UnsupportedOperationException();
	}

	public Object coerceToUi(Object val, Component comp) {
		if (val == null) {
			return "";
		} else {
			if (val instanceof Locale) {
				return ((Locale)val).getDisplayName();
			} else {
				throw new IllegalArgumentException();
			}
		}
	}

}
