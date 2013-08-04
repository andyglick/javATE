package it.amattioli.applicate.context;

import it.amattioli.applicate.commands.AbstractCommand;

public class CommandStub extends AbstractCommand {
	/**
	 * A random generated id to compare commands so that they are
	 * equals even if they are enhanced by CGLIB
	 */
	private long id = Math.round(1000000.0*Math.random());
	
	public long getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object other) {
		return other instanceof CommandStub && 
		       ((CommandStub)other).getId() == this.getId();
	}
	
}
