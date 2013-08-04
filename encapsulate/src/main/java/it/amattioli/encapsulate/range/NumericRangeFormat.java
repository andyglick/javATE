package it.amattioli.encapsulate.range;

import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

import static org.apache.commons.lang.StringUtils.*;

public class NumericRangeFormat extends Format {
	private NumberFormat nfmt = NumberFormat.getInstance();
	
	public static NumericRangeFormat getInstance() {
		return new NumericRangeFormat(NumberFormat.getInstance());
	}
	
	public static NumericRangeFormat getInstance(Locale locale) {
		return new NumericRangeFormat(NumberFormat.getInstance(locale));
	}
	
	private NumericRangeFormat(NumberFormat nfmt) {
		this.nfmt = nfmt;
	}
	
	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		if (! (obj instanceof Range<?>)) {
			throw new IllegalArgumentException();
		}
		Range<? extends Number> range = (Range<? extends Number>)obj;
		if (range.isLowBounded()) {
			nfmt.format(range.getLow(), toAppendTo, pos);
		}
		toAppendTo.append(" - ");
		if (range.isHighBounded()) {
			nfmt.format(range.getHigh(), toAppendTo, pos);
		}
		return toAppendTo;
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		Comparable low = null;
		if (source.substring(pos.getIndex()).startsWith("...")) {
			pos.setIndex(pos.getIndex()+3);
		} else {
			low =(Comparable)nfmt.parse(source, pos);
		}
		if (isBlank(source.substring(pos.getIndex()))) {
			return new GenericContinousRange(low, low);
		}
		String s2 = stripStart(source.substring(pos.getIndex())," ");
		if (s2.startsWith("- ") || (s2.startsWith("-") && source.length()-s2.length() == pos.getIndex())) {
			s2 = stripStart(s2.substring(1)," ");
		}
		pos.setIndex(source.length()-s2.length());
		Comparable high = null;
		if (source.substring(pos.getIndex()).startsWith("...")) {
			pos.setIndex(pos.getIndex()+3);
		} else {
			high = (Comparable)nfmt.parse(source, pos);
		}
		return new GenericContinousRange(low, high);
	}

}
