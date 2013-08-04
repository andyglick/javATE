package it.amattioli.dominate.specifications;

import org.apache.commons.collections.Predicate;
//import org.hibernate.Criteria;
//import org.hibernate.impl.CriteriaImpl;

import it.amattioli.dominate.specifications.dflt.PredicateAssembler;
//import it.amattioli.dominate.specifications.hibernate.CriteriaAssembler;
import junit.framework.TestCase;

public class SpecificationTest extends TestCase {

	public void testSatisfied() {
		MyEntity e1 = new MyEntity();
		e1.setDescription("descr1");
		MyEntity e2 = new MyEntity();
		e2.setDescription("descr2");
		MySpecification s = new MySpecification();
		s.setDescription("descr1");
		assertTrue(s.isSatisfiedBy(e1));
		assertFalse(s.isSatisfiedBy(e2));
	}
	
	public void testPredicate() {
		MyEntity e1 = new MyEntity();
		e1.setDescription("descr1");
		MyEntity e2 = new MyEntity();
		e2.setDescription("descr2");
		MySpecification s = new MySpecification();
		s.setDescription("descr1");
		PredicateAssembler assembler = new PredicateAssembler();
		assertTrue(s.supportsAssembler(assembler));
		s.assembleQuery(assembler);
		Predicate p = assembler.assembledPredicate();
		assertTrue(p.evaluate(e1));
		assertFalse(p.evaluate(e2));
	}
	/*
	public void testHibernateCriteria() {
		MySpecification s = new MySpecification();
		s.setDescription("descr1");
		CriteriaAssembler ass = new CriteriaAssembler(new CriteriaImpl("MyEntity", null));
		assertTrue(s.supportsAssembler(ass));
		s.assembleQuery(ass);
		Criteria crit = ass.assembledCriteria();
		assertEquals("CriteriaImpl(MyEntity:this[][description ilike descr1%])", crit.toString());
	}
	*/
	
}
