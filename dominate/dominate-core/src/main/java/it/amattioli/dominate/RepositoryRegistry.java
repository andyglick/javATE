package it.amattioli.dominate;

import it.amattioli.dominate.memory.MemoryRepositoryFactory;

import java.io.Serializable;
import java.util.Collection;

/**
 * A singleton that can be used to retrieve {@link Repository} instances
 * 
 * @author andrea
 *
 */
public class RepositoryRegistry {
	private static RepositoryRegistry instance = new RepositoryRegistry();
	
	/**
	 * Retrieve the singleton instance of this class
	 * @return the singleton instance of this class
	 */
	public static RepositoryRegistry instance() {
		return instance;
	}
	
	/**
	 * Set the singleton instance that can be retrieved using {@link #instance()} 
	 * @param newInstance the instance to be set
	 */
	public static void setInstance(RepositoryRegistry newInstance) {
		RepositoryRegistry.instance = newInstance;
	}
	
	private Class<? extends RepositoryFactory> repositoryFactoryClass = MemoryRepositoryFactory.class;
	private RepositoryFactory repositoryFactory;
	
	/**
	 * Retrieve the repository factory class the registry will use to create new
	 * repository instances.
	 * 
	 * @return the repository factory the registry will use
	 */
	public Class<? extends RepositoryFactory> getRepositoryFactoryClass() {
		return repositoryFactoryClass;
	}

	/**
	 * Set the repository factory class the registry will use to create new
	 * repository instances.
	 * 
	 * @param repositoryFactoryClass the repository factory the registry will use
	 */
	public void setRepositoryFactoryClass(Class<? extends RepositoryFactory> repositoryFactoryClass) {
		this.repositoryFactoryClass = repositoryFactoryClass;
		this.repositoryFactory = null;
	}
	
	/**
	 * Return the repository factory object that this registry will use to create
	 * the repositories.
	 * 
	 * @return the repository factory object that this registry will use to create
	 *         the repositories
	 */
	public RepositoryFactory getRepositoryFactory() {
		if (repositoryFactory == null) {
			if (repositoryFactoryClass == null) {
				throw new IllegalStateException("Set repositoryFactoryClass before using the registry");
			}
			try {
				repositoryFactory = repositoryFactoryClass.newInstance();
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (InstantiationException e) {
				throw new RuntimeException(e);
			}
		} 
		return repositoryFactory;
	}
	
	public void setRepositoryFactory(RepositoryFactory repositoryFactory) {
		this.repositoryFactory = repositoryFactory;
	}
	
	/**
	 * Retrieve a repository for objects of the given class.
	 * 
	 * @param <I> The class of the id of the objects contained in the constructed repository
	 * @param <T> The class of the objects contained in the constructed repository
	 * @param c The class object of the objects contained in the repository
	 * @return
	 */
	public <I extends Serializable,T extends Entity<I>> Repository<I, T> getRepository(Class<T> c) {
		return getRepositoryFactory().getRepository(c);
	}
	
	/**
	 * Retrieve a collection repository for the given collection.
	 * 
	 * @param <I> The class of the id of the objects contained in the constructed repository
	 * @param <T> The class of the objects contained in the constructed repository
	 * @param c the collection to be adapted as a repository
	 * @return
	 */
	public <I extends Serializable, T extends Entity<I>> Repository<I, T> getRepository(Collection<T> coll) {
		return getRepositoryFactory().getRepository(coll);
	}
}
