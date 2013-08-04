package it.amattioli.applicate.serviceFactory;

import java.io.Serializable;

import it.amattioli.applicate.browsing.DefaultTreeBrowser;
import it.amattioli.applicate.browsing.TreeBrowser;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.resolver.EntityResolver;

/**
 * A service factory that create {@link TreeBrowser} objects.<p>
 * 
 * The name of the service to be created must follow the pattern:
 * <ul>
 *   <li><i>entityName</i>TreeBrowser
 * </ul>
 * where <i>entityName</i> is the name of the entity that has to be browsed
 * by the created browser.<p>
 * 
 * By default the created browser will look for the parent instance of the entity
 *  using the "parent" property and it will look for the children of the entity
 *  using the "children" property.
 * 
 * @author andrea
 *
 */
public class TreeBrowserFactory extends DefaultServiceFactory {
	private static final String SERVICE_NAME_SUFFIX = "TreeBrowser";
	
	public TreeBrowserFactory() {
		
	}
	
	public TreeBrowserFactory(EntityResolver entityResolver) {
		super(entityResolver);
	}
	
	public <I extends Serializable, T extends Entity<I>> TreeBrowser<I,T> createService(Class<T> entityClass) {
		return new DefaultTreeBrowser<I,T>(entityClass, null, "children", "parent");
	}

	@Override
	protected String getServiceNameSuffix() {
		return SERVICE_NAME_SUFFIX;
	}
	
	

}
