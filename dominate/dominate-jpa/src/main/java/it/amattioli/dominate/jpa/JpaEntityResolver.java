package it.amattioli.dominate.jpa;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.resolver.EntityResolver;

public class JpaEntityResolver implements EntityResolver {

	@Override
	public Class<? extends Entity<?>> find(String entityName) {
		Metamodel metamodel = JpaSessionManager.getEntityManagerFactory().getMetamodel();
		for (EntityType<?> type : metamodel.getEntities()) {
			Class mappedClass = type.getJavaType();
			if (mappedClass.getSimpleName().equals(entityName)) {
				return mappedClass;
			}
		}
		return null;
	}

}
