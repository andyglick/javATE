package it.amattioli.encapsulate.dates.format;

import it.amattioli.encapsulate.dates.DateChecks;
import it.amattioli.encapsulate.dates.Day;
import it.amattioli.encapsulate.dates.GenericTimeInterval;
import it.amattioli.encapsulate.dates.Month;
import it.amattioli.encapsulate.dates.TimeInterval;
import it.amattioli.encapsulate.dates.Year;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import static java.util.Locale.*;
import java.util.ResourceBundle;

public class TimeIntervalFormat extends Format {
	private Locale locale;
	private DateFormat dayFormat;
	private DateFormat monthFormat;
	private DateFormat yearFormat;
	
	public static TimeIntervalFormat getInstance() {
		return new TimeIntervalFormat();
	}
	
	public static TimeIntervalFormat getInstance(Locale locale) {
		return new TimeIntervalFormat(locale);
	}
	
	private static final Locale[] AVAILABLE_LOCALES = new Locale[] { ENGLISH , ITALIAN };
	
	public static Locale[] getAvailableLocales() {
		return AVAILABLE_LOCALES; 
	}
	
	public TimeIntervalFormat() {
		this(Locale.getDefault());
	}
	
	public TimeIntervalFormat(Locale locale) {
		this.locale = locale;
		ResourceBundle formats = ResourceBundle.getBundle(getClass().getPackage().getName()+".formats", locale);
		dayFormat = new SimpleDateFormat(formats.getString("dayFormat"));
		monthFormat = new SimpleDateFormat(formats.getString("monthFormat"));
		yearFormat = new SimpleDateFormat(formats.getString("yearFormat"));
	}
	
	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		StringBuffer retVal = new StringBuffer();
		if (obj != null) {
			if (obj instanceof Day) {
				retVal.append(dayFormat.format(((Day)obj).getLow()));
			} else if (obj instanceof Month) {
				retVal.append(monthFormat.format(((Month)obj).getLow()));
			} else if (obj instanceof Year) {
				retVal.append(yearFormat.format(((Year)obj).getLow()));
			} else if (obj instanceof GenericTimeInterval) {
				formatGenericTimeInterval(retVal, (GenericTimeInterval)obj);
			}
		}
		return retVal;
	}

	private void formatGenericTimeInterval(StringBuffer retVal, GenericTimeInterval interval) {
		DateFormat fmt;
		if(isYearInterval(interval)) {
			fmt = yearFormat;
		} else if (isMonthInterval(interval)) {
			fmt = monthFormat;
		} else {
			fmt = dayFormat;
		}
		String sDataDA = "";
		String sDataA = "";
		if (interval.isLowBounded()) {
			Date dataDA = (Date)interval.getLow();
			sDataDA = fmt.format(dataDA);
		}
		if (interval.isHighBounded()) {
			Date dataA = (Date)interval.getHigh();
			Day dayA = (Day)new Day(dataA).previous();
			sDataA = fmt.format(dayA.getLow());
		}
		String separator = (interval.isLowBounded() && interval.isHighBounded()) ? " - " : "...";
		retVal.append(sDataDA).append(separator).append(sDataA);
	}
	
	private boolean isMonthInterval(GenericTimeInterval interval) {
		return (!interval.isLowBounded() || interval.isLowBounded() && DateChecks.isFirstDayOfMonth(interval.getLow())) &&
				(!interval.isHighBounded() || interval.isHighBounded() && DateChecks.isFirstDayOfMonth(interval.getHigh()));
	}
	
	private boolean isYearInterval(GenericTimeInterval interval) {
		return (!interval.isLowBounded() || interval.isLowBounded() && DateChecks.isJanuaryFirst(interval.getLow())) &&
				(!interval.isHighBounded() || interval.isHighBounded() && DateChecks.isJanuaryFirst(interval.getHigh()));
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		Parser p = new IntervalParser(locale, Parser.create(locale));
		try {
			TimeInterval result = p.parse(source);
			pos.setIndex(source.length());
			return result;
		} catch(ParseException e) {
			pos.setErrorIndex(e.getErrorOffset());
			return null;
		}
	}
	
	@Override
	public TimeInterval parseObject(String s) throws ParseException {
		return (TimeInterval)super.parseObject(s);
	}
	
	public TimeInterval parse(String s) throws ParseException {
		return parseObject(s);
	}

}
