package it.amattioli.applicate.serviceFactory;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.resolver.CompositeEntityResolver;
import it.amattioli.dominate.resolver.EntityResolver;

public abstract class DefaultServiceFactory implements ServiceFactory {
	private EntityResolver entityResolver;
	
	public DefaultServiceFactory() {
		CompositeEntityResolver entityResolver = new CompositeEntityResolver();
		entityResolver.loadChildren();
		this.entityResolver = entityResolver;
	}
	
	public DefaultServiceFactory(EntityResolver entityResolver) {
		this.entityResolver = entityResolver;
	}
	
	@Override
	public Object createService(String serviceName) {
		Class entityClass = entityResolver.find(getEntityName(serviceName));
		return createService(entityClass);
	}

	protected abstract <I extends Serializable, T extends Entity<I>> Object createService(Class<T> entityClass);
	
	protected abstract String getServiceNameSuffix();
	
	private String getEntityName(String serviceName) {
		return StringUtils.substringBefore(serviceName, getServiceNameSuffix());
	}

	@Override
	public boolean knowsService(String serviceName) {
		return serviceName.endsWith(getServiceNameSuffix()) &&
		       entityResolver.find(getEntityName(serviceName)) != null;
	}
}
