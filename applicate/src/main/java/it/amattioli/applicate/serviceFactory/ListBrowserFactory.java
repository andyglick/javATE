package it.amattioli.applicate.serviceFactory;

import java.io.Serializable;

import it.amattioli.applicate.browsing.ListBrowser;
import it.amattioli.applicate.browsing.ListBrowserImpl;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.resolver.*;
import it.amattioli.dominate.specifications.beans.BeanSpecification;

/**
 * A service factory that create {@link ListBrowser} objects.<p>
 * 
 * The name of the service to be created must follow the pattern:
 * <ul>
 *   <li><i>entityName</i>ListBrowser
 * </ul>
 * where <i>entityName</i> is the name of the entity that has to be browsed
 * by the created browser.<p>
 * 
 * By default the created browser will have associated a BeanSpecification
 * instance.
 * 
 * @author andrea
 *
 */
public class ListBrowserFactory extends DefaultServiceFactory {
	private static final String SERVICE_NAME_SUFFIX = "ListBrowser";
	
	public ListBrowserFactory() {
	
	}
	
	public ListBrowserFactory(EntityResolver entityResolver) {
		super(entityResolver);
	}
	
	public <I extends Serializable, T extends Entity<I>> ListBrowser<I,T> createService(Class<T> entityClass) {
		ListBrowser<I,T> result = new ListBrowserImpl<I,T>(entityClass);
		BeanSpecification<T> spec = new BeanSpecification<T>(entityClass);
		result.setSpecification(spec);
		return result;
	}

	@Override
	protected String getServiceNameSuffix() {
		return SERVICE_NAME_SUFFIX;
	}
	
	

}
