package it.amattioli.applicate.serviceFactory;

import java.io.Serializable;

import it.amattioli.applicate.browsing.ObjectBrowser;
import it.amattioli.applicate.browsing.ObjectBrowserImpl;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.resolver.*;

/**
 * A service factory that create {@link ObjectBrowser} objects.<p>
 * 
 * The name of the service to be created must follow the pattern:
 * <ul>
 *   <li><i>entityName</i>ObjectBrowser
 * </ul>
 * where <i>entityName</i> is the name of the entity that has to be browsed
 * by the created browser.<p>
 * 
 * @author andrea
 *
 */
public class ObjectBrowserFactory extends DefaultServiceFactory {
	private static final String SERVICE_NAME_SUFFIX = "ObjectBrowser";
	
	public ObjectBrowserFactory() {
	
	}
	
	public ObjectBrowserFactory(EntityResolver entityResolver) {
		super(entityResolver);
	}
	
	public <I extends Serializable, T extends Entity<I>> ObjectBrowser<I,T> createService(Class<T> entityClass) {
		return new ObjectBrowserImpl<I,T>(entityClass);
	}

	@Override
	protected String getServiceNameSuffix() {
		return SERVICE_NAME_SUFFIX;
	}

}
