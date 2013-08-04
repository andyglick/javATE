package it.amattioli.dominate.jpa.specifications;

import java.util.ArrayList;
import java.util.Collections;

import it.amattioli.dominate.repositories.Order;
import it.amattioli.dominate.specifications.MultipleEntitySpecification;
import junit.framework.TestCase;

public class JpaqlMultipleEntitySpecificationTest extends TestCase {
	
	public void testConstruction() {
		MultipleEntitySpecification<MyEntity,Long,MyEntity> spec = MultipleEntitySpecification.newInstance("myProperty", MyEntity.class);
		assertTrue(spec.supportsAssembler(new JpaqlAssembler("", Collections.EMPTY_LIST)));
	}

	public void testAssembledQuery() throws Exception {
		JpaqlMultipleEntitySpecification<MyEntity,Long,MyEntity> spec = new JpaqlMultipleEntitySpecification<MyEntity, Long, MyEntity>("myProperty", MyEntity.class);
		spec.setValue(new ArrayList<MyEntity>() {{ add(new MyEntity()); }});
		JpaqlAssembler asm = new JpaqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(asm);
		String query = asm.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where entity_alias.myProperty in (:myProperty0)", query);
	}
	
	public void testAssembledQueryNoAlias() throws Exception {
		JpaqlMultipleEntitySpecification<MyEntity,Long,MyEntity> spec = new JpaqlMultipleEntitySpecification<MyEntity, Long, MyEntity>("myProperty", MyEntity.class);
		spec.setValue(new ArrayList<MyEntity>() {{ add(new MyEntity()); }});
		JpaqlAssembler asm = new JpaqlAssembler("from MyEntity", new ArrayList<Order>(), false);
		spec.assembleQuery(asm);
		String query = asm.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity where myProperty in (:myProperty0)", query);
	}
	
}
