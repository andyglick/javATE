package it.amattioli.encapsulate.dates;

import java.util.*;

/**
 * La classe Easter permette di gestire cio' che ha a che fare con la Pasqua
 * cristiana. Permette fondamentalmente due tipi di operazioni:
 * <ul>
 * <li>Il calcolo del giorno della Pasqua dato l'anno
 * <li>La verifica che un certo giorno corrisponda alla Pasqua
 * </ul>
 * Per quanto riguarda quest'ultima possibilita' la classe Easter implementa le
 * interfacce TemporalExpression e DayExpression permettendo cosi' la verifica
 * sia di un ogetto di classe Date che di uno di classe Day.
 */
public class Easter implements TemporalExpression, DayExpression {

	/**
	 * Calcola il giorno della pasqua a partire dall'anno.
	 * 
	 * @param year
	 *            l'anno di cui si vuole conoscere la pasqua
	 * @return il giorno della pasqua dell'anno indicato
	 */
	public static Day day(int year) {
		int a = year % 19;
		int b = year / 100;
		int c = year % 100;
		int d = b / 4;
		int e = b % 4;
		int g = (8 * b + 13) / 25;
		int h = (19 * a + b - d - g + 15) % 30;
		int j = c / 4;
		int k = c % 4;
		int m = (a + 11 * h) / 319;
		int r = (2 * e + 2 * j - k - h + m + 32) % 7;
		int n = (h - m + r + 90) / 25; // n is Easter month
		int p = (h - m + r + n + 19) % 32; // p is Easter date
		return new Day(p, n-1, year);
	}

	/**
	 * Verifica che l'istante di tempo passato come parametro sia compreso
	 * all'interno di un giorno di Pasqua.
	 */
	public boolean includes(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int year = cal.get(Calendar.YEAR);
		Day easter = Easter.day(year);
		return easter.includes(d);
	}

	/**
	 * Verifica che il giorno passato come parametro corrisponda ad un giorno di
	 * Pasqua.
	 */
	public boolean includes(Day d) {
		Day easter = Easter.day(d.getYear());
		return easter.equals(d);
	}

}
