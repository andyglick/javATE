package it.amattioli.dominate.hibernate.specifications;

import java.util.ArrayList;

import org.apache.commons.collections.functors.TruePredicate;

import junit.framework.TestCase;
import it.amattioli.dominate.repositories.Order;
import it.amattioli.dominate.specifications.dflt.PredicateAssembler;

public class HqlStringSpecificationTest extends TestCase {

	public void testWithPredicateAssembler() {
		HqlStringSpecification<MyEntity> spec = new HqlStringSpecification<MyEntity>("");
		PredicateAssembler ass = new PredicateAssembler();
		spec.assembleQuery(ass);
		assertEquals(TruePredicate.class, ass.assembledPredicate().getClass());
	}
	
	public void testWithHqlAssembler() throws Exception {
		HqlStringSpecification<MyEntity> spec = new HqlStringSpecification<MyEntity>("description");
		spec.setValue("descr1");
		HqlAssembler ass = new HqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(ass);
		String query = ass.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where upper(entity_alias.description) like :description", query);
	}
	
	public void testWithHqlAssemblerNoAlias() throws Exception {
		HqlStringSpecification<MyEntity> spec = new HqlStringSpecification<MyEntity>("description");
		spec.setValue("descr1");
		HqlAssembler ass = new HqlAssembler("from MyEntity", new ArrayList<Order>(), false);
		spec.assembleQuery(ass);
		String query = ass.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity where upper(description) like :description", query);
	}
	
	public void testNotSet() throws Exception {
		HqlStringSpecification<MyEntity> spec = new HqlStringSpecification<MyEntity>("description");
		HqlAssembler ass = new HqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(ass);
		String query = ass.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias", query);
	}
	
	public void testMappedProperty() throws Exception {
		HqlStringSpecification<MyEntity> spec = new HqlStringSpecification<MyEntity>("description(type)");
		spec.setValue("descr1");
		HqlAssembler ass = new HqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(ass);
		String query = ass.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where upper(entity_alias.description['type']) like :description_type_", query);
	}
	
	public void testNotSatisfiedIfNotSet() throws Exception {
		HqlStringSpecification<MyEntity> spec = new HqlStringSpecification<MyEntity>("description");
		spec.setSatisfiedIfNotSet(false);
		HqlAssembler ass = new HqlAssembler("from MyEntity", new ArrayList<Order>());
		spec.assembleQuery(ass);
		String query = ass.assembledHqlQueryString();
		HqlAssert.areSameQuery("from MyEntity entity_alias where 0 = 1", query);
	}
	
}
