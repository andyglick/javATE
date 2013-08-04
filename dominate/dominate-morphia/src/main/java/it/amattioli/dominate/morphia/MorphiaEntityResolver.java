package it.amattioli.dominate.morphia;

import java.util.Collection;

import com.google.code.morphia.mapping.MappedClass;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.resolver.EntityResolver;

public class MorphiaEntityResolver implements EntityResolver {

	@Override
	public Class<? extends Entity<?>> find(String entityName) {
		Collection<MappedClass> mappedClasses = MorphiaSessionManager.getMorphia().getMapper().getMappedClasses();
		for (MappedClass mappedClass: mappedClasses) {
			String mappedClassName = mappedClass.getClazz().getSimpleName();
			if (mappedClassName.equals(entityName)) {
				return (Class<? extends Entity<?>>)mappedClass.getClazz();
			}
		}
		return null;
	}

}
