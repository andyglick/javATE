package it.amattioli.dominate.jpa.specifications;

import java.util.ArrayList;
import java.util.Collections;

import it.amattioli.dominate.repositories.Order;
import it.amattioli.dominate.specifications.MultipleEnumSpecification;
import static it.amattioli.dominate.jpa.specifications.MyEnum.*;
import junit.framework.TestCase;

public class JpaqlMultipleEnumSpecificationTest extends TestCase {
	
	public void testConstruction() {
		MultipleEnumSpecification<MyEntity, MyEnum> spec = MultipleEnumSpecification.newInstance("myProperty", MyEnum.class);
		assertTrue(spec.supportsAssembler(new JpaqlAssembler("", Collections.EMPTY_LIST)));
	}

	public void testAssembledQuery() throws Exception {
		JpaqlMultipleEnumSpecification<MyEntity, MyEnum> spec = new JpaqlMultipleEnumSpecification<MyEntity, MyEnum>("myProperty", MyEnum.class);
		spec.setValue(new ArrayList<MyEnum>() {{ add(VALUE1); add(VALUE2);}});
		JpaqlAssembler asm = new JpaqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(asm);
		String query = asm.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where entity_alias.myProperty in (:myProperty0, :myProperty1)", query);
	}
	
	public void testAssembledQueryNoAlias() throws Exception {
		JpaqlMultipleEnumSpecification<MyEntity, MyEnum> spec = new JpaqlMultipleEnumSpecification<MyEntity, MyEnum>("myProperty", MyEnum.class);
		spec.setValue(new ArrayList<MyEnum>() {{ add(VALUE1); add(VALUE2);}});
		JpaqlAssembler asm = new JpaqlAssembler("from MyEntity", new ArrayList<Order>(), false);
		spec.assembleQuery(asm);
		String query = asm.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity where myProperty in (:myProperty0, :myProperty1)", query);
	}
	
}
