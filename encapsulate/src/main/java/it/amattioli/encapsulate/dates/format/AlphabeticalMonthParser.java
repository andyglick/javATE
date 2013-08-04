package it.amattioli.encapsulate.dates.format;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import it.amattioli.encapsulate.dates.Month;
import it.amattioli.encapsulate.dates.TimeInterval;
import it.amattioli.encapsulate.dates.Year;

public class AlphabeticalMonthParser extends Parser {
	private TimeInterval otherSide;
	
	public static AlphabeticalMonthParser instance(Locale locale, TimeInterval otherSide, Parser nextInChain) {
		return new AlphabeticalMonthParser(locale, nextInChain, otherSide);
	}
	
	public AlphabeticalMonthParser() {
		this(null);
	}
	
	public AlphabeticalMonthParser(Parser nextInChain) {
		this(Locale.getDefault(), nextInChain, null);
	}
	
	public AlphabeticalMonthParser(Locale locale, Parser nextInChain, TimeInterval otherSide) {
		super(locale, nextInChain);
		this.otherSide = otherSide;
	}
	
	@Override
	public TimeInterval tryParse(String s) throws ParseException {
		if (!s.matches("\\p{Alpha}*")) {
			throw new ParseException("", 0);
		}
		SimpleDateFormat df = new SimpleDateFormat("MMM yyyy", getLocale());
		if (otherSide == null) {
			return new Month(df.parse(s + " " + Year.thisYear().getYear()));
		} else if (otherSide instanceof Month) {
			return new Month(df.parse(s + " " + ((Month)otherSide).getYear()));
		} else {
			throw new ParseException("", 0);
		}
	}
}
