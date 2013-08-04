package it.amattioli.dominate.jpa.specifications;

import static org.mockito.Mockito.*;

import junit.framework.TestCase;

public class JpaqlCustomSpecificationTest extends TestCase {

	public void testQueryAssembling() throws Exception {
		JpaqlCustomSpecification<MyEntity, String> spec = new JpaqlCustomSpecification<MyEntity, String>("{alias}.description like :descr");
		spec.setValue("descr1");
		
		JpaqlAssembler ass = mock(JpaqlAssembler.class);
		spec.assembleQuery(ass);
		verify(ass).append("description like :descr");
	}
	
	public void testQueryAssemblingWithAlias() throws Exception {
		JpaqlCustomSpecification<MyEntity, String> spec = new JpaqlCustomSpecification<MyEntity, String>("{alias}.description like :descr");
		spec.setValue("descr1");
		
		JpaqlAssembler ass = mock(JpaqlAssembler.class);
		when(ass.getAlias()).thenReturn("entity_alias");
		spec.assembleQuery(ass);
		verify(ass).append("entity_alias.description like :descr");
	}
	
	public void testQueryAssemblingNotSet() throws Exception {
		JpaqlCustomSpecification<MyEntity, String> spec = new JpaqlCustomSpecification<MyEntity, String>("{alias}.description like :descr");
		
		JpaqlAssembler ass = mock(JpaqlAssembler.class);
		spec.assembleQuery(ass);
		verify(ass,never()).append(anyString());
	}
	
	public void testQueryParameter() throws Exception {
		JpaqlCustomSpecification<MyEntity, String> spec = new JpaqlCustomSpecification<MyEntity, String>("{alias}.description like :descr");
		String paramValue = "descr1";
		spec.setValue(paramValue);
		
		JpaqlAssembler ass = mock(JpaqlAssembler.class);
		spec.assembleQuery(ass);
		verify(ass).addParameter("descr", paramValue);
	}
	
	public void testIsSatisfiedWhenNotSet() {
		JpaqlCustomSpecification<MyEntity, String> spec = new JpaqlCustomSpecification<MyEntity, String>("{alias}.description like :descr");
		assertTrue(spec.isSatisfiedBy(new MyEntity()));
	}
}
