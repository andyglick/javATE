package it.amattioli.encapsulate.money;

import java.util.*;

public interface Account {

	public void addEntry(AccountEntry entry) throws IncompatibleCurrency;

	public List<AccountEntry> getEntries();

	public Currency getCurrency();

	public Money getBalance();

}
