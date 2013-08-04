package it.amattioli.dominate.jpa.specifications;

import java.util.ArrayList;
import java.util.Collections;

import it.amattioli.dominate.repositories.Order;
import it.amattioli.dominate.specifications.LongSpecification;
import it.amattioli.dominate.specifications.TotalOrderComparisonType;
import junit.framework.TestCase;

public class JpaqlLongSpecificationTest extends TestCase {
	
	public void testConstruction() {
		LongSpecification<MyEntity> spec = LongSpecification.newInstance("myProperty");
		assertTrue(spec.supportsAssembler(new JpaqlAssembler("", Collections.EMPTY_LIST)));
	}

	public void testEquality() throws Exception {
		JpaqlLongSpecification<MyEntity> spec = new JpaqlLongSpecification<MyEntity>("myProperty");
		spec.setValue(1L);
		spec.setComparisonType(TotalOrderComparisonType.EQUAL);
		JpaqlAssembler asm = new JpaqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(asm);
		String query = asm.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where entity_alias.myProperty = :myProperty", query);
	}
	
	public void testGreaterThan() throws Exception {
		JpaqlLongSpecification<MyEntity> spec = new JpaqlLongSpecification<MyEntity>("myProperty");
		spec.setValue(1L);
		spec.setComparisonType(TotalOrderComparisonType.GREATER);
		JpaqlAssembler asm = new JpaqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(asm);
		String query = asm.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where entity_alias.myProperty > :myProperty", query);
	}
	
	public void testEqualityNoAlias() throws Exception {
		JpaqlLongSpecification<MyEntity> spec = new JpaqlLongSpecification<MyEntity>("myProperty");
		spec.setValue(1L);
		spec.setComparisonType(TotalOrderComparisonType.EQUAL);
		JpaqlAssembler asm = new JpaqlAssembler("from MyEntity", new ArrayList<Order>(), false);
		spec.assembleQuery(asm);
		String query = asm.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity where myProperty = :myProperty", query);
	}
}
