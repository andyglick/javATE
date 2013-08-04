package it.amattioli.dominate.properties;

import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.apache.commons.beanutils.PropertyUtils;

public class ClassPropertyDependencies {
	private static final ClassPropertyDependencies instance = new ClassPropertyDependencies();
	
	public static ClassPropertyDependencies getInstance() {
		return instance;
	}
	
	private Map<Class<?>, PropertyDependencies> classDependencies = new WeakHashMap<Class<?>, PropertyDependencies>();
	
	private ClassPropertyDependencies() {
		
	}
	
	public Set<String> getDependencies(Class<?> ownerClass, String propertyName) {
		PropertyDependencies dep = classDependencies.get(ownerClass);
		if (dep == null) {
			addDependencies(ownerClass);
			dep = classDependencies.get(ownerClass);
		}
		return dep.getDependencies(propertyName);
	}
	
	public void addDependency(Class<?> ownerClass, String from, String to) {
		PropertyDependencies dep = classDependencies.get(ownerClass);
		if (dep == null) {
			dep = new PropertyDependencies();
			classDependencies.put(ownerClass, dep);
		}
		dep.addDependency(from, to);
	}
	
	public void addDependencies(Class<?> ownerClass) {
		PropertyDependencies dep = new PropertyDependencies();
		classDependencies.put(ownerClass, dep);
		AnnotationsRetrieverImpl retriever = new AnnotationsRetrieverImpl(ownerClass);
		
		for (PropertyDescriptor currProperty: PropertyUtils.getPropertyDescriptors(ownerClass)) {
			Derived d = retriever.retrieveAnnotation(currProperty.getName(), Derived.class);
			if (d != null) {
				for (String currFrom: d.dependsOn()) {
					addDependency(ownerClass, currFrom, currProperty.getName());
				}
			}
		}
	}
	
	public void clear() {
		classDependencies.clear();
	}
}
