package it.amattioli.encapsulate.money;

import java.util.*;

public class AccountEntry {
	private Money money;
	private Date time;

	public AccountEntry(Money money, Date time) {
		setMoney(money);
		setTime(time);
	}

	public AccountEntry(Money money) {
		setMoney(money);
		setTime(new Date());
	}

	public Money getMoney() {
		return money;
	}

	private void setMoney(Money money) {
		if (money == null) {
			throw new NullPointerException();
		}
		this.money = money;
	}

	public Date getTime() {
		return time;
	}

	private void setTime(Date time) {
		if (time == null) {
			throw new NullPointerException();
		}
		this.time = time;
	}

	public static final Comparator TIME_COMPARATOR = new Comparator() {
		public int compare(Object o1, Object o2) {
			AccountEntry entry1 = (AccountEntry) o1;
			AccountEntry entry2 = (AccountEntry) o2;
			return entry1.getTime().compareTo(entry2.getTime());
		}
	};
}
