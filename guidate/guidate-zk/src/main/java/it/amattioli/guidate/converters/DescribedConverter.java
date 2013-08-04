package it.amattioli.guidate.converters;

import it.amattioli.dominate.Described;
import it.amattioli.dominate.LocalDescribed;

import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class DescribedConverter implements TypeConverter {
	
	public DescribedConverter() {
		
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
		return val.toString();
	}

}
