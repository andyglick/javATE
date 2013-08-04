package it.amattioli.encapsulate.dates;

import java.text.AttributedCharacterIterator;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * @author a.mattioli
 */
public class DayFormat extends Format {
	private static final long serialVersionUID = 1L;
	private DateFormat format;

	public DayFormat(DateFormat format) {
		this.format = format;
		this.format.setLenient(false);
	}

	public static DayFormat getInstance() {
		return new DayFormat(DateFormat.getDateInstance());
	}

	public static DayFormat getInstance(int style) {
		return new DayFormat(DateFormat.getDateInstance(style));
	}

	public static DayFormat getInstance(String format) {
		return new DayFormat(new SimpleDateFormat(format));
	}

	public static DayFormat getInstance(Locale locale) {
		if (Arrays.asList(getAvailableLocales()).contains(locale)) {
			return getInstance((String) DateFormatMask.dayFormatMask.get(locale));
		} else {
			return getInstance();
		}
	}

	public static Locale[] getAvailableLocales() {
		return DateFormatMask.getAvailableLocales();
	}

	/**
   *
   */
	public Object parseObject(String source, ParsePosition pos) {
		Date d = (Date) format.parseObject(source, pos);
		if (d != null) {
			return new Day(d);
		} else {
			return null;
		}
	}

	/**
   *
   */
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		return format.format(((Day) obj).getInitTime(), toAppendTo, pos);
	}

	public AttributedCharacterIterator formatToCharacterIterator(Object obj) {
		return format.formatToCharacterIterator(obj);
	}

	public Day parse(String s) throws ParseException {
		Date d = format.parse(s);
		return new Day(d);
	}
}
