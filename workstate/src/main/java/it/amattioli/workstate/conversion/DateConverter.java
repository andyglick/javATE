package it.amattioli.workstate.conversion;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 */
public class DateConverter {
	
	public static Date getDateFromString(String s) {
		return getDate(s, "dd/MM/yyyy");
	}
	
	private static Date getDate(String dateValue, String format) {
        SimpleDateFormat myFormat = new SimpleDateFormat(format);
        Date myDate = null;

        try {
            myDate = myFormat.parse(dateValue);
        } catch (Exception e) {
            myDate = null;
        }

        return myDate;
    }

}
