package it.amattioli.encapsulate.money;

import java.util.ResourceBundle;

enum MoneyErrorMessage {
	NULL_MONEY_VALUE,
	NULL_MONEY_CURRENCY,
	INCOMPATIBLE_CURRENCY,
	NULL_MONEY_COMPARE;
	
	private static ResourceBundle messages = ResourceBundle.getBundle("it/amattioli/encapsulate/money/Messages");
	
	public String getMessage() {
		return messages.getString(name());
	}
	
}
