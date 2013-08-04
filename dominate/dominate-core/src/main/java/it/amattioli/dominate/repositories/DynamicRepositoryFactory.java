package it.amattioli.dominate.repositories;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.RepositoryFactory;

/**
 * A DynamicRepositoryFactory is able to store repository instances and retrieve the same instance
 * every time a repository of a certain class is needed.
 * 
 * You can configure the factory using {@link #addRepository(Class, Repository)}.
 * 
 * @author andrea
 *
 */
public class DynamicRepositoryFactory implements RepositoryFactory {
	private Map<Class<?>, Repository<?,?>> repositories = new HashMap<Class<?>, Repository<?,?>>();

	public <I extends Serializable, T extends Entity<I>> void addRepository(Class<T> c, Repository<I, T> rep) {
		repositories.put(c, rep);
	}
	
	@Override
	public <I extends Serializable, T extends Entity<I>> Repository<I, T> getRepository(Class<T> c) {
		return (Repository<I, T>)repositories.get(c);
	}

	@Override
	public <I extends Serializable, T extends Entity<I>> Repository<I, T> getRepository(Collection<T> coll) {
		return null;
	}

}
