package it.amattioli.encapsulate.money;

import java.math.BigDecimal;
import java.text.*;
import java.util.*;

import org.apache.commons.lang.StringUtils;

/**
 * Permette la trasformazione da testo a quantità di denaro e viceversa.
 * Fondamentalmente per effettuare la conversione utilizza un'istanza di
 * NumberFormat in cui il numero di decimali è fissato a quello della divisa
 * indicata nel costruttore. Inoltre il metodo parse non ritorna un Number bensì
 * un Money costruito con la divisa indicata.
 */
public class MoneyFormat extends Format {
	private static final long serialVersionUID = 1L;
	private NumberFormat nf;
	private Currency currency;

	protected MoneyFormat(Currency currency) {
		this.nf = NumberFormat.getInstance();
		setCurrency(currency);
	}

	protected MoneyFormat(Locale locale) {
		Currency currency = Currency.getInstance(locale);
		this.nf = NumberFormat.getInstance(locale);
		setCurrency(currency);
	}

	protected MoneyFormat(Locale locale, Currency currency) {
		this.nf = NumberFormat.getInstance(locale);
		setCurrency(currency);
	}

	private void setCurrency(Currency currency) {
		this.currency = currency;
		nf.setMaximumFractionDigits(currency.getDefaultFractionDigits());
		nf.setMinimumFractionDigits(currency.getDefaultFractionDigits());
	}

	/**
	 * Ritorna un'istanza di MoneyFormat che utilizza la Currency indicata e la
	 * locale di default.
	 * 
	 * @param currency
	 *            la Currency da utilizzare per la formattazione
	 * @return una nuova istanza di MoneyFormat
	 */
	public static MoneyFormat getInstance(Currency currency) {
		return new MoneyFormat(currency);
	}
	
	public static MoneyFormat getInstance() {
		return new MoneyFormat(Locale.getDefault());
	}

	/**
	 * Ritorna un'istanza di MoneyFormat che utilizza la Currency e la Locale
	 * indicate.
	 * 
	 * @param currency
	 *            la Currency da utilizzare per la formattazione
	 * @param locale
	 *            la Locale da utilizzare per la formattazione
	 * @return una nuova istanza di MoneyFormat
	 */
	public static MoneyFormat getInstance(Currency currency, Locale locale) {
		return new MoneyFormat(locale, currency);
	}

	public static Locale[] getAvailableLocales() {
		return NumberFormat.getAvailableLocales();
	}

	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		if ((obj == null) || !(obj instanceof Money)) {
			throw new IllegalArgumentException("MoneyFormat non può formattare oggetti diversi da Money");
		}
		Money m = (Money) obj;
		return nf.format(m.getValue(), toAppendTo, pos);
	}

	public Object parseObject(String source, ParsePosition pos) {
		return parse(source, pos);
	}

	protected Money numberToMoney(Number n) {
		BigDecimal bd = new BigDecimal(n.toString());
		Money m = new Money(bd, currency);
		return m;
	}

	public Money parse(String source, ParsePosition pos) {
		Number n = nf.parse(source, pos);
		if (n == null) {
			return null;
		} else {
			return numberToMoney(n);
		}
	}

	public Money parse(String source) throws ParseException {
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		Number n = nf.parse(source);
		return numberToMoney(n);
	}

}