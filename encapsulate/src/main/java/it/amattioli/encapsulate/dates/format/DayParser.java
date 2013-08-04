package it.amattioli.encapsulate.dates.format;

import it.amattioli.encapsulate.dates.Day;
import it.amattioli.encapsulate.dates.TimeInterval;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DayParser extends Parser {
	private SimpleDateFormat df;

	public static DayParser instance(Locale locale, Parser nextInChain) {
		int idx = 1;
		String format;
		Parser p = nextInChain;
		ResourceBundle formats = ResourceBundle.getBundle(DayParser.class.getPackage().getName()+".formats", locale);
		do {
			try {
				format = formats.getString("dayParser.format."+idx);
				p = new DayParser(locale, format, p);
				idx++;
			} catch(MissingResourceException e) {
				format = null;
			}
		} while (format != null);
		return (DayParser)p;
	}
	
	public DayParser(Locale locale, String format, Parser nextInChain) {
		super(locale, nextInChain);
		df = new SimpleDateFormat(format, getLocale());
		df.setLenient(false);
	}
	
	@Override
	public TimeInterval tryParse(String s) throws ParseException {
		return new Day(df.parse(s));
	}

}

	