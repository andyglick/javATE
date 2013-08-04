package it.amattioli.dominate.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotatedConstraint implements Constraint {
	private Annotation annotation;
	
	public AnnotatedConstraint(Annotation annotation) {
		this.annotation = annotation;
	}
	
	@Override
	public String getName() {
		return annotation.annotationType().getName();
	}

	@Override
	public Object getParameter(String name) {
		try {
			Method paramMethod = annotation.getClass().getMethod(name);
			return paramMethod.invoke(annotation);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			return null;
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

}
