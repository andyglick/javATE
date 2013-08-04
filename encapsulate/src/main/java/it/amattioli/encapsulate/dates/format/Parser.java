package it.amattioli.encapsulate.dates.format;

import java.text.ParseException;
import java.util.Locale;

import it.amattioli.encapsulate.dates.TimeInterval;

public abstract class Parser {
	private Parser nextInChain;
	private Locale locale;
	
	public static Parser create() {
		return create(Locale.getDefault(), null);
	}
	
	public static Parser create(Locale locale) {
		return create(locale, null);
	}
	
	public static Parser create(Locale locale, TimeInterval otherSide) {
		Parser p = SingleNumberParser.instance(locale, otherSide, null);
		p = AlphabeticalMonthParser.instance(locale, otherSide, p);
		p = MonthParser.instance(locale, p);
		p = DayParser.instance(locale, p);
		p = AlphabeticalDayParser.instance(locale, p);
		return p;
	}
	
	public Parser(Locale locale, Parser nextInChain) {
		setNextInChain(nextInChain);
		this.locale = locale;
	}
	
	public Locale getLocale() {
		return locale;
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public void setNextInChain(Parser nextInChain) {
		this.nextInChain = nextInChain;
	}

	public TimeInterval parse(String s) throws ParseException {
		try {
			return tryParse(s);
		} catch(ParseException e) {
			if (nextInChain != null) {
				return nextInChain.parse(s);
			} else {
				throw e;
			}
		}
	}
	
	public abstract TimeInterval tryParse(String s) throws ParseException;
	
}
