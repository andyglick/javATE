package it.amattioli.encapsulate.dates;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import static it.amattioli.encapsulate.dates.DatesErrorMessages.*;

/**
 * A {@link TemporalExpression} like this check if a date is between two days of
 * the year independently of the year.
 * For example it can check if the date is between June 15th and August 31th.
 * 
 */
public class RangeEachYear implements TemporalExpression, DayExpression {
	private int startMonth;
	private int endMonth;
	private int startDay;
	private int endDay;

	private void assertValidMonth(int month) {
		Calendar calendar = GregorianCalendar.getInstance();
		if (month < calendar.getMinimum(Calendar.MONTH) || month > calendar.getMaximum(Calendar.MONTH)) {
			throw new IllegalArgumentException(INVALID_MONTH.getMessage());
		}
	}

	private void assertValidDay(int month, int day) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, month);
		if (day < calendar.getActualMinimum(Calendar.DAY_OF_MONTH) || day > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
			throw new IllegalArgumentException(INVALID_DAY.getMessage());
		}
	}

	public RangeEachYear(int month) {
		assertValidMonth(month);
		this.startMonth = month;
		this.endMonth = month;
		this.startDay = 0;
		this.endDay = 0;
	}

	public RangeEachYear(int startMonth, int endMonth) {
		assertValidMonth(startMonth);
		assertValidMonth(endMonth);
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.startDay = 0;
		this.endDay = 0;
	}

	public RangeEachYear(int startMonth, int startDay, int endMonth, int endDay) {
		assertValidMonth(startMonth);
		assertValidMonth(endMonth);
		assertValidDay(startMonth, startDay);
		assertValidDay(endMonth, endDay);
		this.startMonth = startMonth;
		this.endMonth = endMonth;
		this.startDay = startDay;
		this.endDay = endDay;
	}

	public boolean includes(Date d) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(d);
		boolean result = monthsInclude(calendar) || startMonthIncludes(calendar) || endMonthIncludes(calendar);
		return result;
	}

	public boolean includes(Day d) {
		return includes(d.getInitTime());
	}

	private boolean monthsInclude(Calendar calendar) {
		int month = calendar.get(Calendar.MONTH);
		return (month > startMonth && month < endMonth);
	}

	private boolean startMonthIncludes(Calendar calendar) {
		if (calendar.get(Calendar.MONTH) != startMonth) {
			return false;
		}
		if (startDay == 0) {
			return true;
		}
		return (calendar.get(Calendar.DAY_OF_MONTH) >= startDay);
	}

	private boolean endMonthIncludes(Calendar calendar) {
		if (calendar.get(Calendar.MONTH) != endMonth) {
			return false;
		}
		if (endDay == 0) {
			return true;
		}
		return (calendar.get(Calendar.DAY_OF_MONTH) <= endDay);
	}

}