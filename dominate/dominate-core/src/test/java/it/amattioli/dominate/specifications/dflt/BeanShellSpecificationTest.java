package it.amattioli.dominate.specifications.dflt;

import it.amattioli.dominate.specifications.MyEntity;
import junit.framework.TestCase;

public class BeanShellSpecificationTest extends TestCase {

	public void testSatisfied() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setDescription("Descr");
		String code = "\"Descr\".equals(description)";
		BeanShellSpecification<MyEntity> spec = new BeanShellSpecification<MyEntity>(code);
		assertTrue(spec.isSatisfiedBy(e));
	}
	
	public void testNotSatisfied() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setDescription("Descr1");
		String code = "\"Descr\".equals(description)";
		BeanShellSpecification<MyEntity> spec = new BeanShellSpecification<MyEntity>(code);
		assertFalse(spec.isSatisfiedBy(e));
	}
	
	public void testAssembler() {
		MyEntity e = new MyEntity();
		e.setId(1L);
		e.setDescription("Descr");
		String code = "\"Descr\".equals(description)";
		BeanShellSpecification<MyEntity> spec = new BeanShellSpecification<MyEntity>(code);
		PredicateAssembler a = new PredicateAssembler();
		spec.assembleQuery(a);
		assertTrue(a.assembledPredicate().evaluate(e));
	}
	
}
