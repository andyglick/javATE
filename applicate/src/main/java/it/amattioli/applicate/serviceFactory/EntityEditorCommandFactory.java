package it.amattioli.applicate.serviceFactory;

import java.io.Serializable;

import it.amattioli.applicate.commands.EntityEditorCommand;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.resolver.EntityResolver;

/**
 * A service factory that create {@link EntityEditorCommand} objects.<p>
 * 
 * The name of the service to be created must follow the pattern:
 * <ul>
 *   <li><i>entityName</i>EntityEditorCommand
 * </ul>
 * where <i>entityName</i> is the name of the entity that has to be edited
 * by the created editor.<p>
 * 
 * @author andrea
 *
 */
public class EntityEditorCommandFactory extends DefaultServiceFactory {
	private static final String SERVICE_NAME_SUFFIX = "EntityEditorCommand";
	
	public EntityEditorCommandFactory() {
		
	}
	
	public EntityEditorCommandFactory(EntityResolver entityResolver) {
		super(entityResolver);
	}
	
	public <I extends Serializable, T extends Entity<I>> EntityEditorCommand<I,T> createService(Class<T> entityClass) {
		return new EntityEditorCommand<I, T>(entityClass);
	}
	
	@Override
	protected String getServiceNameSuffix() {
		return SERVICE_NAME_SUFFIX;
	}

}
