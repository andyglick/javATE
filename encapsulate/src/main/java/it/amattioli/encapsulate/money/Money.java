package it.amattioli.encapsulate.money;

import java.util.Collection;
import java.util.Currency;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import org.apache.commons.beanutils.PropertyUtils;

import static it.amattioli.encapsulate.money.MoneyErrorMessage.*;

/**
 * Money rappresenta una quantità di denaro. Al costruttore di una money occorre
 * passare sia la quantità che la divisa in cui questa è espressa. E' poi
 * possibile aggiungere e sottrarre quantità di denaro espresse nella stessa
 * divisa.
 */
public class Money implements Comparable<Money>, Serializable, Cloneable {
	private static final long serialVersionUID = 4L;
	private long unscaled;
	private Currency currency;
	private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
	private static final BigDecimal BIG_ONE = new BigDecimal("1");
	private static final Currency EURO = Currency.getInstance("EUR");
	
	public static Money euro(BigDecimal value) {
		return new Money(value, EURO);
	}
	
	public static Money euro(int value) {
		return new Money(new BigDecimal(value), EURO);
	}
	
	public static Money euro(long value) {
		return new Money(new BigDecimal(value), EURO);
	}
	
	public static Money euro(String value) {
		return new Money(new BigDecimal(value), EURO);
	}

	/**
	 * Costruisce un'istanza con la quantità di denaro e la divisa indicate.
	 * Nessuno dei due parametri può essere null. La quantità passata verrà
	 * arrotondata (usando ROUND_HALF_UP) al numero di decimali previsto dalla
	 * divisa.
	 * 
	 * @param value
	 *            la quantità di denaro
	 * @param currency
	 *            la divisa in cui la quantità di denaro è espressa
	 * @throws NullPointerException
	 *             se almeno uno dei due parametri è null
	 */
	public Money(BigDecimal value, Currency currency) {
		if (value == null) {
			throw new NullPointerException(NULL_MONEY_VALUE.getMessage());
		}
		if (currency == null) {
			throw new NullPointerException(NULL_MONEY_CURRENCY.getMessage());
		}
		this.unscaled = value.setScale(currency.getDefaultFractionDigits(),ROUNDING_MODE).unscaledValue().longValue();
		this.currency = currency;
	}

	public Money(long unscaled, Currency currency) {
		if (currency == null) {
			throw new NullPointerException(NULL_MONEY_CURRENCY.getMessage());
		}
		this.unscaled = unscaled;
		this.currency = currency;
	}

	/**
	 * Restituisce la quantità di denaro.
	 * 
	 * @return la quantità di denaro
	 */
	public BigDecimal getValue() {
		return new BigDecimal(new BigInteger(Long.toString(unscaled)), 
				              this.getCurrency().getDefaultFractionDigits());
	}

	/**
	 * Restituisce la divisa in cui è espressa la quantità di denaro.
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * Somma a questa una quantit&agrave; di denaro.
	 * 
	 * @param addendo
	 *            la quantità di denaro da sommare
	 * @return questa quantit&agrave; di denaro
	 * @throws IncompatibleCurrency
	 *             se la quantità di denaro da sommare è espressa in una divisa
	 *             diversa da quella di questa istanza
	 */
	public Money add(Money addendo) throws IncompatibleCurrency {
		return sum(this, addendo);
	}
	
	private static void assertSameCurrency(Money m1, Money m2) throws IncompatibleCurrency {
		if (!m1.getCurrency().equals(m2.getCurrency())) {
			throw new IncompatibleCurrency();
		}
	}

	/**
	 * Costruisce una nuova quantit&agrave; di denari data dalla somma degli
	 * addendi passati come parametri.
	 * 
	 * @param addendo
	 *            le quantit&agrave; di denaro da sommare
	 * @return
	 * @throws IncompatibleCurrency
	 *             se le due quantit&agrave; di denaro sono espresse in divise
	 *             diverse tra loro
	 */
	public static <T extends Money> T sum(T... addendo) throws IncompatibleCurrency {
		T result = null;
		for (Money curr : addendo) {
			if (result == null) {
				result = (T) curr.clone();
			} else {
				assertSameCurrency(curr, result);
				result.unscaled += curr.unscaled;
			}
		}
		return result;
	}

	public static <T extends Money> T sum(Collection<T> moneyColl) throws IncompatibleCurrency {
		T result = null;
		for (Money curr : moneyColl) {
			if (result == null) {
				result = (T) curr.clone();
			} else {
				assertSameCurrency(curr, result);
				result.unscaled += curr.unscaled;
			}
		}
		return result;
	}
	
	@Deprecated
	public static Money sumProperty(Collection<?> coll, String propertyName) throws IncompatibleCurrency, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Money result = null;
		for (Object curr : coll) {
			Money currMoney = (Money)PropertyUtils.getProperty(curr, propertyName);
			if (currMoney != null) {
				if (result == null) {
					result = currMoney.clone();
				} else {
					assertSameCurrency(currMoney, result);
					result.unscaled += currMoney.unscaled;
				}
			}
		}
		return result;
	}
	
	public static Money propertySum(Collection<?> coll, String propertyName) {
		try {
			return sumProperty(coll, propertyName);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Sottrae a questa una quantit&agrave; di denaro.
	 * 
	 * @param v
	 *            la quantità di denaro da sottrarre
	 * @throws IncompatibleCurrency
	 *             se la quantità di denaro da sottrarre è espressa in una
	 *             divisa diversa da quella di questa istanza
	 */
	public Money subtract(Money v) throws IncompatibleCurrency {
		return subtract(this, v);
	}

	/**
	 * Costruisce una nuova quantit&agrave; di denaro ottenuta sottraendo il
	 * secondo parametro dal primo.
	 * 
	 * @param addendo1
	 * @param addendo2
	 * @return
	 * @throws IncompatibleCurrency
	 */
	public static <T extends Money> T subtract(T addendo1, T addendo2) throws IncompatibleCurrency {
		assertSameCurrency(addendo1, addendo2);
		T result = (T) addendo1.clone();
		result.unscaled -= addendo2.unscaled;
		return result;
	}

	/**
	 * Moltiplica questa qunatit&agrave; di denaro per il double passato come
	 * parametro. Il risultato verrà approssimato in modo da rispettare il
	 * numero di decimali previsto per la divisa.
	 * 
	 * @param d
	 *            il numero per il quale moltiplicare la quantit&agrave; di
	 *            denaro
	 * @return questa quantit&agrave; di denaro
	 */
	public Money multiply(double d) {
		return multiply(this, d);
	}

	/**
	 * Costruisce una nuova qunatit&agrave; di denaro data dal prodotto di
	 * questa per il BigDecimal passato come parametro. Il risultato verrà
	 * approssimato in modo da rispettare il numero di decimali previsto per la
	 * divisa.
	 * 
	 * @param d
	 *            il numero per il quale moltiplicare la quantit&agrave; di
	 *            denaro
	 */
	public Money multiply(BigDecimal d) {
		return multiply(this, d);
	}

	public Money multiply(int i) {
		return multiply(this, i);
	}

	public static <T extends Money> T multiply(T money, int i) {
		T result = (T) money.clone();
		result.unscaled = money.unscaled * i;
		return result;
	}

	public static <T extends Money> T multiply(T money, double d) {
		T result = (T) money.clone();
		result.unscaled = (long) (money.unscaled * d);
		return result;
	}

	public static <T extends Money> T multiply(T money, BigDecimal d) {
		T result = (T) money.clone();
		BigDecimal newValue = money.getValue().multiply(d);
		result.unscaled = newValue.setScale(money.getCurrency().getDefaultFractionDigits(),ROUNDING_MODE).unscaledValue().longValue();
		return result;
	}

	/**
	 * Costruisce una nuova quantit&agrave; di denaro sommando a questa la
	 * percentuale passata come parametro. Il risultato verrà approssimato in
	 * modo da rispettare il numero di decimali previsto per la divisa.
	 * 
	 * @param p
	 *            la percentuale da sommare a questa quantit&agrave; di denaro.
	 */
	public Money addPercent(BigDecimal p) {
		return multiply(BIG_ONE.add(p));
	}

	/**
	 * Costruisce una nuova quantit&agrave; di denaro sottraendo a questa la
	 * percentuale passata come parametro. Il risultato verrà approssimato in
	 * modo da rispettare il numero di decimali previsto per la divisa.
	 * 
	 * @param p
	 *            la percentuale da sottrarre a questa quantit&agrave; di
	 *            denaro.
	 */
	public Money subtractPercent(BigDecimal p) {
		return multiply(BIG_ONE.subtract(p));
	}

	/**
	 * Calcola che percentuale è questa quantità di denaro rispetto a quella passata
	 * come parametro.
	 * 
	 * @param other
	 * @return
	 */
	public BigDecimal percentOf(Money other, int scale) {
		return getValue().divide(other.getValue(), scale, RoundingMode.HALF_UP);
	}
	
	public BigDecimal percentOf(Money other) {
		return percentOf(other, 2);
	}

	/**
	 * Due Money sono considerati uguali se hanno la stessa divisa e la stessa
	 * quantit&agrave; di denaro.
	 */
	public boolean equals(Object o) {
		boolean result = false;
		if ((o != null) && (o instanceof Money)) {
			Money m = (Money) o;
			result = getCurrency().equals(m.getCurrency())
					&& unscaled == m.unscaled;
		}
		return result;
	}

	/**
	 * Confronta due Money. Due Money sono confrontabili solamente se hanno la
	 * stessa divisa. Per il confronto viene utilizzato il metodo compareTo
	 * della classe BigDecimal ma non dovrebbero esserci i noti problemi con la
	 * discrepanaza tra compareTo ed equal, perchè due Money con la stessa
	 * divisa hanno sempre lo stesso numero di cifre decimali.
	 * 
	 * @param m
	 *            il Money da confrontare con questa istanza
	 * @return il risultato del metodo compareTo applicato alle quantità di
	 *         denaro
	 * @throws ClassCastException
	 *             se i due Money non hanno la stessa divisa
	 */
	public int compareTo(Money m) {
		if (m == null) {
			throw new NullPointerException(NULL_MONEY_COMPARE.getMessage());
		}
		if (!getCurrency().equals(m.getCurrency())) {
			throw new ClassCastException(INCOMPATIBLE_CURRENCY.getMessage());
		}
		return getValue().compareTo(m.getValue());
	}

	/**
	 * Verifica se questa quantit&agrave; di denaro &egrave; maggiore di
	 * un'altra passata come parametro.
	 * 
	 * @param m
	 *            la quantità di denaro da confrontare
	 * @return true se questa quantit&agrave; di denaro &egrave; maggiore di
	 *         quella passata come parametro
	 * @throws ClassCastException
	 *             se i due Money non hanno la stessa divisa
	 */
	public boolean isGreaterThan(Money m) {
		return this.compareTo(m) > 0;
	}

	/**
	 * Verifica se questa quantit&agrave; di denaro &egrave; minore di un'altra
	 * passata come parametro.
	 * 
	 * @param m
	 *            la quantità di denaro da confrontare
	 * @return true se questa quantit&agrave; di denaro &egrave; minore di
	 *         quella passata come parametro
	 * @throws ClassCastException
	 *             se i due Money non hanno la stessa divisa
	 */
	public boolean isLessThan(Money m) {
		return this.compareTo(m) < 0;
	}

	public Money[] allocate(int buckets) {
		long total = 0;
		// for (int i = 0; i < buckets; i++) total += ratios[i];
		long amount = unscaled;
		if (amount < 0) {
			amount = -amount;
		}
		long remainder = amount;
		Money[] results = new Money[buckets];
		for (int i = 0; i < results.length; i++) {
			results[i] = this.clone();
			results[i].unscaled = amount / buckets;
			remainder -= results[i].unscaled;
		}
		for (int i = 0; i < remainder; i++) {
			results[i].unscaled++;
		}
		if (unscaled < 0) {
			for (int i = 0; i < results.length; i++) {
				results[i].unscaled = -results[i].unscaled;
			}
		}
		return results;
	}

	public Money[] allocate(long[] ratios) {
		long total = 0;
		for (int i = 0; i < ratios.length; i++)
			total += ratios[i];
		long amount = unscaled;
		if (amount < 0) {
			amount = -amount;
		}
		long remainder = amount;
		Money[] results = new Money[ratios.length];
		for (int i = 0; i < results.length; i++) {
			results[i] = this.clone();
			results[i].unscaled = amount * ratios[i] / total;
			remainder -= results[i].unscaled;
		}
		for (int i = 0; i < remainder; i++) {
			results[i].unscaled++;
		}
		if (unscaled < 0) {
			for (int i = 0; i < results.length; i++) {
				results[i].unscaled = -results[i].unscaled;
			}
		}
		return results;
	}
	
	public Money[] allocate(Money[] ratios) {
		long[] longRatios = new long[ratios.length];
		for (int i=0; i<ratios.length; i++) {
			longRatios[i] = ratios[i].unscaled;
		}
		return allocate(longRatios);
	}

	public Money presentValue(BigDecimal rate, int periods) {
		BigDecimal rate1 = BigDecimal.ONE.add(rate);
		BigDecimal denom = rate1.pow(periods);
		BigDecimal coeff = BigDecimal.ONE.divide(denom, rate.scale() * 2, BigDecimal.ROUND_HALF_UP);
		return multiply(this, coeff);
	}

	public Money futureValue(BigDecimal rate, int periods) {
		BigDecimal coeff = BigDecimal.ONE.add(rate).pow(periods);
		return multiply(this, coeff);
	}

	public Money simpleInterest(BigDecimal rate, int periods) {
		BigDecimal coeff = rate.multiply(new BigDecimal(periods));
		return multiply(this, coeff);
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + getValue().hashCode();
		result = 37 * result + currency.hashCode();
		return result;
	}
	
	public String toString() {
		return "" + getValue().toString() + " " + getCurrency();
	}

	@Override
	public Money clone() {
		try {
			return (Money) super.clone();
		} catch(CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

}
