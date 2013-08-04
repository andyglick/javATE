package it.amattioli.encapsulate.dates.format;

import it.amattioli.encapsulate.dates.TimeInterval;
import it.amattioli.encapsulate.dates.TimeIntervalFactory;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang.StringUtils.*;

public class IntervalParser extends Parser {
	private static final String SEPARATOR = "(?:(?:-)|(?:\\.\\.\\.))";

	public IntervalParser() {
		this(Locale.getDefault(), null);
	}
	
	public IntervalParser(Locale locale, Parser nextInChain) {
		super(locale, nextInChain);
	}
	
	@Override
	public TimeInterval tryParse(String s) throws ParseException {
		Pattern pattern = Pattern.compile("(.*)"+SEPARATOR+"(.*)");
		Matcher mtch = pattern.matcher(s);
		if (mtch.matches()) {
			Date low = null;
			Date high = null;
			TimeInterval end = null;
			TimeInterval start = null;
			String startString = trim(mtch.group(1));
			String endString = trim(mtch.group(2));
			if (!isBlank(endString)) {
				Parser endParser = Parser.create(getLocale());
				end = endParser.parse(endString);
				high = end.getHigh();
			}
			if (!isBlank(startString)) {
				Parser startParser = Parser.create(getLocale(), end);
				start = startParser.parse(startString);
				low = start.getLow();
			}
			if (low != null && high != null && low.after(high)) {
				throw new ParseException("", 0);
			}
			return new TimeIntervalFactory().createTimeInterval(low, high);
		} else {
			throw new ParseException("", 0);
		}
	}

}
