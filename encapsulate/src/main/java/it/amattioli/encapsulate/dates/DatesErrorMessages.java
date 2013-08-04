package it.amattioli.encapsulate.dates;

import java.util.ResourceBundle;

public enum DatesErrorMessages {
	NULL_DURATION_BEGIN,
	NULL_DURATION_END,
	INVALID_MONTH,
	INVALID_DAY;
	
	private static ResourceBundle messages = ResourceBundle.getBundle("it/amattioli/encapsulate/dates/Messages");
	
	public String getMessage() {
		return messages.getString(name());
	}
	
}
