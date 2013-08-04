package it.amattioli.dominate.validation;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.validator.ClassValidator;

public class ClassValidatorPool {
	private static final ClassValidatorPool instance = new ClassValidatorPool(); 
	
	public static ClassValidatorPool getInstance() {
		return instance;
	}
	
	private final Map<Class<?>, ClassValidator<?>> pool = new HashMap<Class<?>, ClassValidator<?>>();
	
	private ClassValidatorPool() {
		
	}
	
	public synchronized <T> ClassValidator<T> getValidator(Class<T> c) {
		ClassValidator<T> result = (ClassValidator<T>)pool.get(c);
		if (result == null) {
			result = new ClassValidator<T>(c);
			pool.put(c, result);
		}
		return result;
	}
}
