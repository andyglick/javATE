package it.amattioli.encapsulate.money;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class EuroFormat extends MoneyFormat {
	private static final long serialVersionUID = 4L;

	protected EuroFormat(Locale locale) {
		super(locale, Currency.getInstance("EUR"));
	}

	public static EuroFormat getInstance(Locale locale) {
		return new EuroFormat(locale);
	}

	public static Locale[] getAvailableLocales() {
		return NumberFormat.getAvailableLocales();
	}

	protected Money numberToMoney(Number n) {
		BigDecimal bd = new BigDecimal(n.toString());
		Money m = Money.euro(bd);
		return m;
	}

}
