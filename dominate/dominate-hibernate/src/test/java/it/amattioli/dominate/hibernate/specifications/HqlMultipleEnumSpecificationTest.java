package it.amattioli.dominate.hibernate.specifications;

import java.util.ArrayList;

import it.amattioli.dominate.repositories.Order;
import static it.amattioli.dominate.hibernate.specifications.MyEnum.*;
import junit.framework.TestCase;

public class HqlMultipleEnumSpecificationTest extends TestCase {

	public void testAssembledQuery() throws Exception {
		HqlMultipleEnumSpecification<MyEntity, MyEnum> spec = new HqlMultipleEnumSpecification<MyEntity, MyEnum>("myProperty", MyEnum.class);
		spec.setValue(new ArrayList<MyEnum>() {{ add(VALUE1); add(VALUE2);}});
		HqlAssembler asm = new HqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(asm);
		String query = asm.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where entity_alias.myProperty in (:myProperty0, :myProperty1)", query);
	}
	
	public void testAssembledQueryNoAlias() throws Exception {
		HqlMultipleEnumSpecification<MyEntity, MyEnum> spec = new HqlMultipleEnumSpecification<MyEntity, MyEnum>("myProperty", MyEnum.class);
		spec.setValue(new ArrayList<MyEnum>() {{ add(VALUE1); add(VALUE2);}});
		HqlAssembler asm = new HqlAssembler("from MyEntity", new ArrayList<Order>(), false);
		spec.assembleQuery(asm);
		String query = asm.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity where myProperty in (:myProperty0, :myProperty1)", query);
	}
	
}
