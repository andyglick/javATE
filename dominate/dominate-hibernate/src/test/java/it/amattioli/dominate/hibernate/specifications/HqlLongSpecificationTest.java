package it.amattioli.dominate.hibernate.specifications;

import java.util.ArrayList;

import it.amattioli.dominate.repositories.Order;
import it.amattioli.dominate.hibernate.specifications.MyEntity;
import it.amattioli.dominate.specifications.TotalOrderComparisonType;
import junit.framework.TestCase;

public class HqlLongSpecificationTest extends TestCase {

	public void testEquality() throws Exception {
		HqlLongSpecification<MyEntity> spec = new HqlLongSpecification<MyEntity>("myProperty");
		spec.setValue(1L);
		spec.setComparisonType(TotalOrderComparisonType.EQUAL);
		HqlAssembler asm = new HqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(asm);
		String query = asm.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where entity_alias.myProperty = :myProperty", query);
	}
	
	public void testGreaterThan() throws Exception {
		HqlLongSpecification<MyEntity> spec = new HqlLongSpecification<MyEntity>("myProperty");
		spec.setValue(1L);
		spec.setComparisonType(TotalOrderComparisonType.GREATER);
		HqlAssembler asm = new HqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(asm);
		String query = asm.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where entity_alias.myProperty > :myProperty", query);
	}
	
	public void testEqualityNoAlias() throws Exception {
		HqlLongSpecification<MyEntity> spec = new HqlLongSpecification<MyEntity>("myProperty");
		spec.setValue(1L);
		spec.setComparisonType(TotalOrderComparisonType.EQUAL);
		HqlAssembler asm = new HqlAssembler("from MyEntity", new ArrayList<Order>(), false);
		spec.assembleQuery(asm);
		String query = asm.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity where myProperty = :myProperty", query);
	}
}
