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

@Deprecated
public class TimeIntervalFormat extends Format {
	private static final long serialVersionUID = 1L;
	private static String SLASH = "/";
	private static String MENO = "-";

	private DateFormat dayFormat;
	private DateFormat monthFormat;
	private DateFormat yearFormat;
	  
	public TimeIntervalFormat(DateFormat dayFormat, DateFormat monthFormat, DateFormat yearFormat) {
		this.dayFormat = dayFormat;
		this.monthFormat = monthFormat;
		this.yearFormat = yearFormat;
		this.dayFormat.setLenient(false);
		this.monthFormat.setLenient(false);
		this.yearFormat.setLenient(false);
	}
	
	public static TimeIntervalFormat getInstance() {
		return new TimeIntervalFormat(new SimpleDateFormat("dd/MM/yyyy"),
				                      new SimpleDateFormat("MM/yyyy"),
				                      new SimpleDateFormat("yyyy"));
	}
		
	public static TimeIntervalFormat getInstance(Locale locale) {
		if (Arrays.asList(getAvailableLocales()).contains(locale)) {
			return new TimeIntervalFormat(new SimpleDateFormat((String)DateFormatMask.dayFormatMask.get(locale)),
					                      new SimpleDateFormat((String)DateFormatMask.monthFormatMask.get(locale)),
					                      new SimpleDateFormat((String)DateFormatMask.yearFormatMask.get(locale)));
		} else {
			return getInstance();
		}
	}
	
	public static Locale[] getAvailableLocales() {
		return DateFormatMask.getAvailableLocales();
	}

	
	public AttributedCharacterIterator formatToCharacterIterator(Object obj) {
		return dayFormat.formatToCharacterIterator(obj);
	}
	
	public Object parseObject(String source, ParsePosition pos){
		try {
			Object result = split(source); 
			pos.setIndex(source.length());
			return result;
		} catch(ParseException e) {
			pos.setErrorIndex(e.getErrorOffset());
			return null;
		}
	}
    
	public TimeInterval parse(String value) throws ParseException {
		return split(value);
	}

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
				Date dataDA = (Date)((GenericTimeInterval)obj).getLow();
				String sDataDA = dayFormat.format(dataDA);
				Date dataA = (Date)((GenericTimeInterval)obj).getHigh();
				Day dayA = (Day)new Day(dataA).previous();
				String sDataA = dayFormat.format(dayA.getLow());
				retVal.append(sDataDA).append(" - ").append(sDataA);
			}
		}
		return retVal;
	}

	private TimeInterval split(String value) throws ParseException {
		TimeInterval retVal = null;
		value = value.replaceAll(" ", "");
		String[] array = value.split(MENO);
		if (array.length == 1) {
			retVal = splitForConventional(array[0]);
		} else if (array.length == 2) {
			ConventionalTimeInterval dataDa = splitForConventional(array[0]);
			ConventionalTimeInterval dataA = splitForConventional(array[1]);
			retVal = new GenericTimeInterval(dataDa, dataA);
		}
		return retVal;
	}
	
	private ConventionalTimeInterval splitForConventional(String value) throws ParseException {
		ConventionalTimeInterval retVal = null;
		value = value.replaceAll(" ", "");
		String[] array = value.split(SLASH);
		if (array.length == 1) {
			retVal = new Year(yearFormat.parse(value));
		} if (array.length == 2) {
			retVal = new Month(monthFormat.parse(value));
		} else if (array.length == 3) {
			retVal = new Day(dayFormat.parse(value));
		}
		return retVal;
	}
}
