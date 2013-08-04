package it.amattioli.dominate.hibernate.specifications;

import java.util.ArrayList;
import java.util.Collections;

import it.amattioli.dominate.repositories.Order;
import it.amattioli.dominate.specifications.CollectionSpecification;
import junit.framework.TestCase;

public class HqlCollectionSpecificationTest extends TestCase {
	
	public void testConstruction() {
		CollectionSpecification<MyEntity> spec = CollectionSpecification.newInstance("myProperty");
		assertTrue(spec.supportsAssembler(new HqlAssembler("", Collections.EMPTY_LIST)));
	}

	public void testAssembledQuery() throws Exception {
		HqlCollectionSpecification<MyEntity> spec = new HqlCollectionSpecification<MyEntity>("collectionProperty");
		spec.setValue("TestValue");
		HqlAssembler asm = new HqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(asm);
		String query = asm.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where :collectionProperty in elements(collectionProperty)", query);
	}

}
