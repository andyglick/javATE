package it.amattioli.dominate.resolver;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import it.amattioli.dominate.Entity;

public class CompositeEntityResolver implements EntityResolver {
	private List<EntityResolver> children;
	
	public CompositeEntityResolver() {
		this(new ArrayList<EntityResolver>());
	}
	
	public CompositeEntityResolver(List<EntityResolver> children) {
		this.children = children;
	}
	
	public void addChildResolver(EntityResolver child) {
		children.add(child);
	}
	
	public void loadChildren() {
		for(EntityResolver child: ServiceLoader.load(EntityResolver.class)) {
			children.add(child);
		}
	}
	
	@Override
	public Class<? extends Entity<?>> find(String entityName) {
		for (EntityResolver child: children) {
			Class<? extends Entity<?>> result = child.find(entityName);
			if (result != null) {
				return result;
			}
		}
		return null;
	}	
	
}
