package it.amattioli.dominate.hibernate.specifications;

import java.util.ArrayList;

import it.amattioli.dominate.repositories.Order;
import junit.framework.TestCase;

public class HqlMultipleEntitySpecificationTest extends TestCase {

	public void testAssembledQuery() throws Exception {
		HqlMultipleEntitySpecification<MyEntity,Long,MyEntity> spec = new HqlMultipleEntitySpecification<MyEntity, Long, MyEntity>("myProperty", MyEntity.class);
		spec.setValue(new ArrayList<MyEntity>() {{ add(new MyEntity()); }});
		HqlAssembler asm = new HqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(asm);
		String query = asm.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where entity_alias.myProperty in (:myProperty0)", query);
	}
	
	public void testAssembledQueryNoAlias() throws Exception {
		HqlMultipleEntitySpecification<MyEntity,Long,MyEntity> spec = new HqlMultipleEntitySpecification<MyEntity, Long, MyEntity>("myProperty", MyEntity.class);
		spec.setValue(new ArrayList<MyEntity>() {{ add(new MyEntity()); }});
		HqlAssembler asm = new HqlAssembler("from MyEntity", new ArrayList<Order>(), false);
		spec.assembleQuery(asm);
		String query = asm.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity where myProperty in (:myProperty0)", query);
	}
	
}
