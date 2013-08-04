package it.amattioli.dominate.hibernate.specifications;

import static org.mockito.Mockito.*;

import junit.framework.TestCase;

public class HqlCustomSpecificationTest extends TestCase {

	public void testQueryAssembling() throws Exception {
		HqlCustomSpecification<MyEntity, String> spec = new HqlCustomSpecification<MyEntity, String>("{alias}.description like :descr");
		spec.setValue("descr1");
		
		HqlAssembler ass = mock(HqlAssembler.class);
		spec.assembleQuery(ass);
		verify(ass).append("description like :descr");
	}
	
	public void testQueryAssemblingWithAlias() throws Exception {
		HqlCustomSpecification<MyEntity, String> spec = new HqlCustomSpecification<MyEntity, String>("{alias}.description like :descr");
		spec.setValue("descr1");
		
		HqlAssembler ass = mock(HqlAssembler.class);
		when(ass.getAlias()).thenReturn("entity_alias");
		spec.assembleQuery(ass);
		verify(ass).append("entity_alias.description like :descr");
	}
	
	public void testQueryAssemblingNotSet() throws Exception {
		HqlCustomSpecification<MyEntity, String> spec = new HqlCustomSpecification<MyEntity, String>("{alias}.description like :descr");
		
		HqlAssembler ass = mock(HqlAssembler.class);
		spec.assembleQuery(ass);
		verify(ass,never()).append(anyString());
	}
	
	public void testQueryParameter() throws Exception {
		HqlCustomSpecification<MyEntity, String> spec = new HqlCustomSpecification<MyEntity, String>("{alias}.description like :descr");
		String paramValue = "descr1";
		spec.setValue(paramValue);
		
		HqlAssembler ass = mock(HqlAssembler.class);
		spec.assembleQuery(ass);
		verify(ass).addParameter("descr", paramValue);
	}
	
	public void testIsSatisfiedWhenNotSet() {
		HqlCustomSpecification<MyEntity, String> spec = new HqlCustomSpecification<MyEntity, String>("{alias}.description like :descr");
		assertTrue(spec.isSatisfiedBy(new MyEntity()));
	}
}
