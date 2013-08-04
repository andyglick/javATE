package it.amattioli.encapsulate.dates.format;

import it.amattioli.encapsulate.dates.Day;
import it.amattioli.encapsulate.dates.Month;
import it.amattioli.encapsulate.dates.TimeInterval;
import it.amattioli.encapsulate.dates.Year;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class SingleNumberParser extends Parser {
	private TimeInterval otherSide;

	public static SingleNumberParser instance(Locale locale, TimeInterval otherSide, Parser nextInChain) {
		return new SingleNumberParser(locale, nextInChain, otherSide);
	}
	
	public SingleNumberParser() {
		this(null);
	}
	
	public SingleNumberParser(Parser nextInChain) {
		this(Locale.getDefault(), nextInChain, null);
	}
	
	public SingleNumberParser(Locale locale, Parser nextInChain, TimeInterval otherSide) {
		super(locale, nextInChain);
		this.otherSide = otherSide;
	}
	
	@Override
	public TimeInterval tryParse(String s) throws ParseException {
		if (!s.matches("\\d*")) {
			throw new ParseException("", 0);
		}
		if (otherSide == null || otherSide instanceof Year) {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy", getLocale());
			return new Year(fmt.parse(s));
		} else if (otherSide instanceof Month) {
			SimpleDateFormat fmt = new SimpleDateFormat("MM/yyyy", getLocale());
			return new Month(fmt.parse(s+"/"+((Month)otherSide).getYear()));
		} else if (otherSide instanceof Day) {
			SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy", getLocale());
			return new Day(fmt.parse(s+"/"+(((Day)otherSide).getMonth()+1)+"/"+((Day)otherSide).getYear()));
		} else {
			throw new ParseException("", 0);
		}
	}

}
