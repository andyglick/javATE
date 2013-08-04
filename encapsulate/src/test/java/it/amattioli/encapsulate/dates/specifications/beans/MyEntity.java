package it.amattioli.encapsulate.dates.specifications.beans;

import java.util.Date;

import it.amattioli.dominate.EntityImpl;
import it.amattioli.encapsulate.money.Money;

public class MyEntity extends EntityImpl {
	private Date birthdate;

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
}
