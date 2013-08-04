package it.amattioli.guidate.converters;

import it.amattioli.guidate.config.GuidateConfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class DayConverter implements TypeConverter {
	public static final String FORMAT_ATTRIBUTE = "conversionFormat";

    public Object coerceToBean(Object val, Component comp) {
        // Should not be used 
        throw new UnsupportedOperationException();
    }

    public Object coerceToUi(Object val, Component comp) {
        if (val == null) {
            return "";
        } else {
        	String format = (String)comp.getAttribute(FORMAT_ATTRIBUTE);
        	if (StringUtils.isBlank(format)) {
        		format = GuidateConfig.instance.getFormat(this.getClass());
        	}
        	if (StringUtils.isBlank(format)) {
        		return DateFormat.getDateInstance(DateFormat.MEDIUM, Locales.getCurrent()).format(val);
        	} else {
        		return (new SimpleDateFormat(format)).format(val);
        	}
        }
    }

}
