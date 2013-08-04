package it.amattioli.dominate.hibernate.specifications;

import org.apache.commons.collections.functors.TruePredicate;
import org.hibernate.Criteria;
import org.hibernate.impl.CriteriaImpl;

import junit.framework.TestCase;
import it.amattioli.dominate.specifications.dflt.PredicateAssembler;

public class CriteriaStringSpecificationTest extends TestCase {

	public void testWithPredicateAssembler() {
		CriteriaStringSpecification<MyEntity> spec = new CriteriaStringSpecification<MyEntity>("");
		PredicateAssembler ass = new PredicateAssembler();
		spec.assembleQuery(ass);
		assertEquals(TruePredicate.class, ass.assembledPredicate().getClass());
	}
	
	public void testWithCriteriaAssembler() {
		CriteriaStringSpecification<MyEntity> spec = new CriteriaStringSpecification<MyEntity>("description");
		spec.setValue("descr1");
		CriteriaAssembler ass = new CriteriaAssembler(new CriteriaImpl("MyEntity", null));
		spec.assembleQuery(ass);
		Criteria crit = ass.assembledCriteria();
		assertEquals("CriteriaImpl(MyEntity:this[][description ilike descr1%])", crit.toString());
	}
	
}
