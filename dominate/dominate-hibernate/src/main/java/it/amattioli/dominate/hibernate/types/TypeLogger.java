package it.amattioli.dominate.hibernate.types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypeLogger {
	private Logger logger;
	
	public TypeLogger(String type) {
		logger = LoggerFactory.getLogger("org.hibernate.type."+type);
	}
	
	public void logGet(Object value, String column) {
		logger.trace("returning '{}' as column: {}", value, column);
	}
	
	public void logSet(Object value, int index) {
		logger.trace("binding '{}' to parameter: {}", value, index);
	}
}
