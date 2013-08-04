package it.amattioli.dominate.jpa.specifications;

import java.util.ArrayList;
import java.util.Collections;

import it.amattioli.dominate.repositories.Order;
import it.amattioli.dominate.specifications.CollectionSpecification;
import junit.framework.TestCase;

public class JpaqlCollectionSpecificationTest extends TestCase {
	
	public void testConstruction() {
		CollectionSpecification<MyEntity> spec = CollectionSpecification.newInstance("myProperty");
		assertTrue(spec.supportsAssembler(new JpaqlAssembler("", Collections.EMPTY_LIST)));
	}

	public void testAssembledQuery() throws Exception {
		JpaqlCollectionSpecification<MyEntity> spec = new JpaqlCollectionSpecification<MyEntity>("collectionProperty");
		spec.setValue("TestValue");
		JpaqlAssembler asm = new JpaqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(asm);
		String query = asm.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where :collectionProperty in elements(entity_alias.collectionProperty)", query);
	}

}
