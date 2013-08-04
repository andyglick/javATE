package it.amattioli.applicate.sessions;

public interface ServiceEnhancer {

	public void setSession(ApplicateSession session);
	
	public void customizeService(Object newService);
	
}
