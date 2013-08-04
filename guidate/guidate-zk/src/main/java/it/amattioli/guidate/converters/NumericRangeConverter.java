package it.amattioli.guidate.converters;

import it.amattioli.encapsulate.range.NumericRangeFormat;

import java.text.ParseException;

import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class NumericRangeConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object val, Component comp) {
		if (val == null || val.equals("")) {
			return null;
		} else {
			NumericRangeFormat fmt = NumericRangeFormat.getInstance(Locales.getCurrent());
			try {
				return fmt.parseObject((String)val);
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
			NumericRangeFormat fmt = NumericRangeFormat.getInstance(Locales.getCurrent());
			return fmt.format(val);
		}
	}

}
