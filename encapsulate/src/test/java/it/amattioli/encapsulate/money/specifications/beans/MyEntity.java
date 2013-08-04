package it.amattioli.encapsulate.money.specifications.beans;

import it.amattioli.dominate.EntityImpl;
import it.amattioli.encapsulate.money.Money;

public class MyEntity extends EntityImpl {
	private Money cost;

	public Money getCost() {
		return cost;
	}

	public void setCost(Money cost) {
		this.cost = cost;
	}
}
