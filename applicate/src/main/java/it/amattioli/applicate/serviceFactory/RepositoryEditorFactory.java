package it.amattioli.applicate.serviceFactory;

import java.io.Serializable;

import it.amattioli.applicate.editors.RepositoryEditor;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.resolver.EntityResolver;

/**
 * A service factory that create {@link RepositoryEditor} objects.<p>
 * 
 * The name of the service to be created must follow the pattern:
 * <ul>
 *   <li><i>entityName</i>RepositoryEditor
 * </ul>
 * where <i>entityName</i> is the name of the entity whose repository has to be edited
 * by the created editor.<p>
 * 
 * @author andrea
 *
 */
public class RepositoryEditorFactory extends DefaultServiceFactory {
	private static final String SERVICE_NAME_SUFFIX = "RepositoryEditor";
	
	public RepositoryEditorFactory() {
	
	}
	
	public RepositoryEditorFactory(EntityResolver entityResolver) {
		super(entityResolver);
	}
	
	public <I extends Serializable, T extends Entity<I>> RepositoryEditor<I,T> createService(Class<T> entityClass) {
		return new RepositoryEditor<I, T>(entityClass);
	}

	@Override
	protected String getServiceNameSuffix() {
		return SERVICE_NAME_SUFFIX;
	}
	
	
}
