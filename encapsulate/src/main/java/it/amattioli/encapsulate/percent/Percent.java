package it.amattioli.encapsulate.percent;

import java.math.BigDecimal;

public class Percent {
	private BigDecimal multiplier;
	
	public Percent(BigDecimal multiplier) {
		this.multiplier = multiplier;
	}
	
	public Percent(String multiplier) {
		this(new BigDecimal(multiplier));
	}
	
	public BigDecimal getMultiplier() {
		return multiplier;
	}
	
	public BigDecimal of(BigDecimal value) {
		return value.multiply(multiplier);
	}
	
	public BigDecimal addTo(BigDecimal value) {
		return value.multiply(BigDecimal.ONE.add(multiplier));
	}
	
}
