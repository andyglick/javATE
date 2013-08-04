package it.amattioli.workstate.config;

import it.amattioli.workstate.core.*;

public interface Configuration {

	public void setSource(String source);

	public void setId(String id);
	
	public String getId();

	public MetaMachine read();

}
