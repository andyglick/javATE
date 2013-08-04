package it.amattioli.encapsulate.dates.format;

import it.amattioli.encapsulate.dates.Month;
import it.amattioli.encapsulate.dates.TimeInterval;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MonthParser extends Parser {
	private SimpleDateFormat df;
	private String regExp;
	
	public static MonthParser instance(Locale locale, Parser nextInChain) {
		int idx = 1;
		String format;
		Parser p = nextInChain;
		ResourceBundle formats = ResourceBundle.getBundle(MonthParser.class.getPackage().getName()+".formats", locale);
		do {
			try {
				format = formats.getString("monthParser.format."+idx);
				String pattern = formats.getString("monthParser.pattern."+idx);
				p = new MonthParser(pattern, format, p);
				idx++;
			} catch(MissingResourceException e) {
				format = null;
			}
		} while (format != null);
		return (MonthParser)p;
	}
	
	public MonthParser(String regExp, String format) {
		this(regExp, format, null);
	}
	
	public MonthParser(String regExp, String format, Parser nextInChain) {
		super(Locale.getDefault(), nextInChain);
		this.regExp = regExp;
		df = new SimpleDateFormat(format, getLocale());
		df.setLenient(false);
	}
	
	@Override
	public TimeInterval tryParse(String s) throws ParseException {
		if (!s.matches(regExp)) {
			throw new ParseException("", 0);
		}
		return new Month(df.parse(s));
	}

}
