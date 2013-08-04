package it.amattioli.applicate.serviceFactory;

import java.io.Serializable;

import it.amattioli.applicate.commands.tree.TreeEditorCommand;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.resolver.EntityResolver;

/**
 * A service factory that create {@link TreeEditorCommand} objects.<p>
 * 
 * The name of the service to be created must follow the pattern:
 * <ul>
 *   <li><i>entityName</i>TreeBrowser
 * </ul>
 * where <i>entityName</i> is the name of the entity whose tree has to be edited
 * by the created editor.<p>
 * 
 * By default the created editor will look for the parent instance of the entity
 *  using the "parent" property and it will look for the children of the entity
 *  using the "children" property.
 * 
 * @author andrea
 *
 */
public class TreeEditorCommandFactory extends DefaultServiceFactory {
	private static final String SERVICE_NAME_SUFFIX = "TreeEditorCommand";
	
	public TreeEditorCommandFactory() {
		
	}
	
	public TreeEditorCommandFactory(EntityResolver entityResolver) {
		super(entityResolver);
	}
	
	public <I extends Serializable, T extends Entity<I>> TreeEditorCommand<I,T> createService(Class<T> entityClass) {
		return new TreeEditorCommand<I,T>(entityClass);
	}

	@Override
	protected String getServiceNameSuffix() {
		return SERVICE_NAME_SUFFIX;
	}
	
	

}
