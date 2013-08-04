package it.amattioli.guidate.converters;

import it.amattioli.guidate.config.GuidateConfig;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Decimalbox;

public class DecimalConverter implements TypeConverter {
	public static final String FORMAT_ATTRIBUTE = "conversionFormat";

	@Override
	public Object coerceToBean(Object val, Component comp) {
		if (val instanceof BigDecimal) {
			return val;
		}
		if (StringUtils.isBlank((String)val)) {
			return null;
		} else {
			try {
				return new BigDecimal(NumberFormat.getInstance(Locales.getCurrent()).parse((String)val).toString());
			} catch (ParseException e) {
				// TODO: Handle exception
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public Object coerceToUi(Object val, Component comp) {
		if (comp instanceof Decimalbox) {
			return val;
		}
		if (val == null) {
            return "";
        } else {
        	String format = (String)comp.getAttribute(FORMAT_ATTRIBUTE);
        	if (StringUtils.isBlank(format)) {
        		format = GuidateConfig.instance.getFormat(this.getClass());
        	}
        	if (StringUtils.isBlank(format)) {
        		return NumberFormat.getInstance(Locales.getCurrent()).format(val);
        	} else {
        		return new DecimalFormat(format).format(val);
        	}
        }
	}

}
