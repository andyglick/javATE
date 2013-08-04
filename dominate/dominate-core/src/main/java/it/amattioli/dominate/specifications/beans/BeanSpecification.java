package it.amattioli.dominate.specifications.beans;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.ConjunctionSpecification;

public class BeanSpecification<T extends Entity<?>> extends ConjunctionSpecification<T> implements DynaBean {
	private static ArrayList<PropertySpecificationFactory> factories = new ArrayList<PropertySpecificationFactory>();
	static {
		for (PropertySpecificationFactory factory: ServiceLoader.load(PropertySpecificationFactory.class)) {
			registerFactory(factory);
		}
	};
	
	public static void registerFactory(PropertySpecificationFactory factory) {
		factories.add(0,factory);
	}
	
	public static Collection<PropertySpecificationFactory> getFactories() {
		return factories;
	}
	
	private Map<String, PropertySpecification<T>> specs = new HashMap<String, PropertySpecification<T>>();
	private DynaClass dynaClass;
	
	public BeanSpecification(DynaClass dynaClass) {
		this.dynaClass = dynaClass;
	}
	
	public BeanSpecification(Class<T> beanClass) {
		List<DynaProperty> props = new ArrayList<DynaProperty>();
		for (PropertyDescriptor descriptor: PropertyUtils.getPropertyDescriptors(beanClass)) {
			PropertySpecification<T> propertySpec = createPropertySpecification(descriptor);
			addSpecification(propertySpec);
			props.addAll(propertySpec.getProperties());
		}
		dynaClass = new BasicDynaClass(beanClass.getName()+"Specification", 
				                       BeanSpecification.class, 
				                       props.toArray(new DynaProperty[props.size()]));
	}
	
	private PropertySpecification<T> createPropertySpecification(PropertyDescriptor descriptor) {
		for (PropertySpecificationFactory factory: factories) {
			if (factory.canBuildSpecificationFor(descriptor)) {
				return factory.newSpecification(descriptor);
			}
		}
		return null;
	}
	
	private void addSpecification(PropertySpecification<T> spec) {
		addSpecification(spec.getSpecification());
		for (String propertyName: spec.getPropertyNames()) {
			specs.put(propertyName, spec);
		}
	}

	private PropertySpecification<T> getPropertySpec(String propertyName) {
		return specs.get(propertyName);
	}
	
	@Override
	public boolean contains(String name, String key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object get(String arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object get(String arg0, String arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object get(String propertyName) {
		return getPropertySpec(propertyName).get(propertyName);
	}

	@Override
	public DynaClass getDynaClass() {
		return dynaClass;
	}

	@Override
	public void remove(String arg0, String arg1) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void set(String arg0, int arg1, Object arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void set(String propertyName, Object specValue) {
		Object old = get(propertyName);
		getPropertySpec(propertyName).set(propertyName, specValue);
	}

	@Override
	public void set(String arg0, String arg1, Object arg2) {
		throw new UnsupportedOperationException();
	}

}
