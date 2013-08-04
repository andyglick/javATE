package it.amattioli.guidate.converters;

import it.amattioli.dominate.Described;
import it.amattioli.dominate.LocalDescribed;

import org.zkoss.util.Locales;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class EnumConverter implements TypeConverter {

	public Object coerceToBean(Object val, Component comp) {
		// Should not be used 
		throw new UnsupportedOperationException();
	}

	public Object coerceToUi(Object val, Component comp) {
		if (val == null) {
			return "";
		} else {
			if (val instanceof LocalDescribed) {
				return ((LocalDescribed)val).getDescription(Locales.getCurrent());
			}
			if (val instanceof Described) {
				return ((Described)val).getDescription();
			}
			String result = Labels.getLabel(val.getClass().getName()+"."+val.toString());
			if (result == null || "".equals(result)) {
				result = val.toString();
			}
			return result;
		}
	}

}
