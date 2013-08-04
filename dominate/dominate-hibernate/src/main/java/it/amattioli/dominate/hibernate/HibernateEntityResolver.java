package it.amattioli.dominate.hibernate;

import java.util.Collection;

import org.hibernate.EntityMode;
import org.hibernate.metadata.ClassMetadata;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.resolver.EntityResolver;

public class HibernateEntityResolver implements EntityResolver {

	@Override
	public Class<? extends Entity<?>> find(String entityName) {
		Collection<ClassMetadata> allMappedClasses = (Collection<ClassMetadata>)HibernateSessionManager.getSessionFactory().getAllClassMetadata().values();
		for (ClassMetadata curr: allMappedClasses) {
			Class mappedClass = curr.getMappedClass(EntityMode.POJO);
			if (mappedClass.getSimpleName().equals(entityName)) {
				return mappedClass;
			}
		}
		return null;
	}

}
