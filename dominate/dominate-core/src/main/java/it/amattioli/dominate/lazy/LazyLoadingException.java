package it.amattioli.dominate.lazy;

public class LazyLoadingException extends RuntimeException {

	public LazyLoadingException(Class<?> entityClass, Object id) {
		super("Entity with id '" + id + "' does not exists in repository for " + entityClass);
	}
	
}
