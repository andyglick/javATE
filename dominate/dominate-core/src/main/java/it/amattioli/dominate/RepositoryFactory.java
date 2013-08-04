package it.amattioli.dominate;

import java.io.Serializable;
import java.util.Collection;

/**
 * An abstract factory used by {@link RepositoryRegistry} to create instances of
 * repositories. 
 * 
 * @author andrea
 *
 */
public interface RepositoryFactory {

	/**
	 * Create a repository for objects of the given class.
	 * 
	 * @param <I> The class of the id of the objects contained in the constructed repository
	 * @param <T> The class of the objects contained in the constructed repository
	 * @param c The class object of the objects contained in the constructed repository
	 * @return a new repository for objects of class c or null if this repositoryFactory cannot create
	 *         a repository for the given class
	 */
	<I extends Serializable, T extends Entity<I>> Repository<I, T> getRepository(Class<T> c);
	
	/**
	 * Create an adapter that present the given collection as a repository.
	 *  
	 * @param <I> The class of the id of the objects contained in the constructed repository
	 * @param <T> The class of the objects contained in the constructed repository
	 * @param coll The collection that will be adapted as a repository
	 * @return the newly created repository
	 */
	<I extends Serializable, T extends Entity<I>> Repository<I, T> getRepository(Collection<T> coll);

}