package it.amattioli.encapsulate.money;

import org.apache.commons.collections.*;

import it.amattioli.encapsulate.dates.*;

public class AccountEntryPredicate implements Predicate {
	private TemporalExpression expr;

	public AccountEntryPredicate(TemporalExpression expr) {
		this.expr = expr;
	}

	public boolean evaluate(Object o) {
		AccountEntry entry = (AccountEntry) o;
		return expr.includes(entry.getTime());
	}

}
