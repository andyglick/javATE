package it.amattioli.guidate.converters;

import it.amattioli.encapsulate.money.Money;
import it.amattioli.encapsulate.money.MoneyFormat;
import it.amattioli.guidate.config.GuidateConfig;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Decimalbox;

public class MoneyConverter implements TypeConverter {
	
	@Override
	public Object coerceToBean(Object val, Component comp) {
		if (val instanceof Money) {
			return val;
		}
		if (val instanceof Number) {
			return new Money(new BigDecimal(val.toString()), Currency.getInstance(Locale.getDefault()));
		}
		if (StringUtils.isBlank((String)val)) {
			return null;
		} else {
			try {
				return MoneyFormat.getInstance().parse((String)val);
			} catch (ParseException e) {
				// TODO: Handle exception
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public Object coerceToUi(Object val, Component comp) {
		if (val == null) {
            return null;
        }
		if (comp instanceof Decimalbox) {
			return ((Money)val).getValue();
		}
		return MoneyFormat.getInstance().format(val);
	}
}
