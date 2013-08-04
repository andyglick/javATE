package it.amattioli.dominate.resolver;

import it.amattioli.dominate.Entity;

public interface EntityResolver {

	public Class<? extends Entity<?>> find(String entityName);
	
}
