package it.amattioli.guidate.converters;

import it.amattioli.encapsulate.percent.Percent;

import java.math.BigDecimal;
import java.text.NumberFormat;

import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Decimalbox;

public class PercentConverter implements TypeConverter {
	private static final BigDecimal HUNDRED = new BigDecimal("100");
	
	@Override
	public Object coerceToBean(Object val, Component comp) {
		if (val == null) {
			return null;
		}
		return new Percent(((BigDecimal)val).divide(HUNDRED));
	}

	@Override
	public Object coerceToUi(Object val, Component comp) {
		if (val == null) {
			return null;
		}
		if (comp instanceof Decimalbox) {
			return ((Percent)val).getMultiplier().multiply(HUNDRED);
		} else {
			NumberFormat fmt = NumberFormat.getPercentInstance(Locales.getCurrent());
			return fmt.format(((Percent)val).getMultiplier());
		}
	}

}
