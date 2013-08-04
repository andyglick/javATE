package it.amattioli.dominate.jpa.specifications;

import java.util.ArrayList;
import java.util.Collections;

import it.amattioli.dominate.repositories.Order;
import it.amattioli.dominate.specifications.EnumSpecification;
import junit.framework.TestCase;

public class JpaqlEnumSpecificationTest extends TestCase {

	public void testConstruction() {
		EnumSpecification<MyEntity, MyEnum> spec = EnumSpecification.newInstance("myProperty", MyEnum.class);
		assertTrue(spec.supportsAssembler(new JpaqlAssembler("", Collections.EMPTY_LIST)));
	}
	
	public void testAssembledQuery() throws Exception {
		JpaqlEnumSpecification<MyEntity, MyEnum> spec = new JpaqlEnumSpecification<MyEntity, MyEnum>("myProperty", MyEnum.class);
		spec.setValue(MyEnum.VALUE1);
		JpaqlAssembler asm = new JpaqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(asm);
		String query = asm.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where entity_alias.myProperty like :myProperty", query);
	}
	
	public void testAssembledQueryNoAlias() throws Exception {
		JpaqlEnumSpecification<MyEntity, MyEnum> spec = new JpaqlEnumSpecification<MyEntity, MyEnum>("myProperty", MyEnum.class);
		spec.setValue(MyEnum.VALUE1);
		JpaqlAssembler asm = new JpaqlAssembler("from MyEntity", new ArrayList<Order>(), false);
		spec.assembleQuery(asm);
		String query = asm.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity where myProperty like :myProperty", query);
	}
	
}
