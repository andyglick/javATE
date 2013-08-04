package it.amattioli.dominate.specifications.dflt;

import java.util.Arrays;
import java.util.Collection;

import it.amattioli.dominate.specifications.MyEntity;
import it.amattioli.dominate.specifications.MyEnum;
import junit.framework.TestCase;

public class DefaultMultipleEnumSpecificationTest extends TestCase {

	public void testSatisfied() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setEnumProperty(MyEnum.VALUE1);
		DefaultMultipleEnumSpecification<MyEntity, MyEnum> spec = new DefaultMultipleEnumSpecification<MyEntity, MyEnum>("enumProperty", MyEnum.class);
		Collection<MyEnum> value = Arrays.asList(new MyEnum[] {MyEnum.VALUE1, MyEnum.VALUE3});
		spec.setValue(value);
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testNotSatisfied() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setEnumProperty(MyEnum.VALUE2);
		DefaultMultipleEnumSpecification<MyEntity, MyEnum> spec = new DefaultMultipleEnumSpecification<MyEntity, MyEnum>("enumProperty", MyEnum.class);
		Collection<MyEnum> value = Arrays.asList(new MyEnum[] {MyEnum.VALUE1, MyEnum.VALUE3});
		spec.setValue(value);
		assertFalse(spec.isSatisfiedBy(e));
	}
	
	public void testNotSet() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setEnumProperty(MyEnum.VALUE1);
		DefaultMultipleEnumSpecification<MyEntity, MyEnum> spec = new DefaultMultipleEnumSpecification<MyEntity, MyEnum>("enumProperty", MyEnum.class);
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testAssembler() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setEnumProperty(MyEnum.VALUE1);
		DefaultMultipleEnumSpecification<MyEntity, MyEnum> spec = new DefaultMultipleEnumSpecification<MyEntity, MyEnum>("enumProperty", MyEnum.class);
		Collection<MyEnum> value = Arrays.asList(new MyEnum[] {MyEnum.VALUE1, MyEnum.VALUE3});
		spec.setValue(value);
		PredicateAssembler a = new PredicateAssembler();
		spec.assembleQuery(a);
		assertTrue(a.assembledPredicate().evaluate(e));
	}

}
