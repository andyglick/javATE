package it.amattioli.dominate.jpa.specifications;

import java.util.ArrayList;
import java.util.Collections;

import it.amattioli.dominate.repositories.Order;
import it.amattioli.dominate.specifications.EntitySpecification;
import junit.framework.TestCase;

public class JpaqlEntitySpecificationTest extends TestCase {
	
	public void testConstruction() {
		EntitySpecification<MyEntity,Long,MyEntity> spec = EntitySpecification.newInstance("myProperty", MyEntity.class);
		assertTrue(spec.supportsAssembler(new JpaqlAssembler("", Collections.EMPTY_LIST)));
	}
	
	public void testAssembledQuery() throws Exception {
		JpaqlEntitySpecification<MyEntity,Long,MyEntity> spec = new JpaqlEntitySpecification<MyEntity, Long, MyEntity>("myProperty", MyEntity.class);
		spec.setValue(new MyEntity());
		JpaqlAssembler asm = new JpaqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(asm);
		String query = asm.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where entity_alias.myProperty = :myProperty", query);
	}
	
	public void testAssembledQueryNoAlias() throws Exception {
		JpaqlEntitySpecification<MyEntity,Long,MyEntity> spec = new JpaqlEntitySpecification<MyEntity, Long, MyEntity>("myProperty", MyEntity.class);
		spec.setValue(new MyEntity());
		JpaqlAssembler asm = new JpaqlAssembler("from MyEntity", new ArrayList<Order>(), false);
		spec.assembleQuery(asm);
		String query = asm.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity where myProperty = :myProperty", query);
	}
	
}
