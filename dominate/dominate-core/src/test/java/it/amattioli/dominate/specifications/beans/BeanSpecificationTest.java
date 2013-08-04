package it.amattioli.dominate.specifications.beans;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.ComparisonType;
import it.amattioli.dominate.specifications.MyEntity;
import it.amattioli.dominate.specifications.MyEnum;
import it.amattioli.dominate.specifications.beans.BeanSpecification;

import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;

import junit.framework.TestCase;

public class BeanSpecificationTest extends TestCase {

	public void testStringSpecificationPropertyType() {
		BeanSpecification<MyEntity> spec = new BeanSpecification<MyEntity>(MyEntity.class);
		DynaProperty dynaProperty = spec.getDynaClass().getDynaProperty("description");
		assertEquals(String.class,dynaProperty.getType());
	}
	
	public void testStringSpecificationComparisonPropertyType() {
		BeanSpecification<MyEntity> spec = new BeanSpecification<MyEntity>(MyEntity.class);
		DynaProperty dynaProperty = spec.getDynaClass().getDynaProperty("descriptionComparisonType");
		assertEquals(ComparisonType.class, dynaProperty.getType());
	}
	
	public void testStringSpecificationPropertyValue() throws Exception {
		BeanSpecification<MyEntity> spec = new BeanSpecification<MyEntity>(MyEntity.class);
		PropertyUtils.setProperty(spec, "description", "MyDescription");
		assertEquals("MyDescription", PropertyUtils.getProperty(spec, "description"));
	}
	
	public void testStringSpecificationComparisonPropertyValue() throws Exception {
		BeanSpecification<MyEntity> spec = new BeanSpecification<MyEntity>(MyEntity.class);
		PropertyUtils.setProperty(spec, "descriptionComparisonType", ComparisonType.EXACT);
		assertEquals(ComparisonType.EXACT, PropertyUtils.getProperty(spec, "descriptionComparisonType"));
	}
	
	public void testStringSpecificationSatisfied() throws Exception {
		BeanSpecification<MyEntity> spec = new BeanSpecification<MyEntity>(MyEntity.class);
		PropertyUtils.setProperty(spec, "description", "MyDescription");
		MyEntity e = new MyEntity();
		e.setDescription("MyDescription");
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testEnumSpecificationPropertyType() {
		BeanSpecification<MyEntity> spec = new BeanSpecification<MyEntity>(MyEntity.class);
		DynaProperty dynaProperty = spec.getDynaClass().getDynaProperty("enumProperty");
		assertTrue(dynaProperty.getType().isEnum());
	}
	
	public void testEnumSpecificationPropertyValue() throws Exception {
		BeanSpecification<MyEntity> spec = new BeanSpecification<MyEntity>(MyEntity.class);
		PropertyUtils.setProperty(spec, "enumProperty", MyEnum.VALUE2);
		assertEquals(MyEnum.VALUE2, PropertyUtils.getProperty(spec, "enumProperty"));
	}
	
	public void testEnumSpecificationSatisfied() throws Exception {
		BeanSpecification<MyEntity> spec = new BeanSpecification<MyEntity>(MyEntity.class);
		PropertyUtils.setProperty(spec, "enumProperty", MyEnum.VALUE3);
		MyEntity e = new MyEntity();
		e.setEnumProperty(MyEnum.VALUE3);
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testEntitySpecificationPropertyType() {
		BeanSpecification<MyEntity> spec = new BeanSpecification<MyEntity>(MyEntity.class);
		DynaProperty dynaProperty = spec.getDynaClass().getDynaProperty("entityProperty");
		assertTrue(Entity.class.isAssignableFrom(dynaProperty.getType()));
	}
	
	public void testEntitySpecificationPropertyValue() throws Exception {
		BeanSpecification<MyEntity> spec = new BeanSpecification<MyEntity>(MyEntity.class);
		MyEntity e = new MyEntity();
		PropertyUtils.setProperty(spec, "entityProperty", e);
		assertEquals(e, PropertyUtils.getProperty(spec, "entityProperty"));
	}
	
	public void testEntitySpecificationSatisfied() throws Exception {
		BeanSpecification<MyEntity> spec = new BeanSpecification<MyEntity>(MyEntity.class);
		MyEntity referenced = new MyEntity();
		PropertyUtils.setProperty(spec, "entityProperty", referenced);
		MyEntity e = new MyEntity();
		e.setEntityProperty(referenced);
		assertTrue(spec.isSatisfiedBy(e));
	}
}
