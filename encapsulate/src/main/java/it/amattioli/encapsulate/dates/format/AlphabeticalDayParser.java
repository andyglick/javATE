package it.amattioli.encapsulate.dates.format;

import it.amattioli.encapsulate.dates.Day;
import it.amattioli.encapsulate.dates.TimeInterval;

import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;

public class AlphabeticalDayParser extends Parser {
	private String yesterday;
	private String today;
	private String tomorrow;

	public static AlphabeticalDayParser instance(Locale locale, Parser nextInChain) {
		return new AlphabeticalDayParser(locale, nextInChain);
	}
	
	public AlphabeticalDayParser() {
		this(Locale.getDefault(), null);
	}
	
	public AlphabeticalDayParser(Locale locale, Parser nextInChain) {
		super(locale, nextInChain);
		ResourceBundle formats = ResourceBundle.getBundle(getClass().getPackage().getName()+".formats", locale);
		yesterday = formats.getString("yesterday");
		today = formats.getString("today");
		tomorrow = formats.getString("tomorrow");
	}
	
	@Override
	public TimeInterval tryParse(String s) throws ParseException {
		if (yesterday.equalsIgnoreCase(s)) {
			return Day.yesterday();
		} else if (today.equalsIgnoreCase(s)) {
			return Day.today();
		} else if (tomorrow.equalsIgnoreCase(s)) {
			return Day.tomorrow();
		} else {
			throw new ParseException("", 0);
		}
	}

}
