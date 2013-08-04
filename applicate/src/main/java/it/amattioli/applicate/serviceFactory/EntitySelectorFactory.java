package it.amattioli.applicate.serviceFactory;

import java.io.Serializable;

import it.amattioli.applicate.browsing.EntitySelector;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.resolver.EntityResolver;

/**
 * A service factory that create {@link EntitySelector} objects.<p>
 * 
 * The name of the service to be created must follow the pattern:
 * <ul>
 *   <li><i>entityName</i>EntitySelector
 * </ul>
 * where <i>entityName</i> is the name of the entity that has to be selected
 * by the created selector.<p>
 * 
 * By default the created selector will search using the "description" property
 * of the entity.
 * 
 * @author andrea
 *
 */
public class EntitySelectorFactory extends DefaultServiceFactory {
	private static final String SERVICE_NAME_SUFFIX = "EntitySelector";
	
	public EntitySelectorFactory() {
	}
	
	public EntitySelectorFactory(EntityResolver entityResolver) {
		super(entityResolver);
	}
	
	public <I extends Serializable, T extends Entity<I>> EntitySelector<I,T> createService(Class<T> entityClass) {
		return new EntitySelector<I,T>(entityClass, "description");
	}

	@Override
	protected String getServiceNameSuffix() {
		return SERVICE_NAME_SUFFIX;
	}

}
