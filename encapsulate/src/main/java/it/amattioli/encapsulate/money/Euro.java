package it.amattioli.encapsulate.money;

import java.util.Currency;
import java.math.BigDecimal;

/**
 * Euro &egrave; una sottoclasse di Money che utilizza come divisa preimpostata
 * l'euro. &Egrave; una classe di comodo che permette di evitare di passare ogni
 * volta Currency.EURO al costruttore di Money in applicazioni che utilizzano
 * esclusivamente questa divisa.
 */
@Deprecated
public class Euro extends Money implements Cloneable {
	private static final long serialVersionUID = 10L;
	private static final Currency EURO = Currency.getInstance("EUR");
	private static final BigDecimal ZERO = new BigDecimal("0");
	
	/*
	 * Il costruttore di default privato permette la persistenza tramite TopLink
	 * o Hibernate
	 */
	@SuppressWarnings("unused")
	private Euro() {
		super(ZERO, EURO);
	}

	public Euro(BigDecimal value) {
		super(value, EURO);
	}

	public Euro(int value) {
		this(new BigDecimal(value));
	}

	public Euro(long value) {
		this(new BigDecimal(value));
	}

	public Euro(String value) {
		this(new BigDecimal(value));
	}

	@Override
	public Euro add(Money addendo) throws IncompatibleCurrency {
		return (Euro) super.add(addendo);
	}

	@Override
	public Euro subtract(Money v) throws IncompatibleCurrency {
		return (Euro) super.subtract(v);
	}

	@Override
	public Euro multiply(double d) {
		return (Euro) super.multiply(d);
	}

	@Override
	public Euro multiply(BigDecimal d) {
		return (Euro) super.multiply(d);
	}

	@Override
	public Euro multiply(int i) {
		return (Euro) super.multiply(i);
	}

	@Override
	public Euro addPercent(BigDecimal p) {
		return (Euro) super.addPercent(p);
	}

	@Override
	public Euro subtractPercent(BigDecimal p) {
		return (Euro) super.subtractPercent(p);
	}
	/*
	@Override
	public Euro[] allocate(int buckets) {
		return (Euro[]) super.allocate(buckets);
	}

	@Override
	public Euro[] allocate(long[] ratios) {
		return (Euro[]) super.allocate(ratios);
	}
	*/
	@Override
	public Euro presentValue(BigDecimal rate, int periods) {
		return (Euro) super.presentValue(rate, periods);
	}

	@Override
	public Euro futureValue(BigDecimal rate, int periods) {
		return (Euro) super.futureValue(rate, periods);
	}

	@Override
	public Euro simpleInterest(BigDecimal rate, int periods) {
		return (Euro) super.simpleInterest(rate, periods);
	}

	@Override
	public Euro clone() {
		return (Euro) super.clone();
	}

	@Override
	public String toString() {
		return "" + getValue().toString() + " EUR";
	}

}