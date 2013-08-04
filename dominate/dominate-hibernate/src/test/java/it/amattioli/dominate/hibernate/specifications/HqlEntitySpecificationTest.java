package it.amattioli.dominate.hibernate.specifications;

import java.util.ArrayList;

import it.amattioli.dominate.repositories.Order;
import junit.framework.TestCase;

public class HqlEntitySpecificationTest extends TestCase {
	
	public void testAssembledQuery() throws Exception {
		HqlEntitySpecification<MyEntity,Long,MyEntity> spec = new HqlEntitySpecification<MyEntity, Long, MyEntity>("myProperty", MyEntity.class);
		spec.setValue(new MyEntity());
		HqlAssembler asm = new HqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(asm);
		String query = asm.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where entity_alias.myProperty = :myProperty", query);
	}
	
	public void testAssembledQueryNoAlias() throws Exception {
		HqlEntitySpecification<MyEntity,Long,MyEntity> spec = new HqlEntitySpecification<MyEntity, Long, MyEntity>("myProperty", MyEntity.class);
		spec.setValue(new MyEntity());
		HqlAssembler asm = new HqlAssembler("from MyEntity", new ArrayList<Order>(), false);
		spec.assembleQuery(asm);
		String query = asm.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity where myProperty = :myProperty", query);
	}
	
}
