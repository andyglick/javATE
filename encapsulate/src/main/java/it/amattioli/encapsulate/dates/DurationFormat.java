package it.amattioli.encapsulate.dates;

import java.text.FieldPosition;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.Map;

public class DurationFormat extends Format {
	private static final long serialVersionUID = 1L;
	private NumberFormat nf = NumberFormat.getInstance(); 

	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		PhysicalDuration d = (PhysicalDuration)obj;
		long days = d.inDays();
		if (days != 0) {
			if (toAppendTo.length() != 0) toAppendTo.append(" ");
			toAppendTo.append(nf.format(days));
			toAppendTo.append(" day");
			if (days > 1) toAppendTo.append("s");
		}
		long hours = d.getRemainingHours();
		if (hours != 0) {
			if (toAppendTo.length() != 0) toAppendTo.append(" ");
			toAppendTo.append(nf.format(hours));
			toAppendTo.append(" hour");
			if (hours > 1) toAppendTo.append("s");
		}
		long minutes = d.getRemainingMinutes();
		if (minutes != 0) {
			if (toAppendTo.length() != 0) toAppendTo.append(" ");
			toAppendTo.append(nf.format(minutes));
			toAppendTo.append(" minute");
			if (minutes > 1) toAppendTo.append("s");
		}
		long seconds = d.getRemainingSeconds();
		if (seconds != 0) {
			if (toAppendTo.length() != 0) toAppendTo.append(" ");
			toAppendTo.append(nf.format(seconds));
			toAppendTo.append(" second");
			if (seconds > 1) toAppendTo.append("s");
		}
		long milliseconds = d.getRemainingMilliseconds();
		if (milliseconds != 0) {
			if (toAppendTo.length() != 0) toAppendTo.append(" ");
			toAppendTo.append(nf.format(milliseconds));
			toAppendTo.append(" millisecond");
			if (milliseconds > 1) toAppendTo.append("s");
		}
		return toAppendTo;
	}
	
	private static Map conversion = new HashMap();
	
	static {
		conversion.put("seconds",new Long(1000));
		conversion.put("second",new Long(1000));
		conversion.put("s",new Long(1000));
		conversion.put("minutes",new Long(60000));
		conversion.put("minute",new Long(60000));
		conversion.put("m",new Long(60000));
		conversion.put("hours",new Long(3600000));
		conversion.put("hour",new Long(3600000));
		conversion.put("h",new Long(3600000));
		conversion.put("days",new Long(86400000));
		conversion.put("day",new Long(86400000));
		conversion.put("d",new Long(86400000));
	}

	private void skipBlanks(String source, ParsePosition pos) {
		while (pos.getIndex() < source.length() && source.charAt(pos.getIndex()) == ' ') {
			pos.setIndex(pos.getIndex()+1);
		}
	}
	
	public Object parseObject(String source, ParsePosition pos) {
		long milliseconds = 0;
		int origPos = pos.getIndex();
		skipBlanks(source,pos);
		while (pos.getIndex() < source.length()) {
			Number oValue = ((Number)nf.parseObject(source,pos));
			if (pos.getErrorIndex() >= 0) {
				pos.setIndex(origPos);
				return null;
			}
			long value = oValue.longValue();
			skipBlanks(source,pos);
			StringBuffer unit = new StringBuffer();
			while (pos.getIndex() < source.length() && source.charAt(pos.getIndex()) != ' ') {
				unit.append(source.charAt(pos.getIndex()));
				pos.setIndex(pos.getIndex()+1);
			}
			Long conv = (Long)conversion.get(unit.toString());
			if (conv == null) {
				pos.setErrorIndex(pos.getIndex());
				pos.setIndex(origPos);
				return null;
			}
			milliseconds += value*conv.longValue();
			skipBlanks(source,pos);
		}
		return PhysicalDuration.fromMilliseconds(milliseconds);
	}

}
