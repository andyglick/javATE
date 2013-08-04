package it.amattioli.dominate.specifications.dflt;

import it.amattioli.dominate.specifications.MyEntity;
import it.amattioli.dominate.specifications.MyEnum;
import junit.framework.TestCase;

public class DefaultEnumSpecificationTest extends TestCase {
	
	public void testSatisfied() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setEnumProperty(MyEnum.VALUE1);
		DefaultEnumSpecification<MyEntity, MyEnum> spec = new DefaultEnumSpecification<MyEntity, MyEnum>("enumProperty", MyEnum.class);
		spec.setValue(MyEnum.VALUE1);
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testNotSatisfied() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setEnumProperty(MyEnum.VALUE1);
		DefaultEnumSpecification<MyEntity, MyEnum> spec = new DefaultEnumSpecification<MyEntity, MyEnum>("enumProperty", MyEnum.class);
		spec.setValue(MyEnum.VALUE2);
		assertFalse(spec.isSatisfiedBy(e));
	}
	
	public void testNotSet() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setEnumProperty(MyEnum.VALUE1);
		DefaultEnumSpecification<MyEntity, MyEnum> spec = new DefaultEnumSpecification<MyEntity, MyEnum>("enumProperty", MyEnum.class);
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testPredicate() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setEnumProperty(MyEnum.VALUE1);
		DefaultEnumSpecification<MyEntity, MyEnum> spec = new DefaultEnumSpecification<MyEntity, MyEnum>("enumProperty", MyEnum.class);
		spec.setValue(MyEnum.VALUE1);
		PredicateAssembler a = new PredicateAssembler();
		spec.assembleQuery(a);
		assertTrue(a.assembledPredicate().evaluate(e));
	}
}
