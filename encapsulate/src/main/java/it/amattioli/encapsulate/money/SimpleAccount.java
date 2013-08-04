package it.amattioli.encapsulate.money;

import java.util.*;
import java.math.BigDecimal;
import org.apache.commons.collections.iterators.*;

import it.amattioli.encapsulate.dates.*;

public class SimpleAccount implements Account {
	private List<AccountEntry> entries;
	private Currency currency;
	private Money balance;

	@SuppressWarnings("unused")
	private SimpleAccount() {
	}

	public SimpleAccount(Currency currency) {
		setCurrency(currency);
		try {
			setEntries(Collections.EMPTY_LIST);
		} catch (IncompatibleCurrency ic) {
			// Non puo' verificarsi perche' ho passato una lista vuota
		}
	}

	public void addEntry(AccountEntry entry) throws IncompatibleCurrency {
		if (entry == null) {
			throw new NullPointerException();
		}
		if (!getCurrency().equals(entry.getMoney().getCurrency())) {
			throw new IncompatibleCurrency();
		}
		entries.add(entry);
		setBalance(getBalance().add(entry.getMoney()));
	}

	public List<AccountEntry> getEntries() {
		return Collections.unmodifiableList(entries);
	}

	private void setEntries(List<AccountEntry> entries) throws IncompatibleCurrency {
		if (entries == null) {
			throw new NullPointerException();
		}
		resetBalance();
		this.entries = new ArrayList<AccountEntry>();
		for (AccountEntry currEntry : entries) {
			addEntry(currEntry);
		}
	}

	public Iterator<AccountEntry> getEntriesIterator() {
		return entries.iterator();
	}

	public Iterator getEntriesIterator(TemporalExpression expr) {
		return new FilterIterator(entries.iterator(), new AccountEntryPredicate(expr));
	}

	public Currency getCurrency() {
		return currency;
	}

	private void setCurrency(Currency currency) {
		if (currency == null) {
			throw new NullPointerException();
		}
		this.currency = currency;
	}

	public Money getBalance() {
		return balance;
	}

	private void setBalance(Money balance) {
		this.balance = balance;
	}

	private void resetBalance() {
		setBalance(new Money(new BigDecimal("0"), currency));
	}

}
