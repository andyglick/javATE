package it.amattioli.guidate.converters;

import java.util.ResourceBundle;

import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class YesNoConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object val, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object coerceToUi(Object val, Component comp) {
		if (val == null) {
            return "";
        } else {
        	return getResources().getString(val.toString());
        }
	}

	private ResourceBundle getResources() {
		return ResourceBundle.getBundle("it.amattioli.guidate.converters.YesNo", Locales.getCurrent());
	}

}
