package it.amattioli.encapsulate.money;

import it.amattioli.dominate.EntityImpl;
import it.amattioli.encapsulate.money.Money;

public class FakeMoneyEntity extends EntityImpl {
	private Money money;

	public Money getMoney() {
		return money;
	}

	public void setMoney(Money money) {
		this.money = money;
	}
}
