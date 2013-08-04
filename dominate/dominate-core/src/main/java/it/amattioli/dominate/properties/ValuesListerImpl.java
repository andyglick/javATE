package it.amattioli.dominate.properties;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.RepositoryRegistry;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;

public class ValuesListerImpl implements ValuesLister {
	private Object bean;
	private static final String VALUES_SUBFIX = "Values";
	
	public ValuesListerImpl(Object bean) {
		if (bean == null) {
			throw new NullPointerException();
		}
		this.bean = bean;
	}

	/* (non-Javadoc)
	 * @see it.amattioli.applicate.properties.ValuesLister#getPropertyValues(java.lang.String)
	 */
	public Collection<?> getPropertyValues(String propertyName) {
		try {
			try {
				return (Collection<?>)valuesMethod(propertyName).invoke(bean);
			} catch (NoSuchMethodException e) {
				PropertyClass pClass = new PropertyClassRetrieverImpl(bean).retrievePropertyClass(propertyName);
				if (pClass.getElementClass().isEnum()) {
					return Arrays.asList(pClass.getElementClass().getEnumConstants());
				} else if (Entity.class.isAssignableFrom(pClass.getElementClass())) {
					Class<Entity> entityClass = (Class<Entity>)pClass.getElementClass();
					Repository repository = RepositoryRegistry.instance().getRepository(entityClass);
					if (repository == null) {
						throw new RuntimeException("Cannot find repository for class "+entityClass);
					}
					return repository.list();
				}
				return Collections.emptyList();
			}
		} catch(Exception e) {
			throw new RuntimeException("Error retrieving possible values for property "+propertyName+" of bean "+bean,e);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see it.amattioli.applicate.properties.ValuesLister#valuesProperty(java.lang.String)
	 */
	public static String valuesProperty(String propertyName) {
		return propertyName + VALUES_SUBFIX; 
	}
	
	public Method valuesMethod(String propertyName) throws NoSuchMethodException {
		Method getValuesMethod = bean.getClass().getMethod("get"+StringUtils.capitalize(valuesProperty(propertyName)));
		return getValuesMethod;
	}

}
