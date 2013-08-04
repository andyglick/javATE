package it.amattioli.encapsulate.range.specifications;

import java.util.Collections;

import it.amattioli.dominate.hibernate.specifications.HqlAssembler;
import it.amattioli.encapsulate.range.GenericContinousRange;
import junit.framework.TestCase;

public class HqlComparableSpecificationTest extends TestCase {

	public void testBoundedRange() throws Exception {
		HqlComparableSpecification<MyEntity,Long> spec = new HqlComparableSpecification<MyEntity,Long>("longAttribute");
		spec.setValue(new GenericContinousRange<Long>(0L, 2L));
		HqlAssembler asm = new HqlAssembler("from MyEntity", Collections.EMPTY_LIST, true);
		spec.assembleQuery(asm);
		String query = asm.assembledHqlQueryString();
		assertEquals("from MyEntity entity_alias  where entity_alias.longAttribute >= :longAttributeLow and entity_alias.longAttribute < :longAttributeHigh ", query);
	}
	
	public void testBoundedRangeNoAlias() throws Exception {
		HqlComparableSpecification<MyEntity,Long> spec = new HqlComparableSpecification<MyEntity,Long>("longAttribute");
		spec.setValue(new GenericContinousRange<Long>(0L, 2L));
		HqlAssembler asm = new HqlAssembler("from MyEntity", Collections.EMPTY_LIST, false);
		spec.assembleQuery(asm);
		String query = asm.assembledHqlQueryString();
		assertEquals("from MyEntity where longAttribute >= :longAttributeLow and longAttribute < :longAttributeHigh ", query);
	}
	
	public void testLowBoundedRange() throws Exception {
		HqlComparableSpecification<MyEntity,Long> spec = new HqlComparableSpecification<MyEntity,Long>("longAttribute");
		spec.setValue(new GenericContinousRange<Long>(0L, null));
		HqlAssembler asm = new HqlAssembler("from MyEntity", Collections.EMPTY_LIST, true);
		spec.assembleQuery(asm);
		String query = asm.assembledHqlQueryString();
		assertEquals("from MyEntity entity_alias  where entity_alias.longAttribute >= :longAttributeLow ", query);
	}
	
	public void testHighBoundedRange() throws Exception {
		HqlComparableSpecification<MyEntity,Long> spec = new HqlComparableSpecification<MyEntity,Long>("longAttribute");
		spec.setValue(new GenericContinousRange<Long>(null, 2L));
		HqlAssembler asm = new HqlAssembler("from MyEntity", Collections.EMPTY_LIST, true);
		spec.assembleQuery(asm);
		String query = asm.assembledHqlQueryString();
		assertEquals("from MyEntity entity_alias  where entity_alias.longAttribute < :longAttributeHigh ", query);
	}

}
