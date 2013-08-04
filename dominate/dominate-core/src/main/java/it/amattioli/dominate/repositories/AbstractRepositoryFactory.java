package it.amattioli.dominate.repositories;

import java.io.Serializable;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.RepositoryFactory;

/**
 * This implementation of RepositoryFactory tries to find a custom factory method
 * for the repository of the given class. If it cannot find such a method it will
 * revert to a default repository.
 * 
 * The custom factory method for a repository of class MyClass must be called 
 * getMyClassRepository().
 * 
 * @author andrea
 *
 */
public abstract class AbstractRepositoryFactory implements RepositoryFactory {

	@Override
	public <I extends Serializable, T extends Entity<I>> Repository<I, T> getRepository(Class<T> c) {
		Repository<I, T> rep;
		try {
			rep = (Repository<I, T>)PropertyUtils.getProperty(this, StringUtils.uncapitalize(c.getSimpleName()+"Repository"));
		} catch (NoSuchMethodException nm) {
			rep = this.getDefaultRepository(c);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		return rep;
	}
	
	/**
	 * Create a default repository for objects of class c. This method is called by
	 * {@link #getRepository(Class)} if it cannot find a custom factory method
	 * 
	 * @param <I>
	 * @param <T>
	 * @param c
	 * @return
	 */
	protected abstract <I extends Serializable,T extends Entity<I>> Repository<I, T> getDefaultRepository(Class<T> c);

}
