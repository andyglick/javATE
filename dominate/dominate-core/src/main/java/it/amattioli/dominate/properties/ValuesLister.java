package it.amattioli.dominate.properties;

import java.util.Collection;

public interface ValuesLister {

	public abstract Collection<?> getPropertyValues(String propertyName);

}