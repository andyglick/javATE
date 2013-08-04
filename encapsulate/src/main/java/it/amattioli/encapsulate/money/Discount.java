package it.amattioli.encapsulate.money;

import java.math.BigDecimal;

public class Discount {
	public static final Discount NO_DISCOUNT = new Discount(new BigDecimal(0));

	private BigDecimal perc1;
	private BigDecimal perc2;

	@SuppressWarnings("unused")
	private Discount() {
	}

	public Discount(BigDecimal perc1) {
		this.perc1 = perc1;
		this.perc2 = new BigDecimal(0);
	}

	public Discount(BigDecimal perc1, BigDecimal perc2) {
		this.perc1 = perc1;
		this.perc2 = perc2;
	}

	public BigDecimal getPerc1() {
		return perc1;
	}

	public BigDecimal getPerc2() {
		return perc2;
	}

	public Money applyTo(Money m) {
		return m.subtractPercent(perc1).subtractPercent(perc2);
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Discount)
				&& perc1.equals(((Discount) obj).perc1)
				&& perc2.equals(((Discount) obj).perc2);
	}
}
