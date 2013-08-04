package it.amattioli.dominate.sessions;

public class ConcurrencyException extends RuntimeException {
	private String entityName;
	
	public ConcurrencyException() {
		
	}
	
	public ConcurrencyException(String entityName, Throwable e) {
		super(e);
		this.entityName = entityName;
	}
	
	public ConcurrencyException(Throwable e) {
		super(e);
	}
	
	public String getEntityName() {
		return entityName;
	}
}
