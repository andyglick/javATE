package it.amattioli.encapsulate.money.specifications;

import it.amattioli.dominate.EntityImpl;
import it.amattioli.encapsulate.money.Money;

public class MyEntity extends EntityImpl {
	private Money myMoney;

	public Money getMyMoney() {
		return myMoney;
	}

	public void setMyMoney(Money myMoney) {
		this.myMoney = myMoney;
	}
}
