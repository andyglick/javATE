package it.amattioli.dominate.repositories;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.RepositoryFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A repository factory that delegates the creation of repositories to other factories.
 * Users can register delegate factories with {@link #addFactory(RepositoryFactory)}
 * method. Then {@link #getRepository(Class)} will try on every registered factory
 * until it finds one that creates the resulting repository. The factories will be
 * called in the same order in which they were registered.
 * 
 * @author andrea
 *
 */
public class CompositeRepositoryFactory implements RepositoryFactory {
	private Collection<RepositoryFactory> factories = new ArrayList<RepositoryFactory>();
	
	/**
	 * Register a new factory
	 * 
	 * @param factory the factory to register
	 */
	public void addFactory(RepositoryFactory factory) {
		factories.add(factory);
	}
	
	@Override
	public <I extends Serializable, T extends Entity<I>> Repository<I, T> getRepository(Class<T> c) {
		for (RepositoryFactory curr : factories) {
			Repository<I, T> result = curr.getRepository(c);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	@Override
	public <I extends Serializable, T extends Entity<I>> Repository<I, T> getRepository(Collection<T> c) {
		for (RepositoryFactory curr : factories) {
			Repository<I, T> result = curr.getRepository(c);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

}
