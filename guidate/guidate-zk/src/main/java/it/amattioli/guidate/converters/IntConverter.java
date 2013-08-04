package it.amattioli.guidate.converters;

import java.text.NumberFormat;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class IntConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object val, Component comp) {
		if (StringUtils.isBlank((String)val)) {
			return null;
		} else {
			try {
				return NumberFormat.getIntegerInstance(Locales.getCurrent()).parse((String)val);
			} catch (ParseException e) {
				// TODO: Handle exception
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public Object coerceToUi(Object val, Component comp) {
		if (val == null) {
            return "";
        } else {
        	return NumberFormat.getIntegerInstance(Locales.getCurrent()).format(val);
        }
	}

}
