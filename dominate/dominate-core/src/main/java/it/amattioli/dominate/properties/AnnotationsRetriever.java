package it.amattioli.dominate.properties;

import java.lang.annotation.Annotation;

public interface AnnotationsRetriever {

	public <T extends Annotation> T retrieveAnnotation(String propertyName, Class<T> anotationClass);
	
	public Annotation[] retrieveAnnotations(String propertyName);

}
