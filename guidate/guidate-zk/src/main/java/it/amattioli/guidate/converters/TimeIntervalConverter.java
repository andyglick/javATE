package it.amattioli.guidate.converters;

import it.amattioli.encapsulate.dates.TimeInterval;
import it.amattioli.encapsulate.dates.format.TimeIntervalFormat;

import java.text.ParseException;

import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class TimeIntervalConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object val, Component comp) {
		if (val == null || val.equals("")) {
			return null;
		} else {
			TimeIntervalFormat fmt = TimeIntervalFormat.getInstance(Locales.getCurrent());
			try {
				return fmt.parse((String)val);
			} catch(ParseException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public Object coerceToUi(Object val, Component comp) {
		if (val == null) {
			return "";
		} else {
			TimeIntervalFormat fmt = TimeIntervalFormat.getInstance(Locales.getCurrent());
			return fmt.format((TimeInterval)val);
		}
	}

}
