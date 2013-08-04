package it.amattioli.applicate.sessions;

public interface Application {

	public void init();
	
	public ApplicateSession createSession();
	
}
