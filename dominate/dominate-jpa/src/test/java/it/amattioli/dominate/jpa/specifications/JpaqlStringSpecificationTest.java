package it.amattioli.dominate.jpa.specifications;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.collections.functors.TruePredicate;

import junit.framework.TestCase;
import it.amattioli.dominate.repositories.Order;
import it.amattioli.dominate.specifications.StringSpecification;
import it.amattioli.dominate.specifications.dflt.PredicateAssembler;

public class JpaqlStringSpecificationTest extends TestCase {

	public void testConstruction() {
		StringSpecification<MyEntity> spec = StringSpecification.newInstance("description");
		assertTrue(spec.supportsAssembler(new JpaqlAssembler("", Collections.EMPTY_LIST)));
	}
	
	public void testWithPredicateAssembler() {
		JpaqlStringSpecification<MyEntity> spec = new JpaqlStringSpecification<MyEntity>("");
		PredicateAssembler ass = new PredicateAssembler();
		spec.assembleQuery(ass);
		assertEquals(TruePredicate.class, ass.assembledPredicate().getClass());
	}
	
	public void testWithHqlAssembler() throws Exception {
		JpaqlStringSpecification<MyEntity> spec = new JpaqlStringSpecification<MyEntity>("description");
		spec.setValue("descr1");
		JpaqlAssembler ass = new JpaqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(ass);
		String query = ass.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where upper(entity_alias.description) like :description", query);
	}
	
	public void testWithHqlAssemblerNoAlias() throws Exception {
		JpaqlStringSpecification<MyEntity> spec = new JpaqlStringSpecification<MyEntity>("description");
		spec.setValue("descr1");
		JpaqlAssembler ass = new JpaqlAssembler("from MyEntity", new ArrayList<Order>(), false);
		spec.assembleQuery(ass);
		String query = ass.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity where upper(description) like :description", query);
	}
	
	public void testNotSet() throws Exception {
		JpaqlStringSpecification<MyEntity> spec = new JpaqlStringSpecification<MyEntity>("description");
		JpaqlAssembler ass = new JpaqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(ass);
		String query = ass.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias", query);
	}
	
	public void testMappedProperty() throws Exception {
		JpaqlStringSpecification<MyEntity> spec = new JpaqlStringSpecification<MyEntity>("description(type)");
		spec.setValue("descr1");
		JpaqlAssembler ass = new JpaqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(ass);
		String query = ass.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where upper(entity_alias.description['type']) like :description_type_", query);
	}
	
	public void testNotSatisfiedIfNotSet() throws Exception {
		JpaqlStringSpecification<MyEntity> spec = new JpaqlStringSpecification<MyEntity>("description");
		spec.setSatisfiedIfNotSet(false);
		JpaqlAssembler ass = new JpaqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(ass);
		String query = ass.assembledJpaqlQueryString();
		JpaqlAssert.areSameQuery("from MyEntity entity_alias where 0 = 1", query);
	}
	
}
