package it.amattioli.dominate.morphia;

import com.google.code.morphia.Morphia;

import junit.framework.TestCase;

public class MorphiaEntityResolverTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		MorphiaSessionManager.setMorphia(new Morphia());
	}

	public void testForMappedClass() {
		MorphiaSessionManager.getMorphia().map(MyEntity.class);
		MorphiaEntityResolver resolver = new MorphiaEntityResolver();
		assertEquals(MyEntity.class,resolver.find("MyEntity"));
	}
	
	public void testForNotMappedClass() {
		MorphiaSessionManager.getMorphia().map(MyEntity.class);
		MorphiaEntityResolver resolver = new MorphiaEntityResolver();
		assertNull(resolver.find("YourEntity"));
	}
	
}
