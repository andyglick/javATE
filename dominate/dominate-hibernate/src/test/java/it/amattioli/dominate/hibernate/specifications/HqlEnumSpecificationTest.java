package it.amattioli.dominate.hibernate.specifications;

import java.util.ArrayList;

import it.amattioli.dominate.repositories.Order;
import junit.framework.TestCase;

public class HqlEnumSpecificationTest extends TestCase {

	public void testAssembledQuery() throws Exception {
		HqlEnumSpecification<MyEntity, MyEnum> spec = new HqlEnumSpecification<MyEntity, MyEnum>("myProperty", MyEnum.class);
		spec.setValue(MyEnum.VALUE1);
		HqlAssembler asm = new HqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(asm);
		String query = asm.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where entity_alias.myProperty like :myProperty", query);
	}
	
	public void testAssembledQueryNoAlias() throws Exception {
		HqlEnumSpecification<MyEntity, MyEnum> spec = new HqlEnumSpecification<MyEntity, MyEnum>("myProperty", MyEnum.class);
		spec.setValue(MyEnum.VALUE1);
		HqlAssembler asm = new HqlAssembler("from MyEntity", new ArrayList<Order>(), false);
		spec.assembleQuery(asm);
		String query = asm.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity where myProperty like :myProperty", query);
	}
	
}
