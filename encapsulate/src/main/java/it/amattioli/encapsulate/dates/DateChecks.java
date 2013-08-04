package it.amattioli.encapsulate.dates;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.JANUARY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;

import java.util.Date;
import java.util.GregorianCalendar;

public final class DateChecks {
	
	private DateChecks() {
	}
	
	public static boolean isMidnight(Date d) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(d);
		return isMidnight(cal);
	}

	public static boolean isMidnight(GregorianCalendar cal) {
		return cal.get(HOUR_OF_DAY) == 0 &&
	           cal.get(MINUTE) == 0 &&
	           cal.get(SECOND) == 0;
	}
	
	public static boolean isFirstDayOfMonth(Date d) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(d);
		return isFirstDayOfMonth(cal);
	}

	public static boolean isFirstDayOfMonth(GregorianCalendar cal) {
		return cal.get(DAY_OF_MONTH) == 1 &&
	           isMidnight(cal);
	}

	public static boolean isJanuaryFirst(Date d) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(d);
		return isJanuaryFirst(cal);
	}

	public static boolean isJanuaryFirst(GregorianCalendar cal) {
		return cal.get(MONTH) == JANUARY &&
	           isFirstDayOfMonth(cal);
	}

}
