package it.amattioli.encapsulate.dates;

import static java.util.Calendar.*;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeIntervalFactory {

	public TimeInterval createTimeInterval(Date begin, Date end) {
		if (isYear(begin,end)) {
			return new Year(begin);
		} else if (isMonth(begin,end)) {
			return new Month(begin);
		} else if (isDay(begin,end)) {
			return new Day(begin);
		} else if (isHour(begin,end)) {
			return new Hour(begin);
		} else {
			return new GenericTimeInterval(begin, end);
		}
	}
	
	private static boolean isYear(Date begin, Date end) {
		if (begin == null || end == null) {
			return false;
		}
		GregorianCalendar beginCal = new GregorianCalendar();
		beginCal.setTime(begin);
		GregorianCalendar endCal = new GregorianCalendar();
		endCal.setTime(end);
		
		return beginCal.get(YEAR) == endCal.get(YEAR) - 1 &&
		       DateChecks.isJanuaryFirst(beginCal) &&
		       DateChecks.isJanuaryFirst(endCal);
	}
	
	private static boolean isMonth(Date begin, Date end) {
		if (begin == null || end == null) {
			return false;
		}
		GregorianCalendar beginCal = new GregorianCalendar();
		beginCal.setTime(begin);
		GregorianCalendar endCal = new GregorianCalendar();
		endCal.setTime(end);
		
		return areConsecutiveMonths(beginCal, endCal) &&
		       DateChecks.isFirstDayOfMonth(beginCal) &&
		       DateChecks.isFirstDayOfMonth(endCal);
	}

	private static boolean areConsecutiveMonths(GregorianCalendar beginCal, GregorianCalendar endCal) {
		return (beginCal.get(YEAR) == endCal.get(YEAR) &&
		        beginCal.get(MONTH) == endCal.get(MONTH) -1) ||
		       (beginCal.get(YEAR) == endCal.get(YEAR) - 1 &&
				beginCal.get(MONTH) == DECEMBER &&
				endCal.get(MONTH) == JANUARY);
	}
	
	private static boolean isDay(Date begin, Date end) {
		if (begin == null || end == null) {
			return false;
		}
		GregorianCalendar beginCal = new GregorianCalendar();
		beginCal.setTime(begin);
		GregorianCalendar endCal = new GregorianCalendar();
		endCal.setTime(end);
		
		return areConsecutiveDays(beginCal, endCal) &&
		       DateChecks.isMidnight(beginCal) &&
		       DateChecks.isMidnight(endCal);
	}

	private static boolean areConsecutiveDays(GregorianCalendar beginCal, GregorianCalendar endCal) {
		return ((beginCal.get(YEAR) == endCal.get(YEAR) &&
		         beginCal.get(DAY_OF_YEAR) == endCal.get(DAY_OF_YEAR) -1) ||
		        (beginCal.get(YEAR) == endCal.get(YEAR) - 1 &&
		         beginCal.get(DAY_OF_YEAR) == beginCal.getActualMaximum(DAY_OF_YEAR) && 
		         endCal.get(DAY_OF_YEAR) == 1));
	}
	
	private static boolean isHour(Date begin, Date end) {
		if (begin == null || end == null) {
			return false;
		}
		GregorianCalendar beginCal = new GregorianCalendar();
		beginCal.setTime(begin);
		GregorianCalendar endCal = new GregorianCalendar();
		endCal.setTime(end);
		
		return new PhysicalDuration(begin,end).equals(PhysicalDuration.ONE_HOUR) &&
		           beginCal.get(MINUTE) == 0 &&
	               beginCal.get(SECOND) == 0 &&
	               endCal.get(MINUTE) == 0 &&
	               endCal.get(SECOND) == 0;
	}
	
}
