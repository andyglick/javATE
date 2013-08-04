package it.amattioli.encapsulate.dates;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

@Deprecated
public class DateFormatMask {
	public static final HashMap timeFormatMask = new HashMap();
	public static final HashMap dayFormatMask = new HashMap();
	public static final HashMap monthFormatMask = new HashMap();
	public static final HashMap yearFormatMask = new HashMap();
	
	static {
		timeFormatMask.put(Locale.ITALY,"dd/MM/yyyy HH:mm");
		dayFormatMask.put(Locale.ITALY,"dd/MM/yyyy");
		monthFormatMask.put(Locale.ITALY,"MM/yyyy");
		yearFormatMask.put(Locale.ITALY,"yyyy");
		
		timeFormatMask.put(Locale.ITALIAN,"dd/MM/yyyy HH:mm");
		dayFormatMask.put(Locale.ITALIAN,"dd/MM/yyyy");
		monthFormatMask.put(Locale.ITALIAN,"MM/yyyy");
		yearFormatMask.put(Locale.ITALIAN,"yyyy");
		
		timeFormatMask.put(Locale.GERMANY,"dd/MM/yyyy HH:mm");
		dayFormatMask.put(Locale.GERMANY,"dd/MM/yyyy");
		monthFormatMask.put(Locale.GERMANY,"MM/yyyy");
		yearFormatMask.put(Locale.GERMANY,"yyyy");
		
		timeFormatMask.put(Locale.US,"MM/dd/yyyy hh:mm a");
		dayFormatMask.put(Locale.US,"MM/dd/yyyy");
		monthFormatMask.put(Locale.US,"MM/yyyy");
		yearFormatMask.put(Locale.US,"yyyy");
		
		timeFormatMask.put(Locale.ENGLISH,"MM/dd/yyyy hh:mm a");
		dayFormatMask.put(Locale.ENGLISH,"MM/dd/yyyy");
		monthFormatMask.put(Locale.ENGLISH,"MM/yyyy");
		yearFormatMask.put(Locale.ENGLISH,"yyyy");
		
		timeFormatMask.put(Locale.UK,"MM/dd/yyyy hh:mm a");
		dayFormatMask.put(Locale.UK,"MM/dd/yyyy");
		monthFormatMask.put(Locale.UK,"MM/yyyy");
		yearFormatMask.put(Locale.UK,"yyyy");
	}
	
	public static Locale[] getAvailableLocales() {
		Locale[] result = new Locale[dayFormatMask.size()];
		int i = 0;
		for(Iterator iter = dayFormatMask.keySet().iterator(); iter.hasNext();) {
			result[i++] = (Locale)iter.next();
		}
		return result;
	}
	
}
