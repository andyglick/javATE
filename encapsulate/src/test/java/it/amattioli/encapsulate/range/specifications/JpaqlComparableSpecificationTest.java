package it.amattioli.encapsulate.range.specifications;

import java.util.Collections;

import it.amattioli.dominate.jpa.specifications.JpaqlAssembler;
import it.amattioli.encapsulate.range.GenericContinousRange;
import junit.framework.TestCase;

public class JpaqlComparableSpecificationTest extends TestCase {

	public void testBoundedRange() throws Exception {
		JpaqlComparableSpecification<MyEntity,Long> spec = new JpaqlComparableSpecification<MyEntity,Long>("longAttribute");
		spec.setValue(new GenericContinousRange<Long>(0L, 2L));
		JpaqlAssembler asm = new JpaqlAssembler("from MyEntity", Collections.EMPTY_LIST, true);
		spec.assembleQuery(asm);
		String query = asm.assembledJpaqlQueryString();
		assertEquals("from MyEntity entity_alias  where entity_alias.longAttribute >= :longAttributeLow and entity_alias.longAttribute < :longAttributeHigh ", query);
	}
	
	public void testBoundedRangeNoAlias() throws Exception {
		JpaqlComparableSpecification<MyEntity,Long> spec = new JpaqlComparableSpecification<MyEntity,Long>("longAttribute");
		spec.setValue(new GenericContinousRange<Long>(0L, 2L));
		JpaqlAssembler asm = new JpaqlAssembler("from MyEntity", Collections.EMPTY_LIST, false);
		spec.assembleQuery(asm);
		String query = asm.assembledJpaqlQueryString();
		assertEquals("from MyEntity where longAttribute >= :longAttributeLow and longAttribute < :longAttributeHigh ", query);
	}
	
	public void testLowBoundedRange() throws Exception {
		JpaqlComparableSpecification<MyEntity,Long> spec = new JpaqlComparableSpecification<MyEntity,Long>("longAttribute");
		spec.setValue(new GenericContinousRange<Long>(0L, null));
		JpaqlAssembler asm = new JpaqlAssembler("from MyEntity", Collections.EMPTY_LIST, true);
		spec.assembleQuery(asm);
		String query = asm.assembledJpaqlQueryString();
		assertEquals("from MyEntity entity_alias  where entity_alias.longAttribute >= :longAttributeLow ", query);
	}
	
	public void testHighBoundedRange() throws Exception {
		JpaqlComparableSpecification<MyEntity,Long> spec = new JpaqlComparableSpecification<MyEntity,Long>("longAttribute");
		spec.setValue(new GenericContinousRange<Long>(null, 2L));
		JpaqlAssembler asm = new JpaqlAssembler("from MyEntity", Collections.EMPTY_LIST, true);
		spec.assembleQuery(asm);
		String query = asm.assembledJpaqlQueryString();
		assertEquals("from MyEntity entity_alias  where entity_alias.longAttribute < :longAttributeHigh ", query);
	}

}
