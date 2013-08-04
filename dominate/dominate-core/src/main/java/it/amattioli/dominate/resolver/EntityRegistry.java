package it.amattioli.dominate.resolver;

import it.amattioli.dominate.Entity;

import java.util.HashMap;
import java.util.Map;

public class EntityRegistry implements EntityResolver {
	private Map<String, Class<? extends Entity<?>>> entities;
	
	public EntityRegistry() {
		this(new HashMap<String, Class<? extends Entity<?>>>());
	}
	
	public EntityRegistry(Map<String, Class<? extends Entity<?>>> entities) {
		this.entities = entities;
	}
	
	@Override
	public Class<? extends Entity<?>> find(String entityName) {
		return entities.get(entityName);
	}
	
	public void register(String name, Class<? extends Entity<?>> entityClass) {
		entities.put(name, entityClass);
	}

}
