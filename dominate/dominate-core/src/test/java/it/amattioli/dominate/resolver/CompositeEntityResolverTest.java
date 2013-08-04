package it.amattioli.dominate.resolver;

import java.util.ArrayList;
import java.util.List;

import it.amattioli.dominate.FakeEntity;
import it.amattioli.dominate.specifications.MyEntity;
import junit.framework.TestCase;

public class CompositeEntityResolverTest extends TestCase {

	public void testNoAddedChildren() {
		CompositeEntityResolver resolver = new CompositeEntityResolver();
		assertNull(resolver.find("noEntity"));
	}
	
	public void testOneAddedChildren() {
		CompositeEntityResolver resolver = new CompositeEntityResolver();
		EntityRegistry reg1 = new EntityRegistry();
		reg1.register("fake", FakeEntity.class);
		resolver.addChildResolver(reg1);
		assertEquals(FakeEntity.class, resolver.find("fake"));
		assertNull(resolver.find("noEntity"));
	}
	
	public void testTwoAddedChildren() {
		CompositeEntityResolver resolver = new CompositeEntityResolver();
		EntityRegistry reg1 = new EntityRegistry();
		reg1.register("fake", FakeEntity.class);
		resolver.addChildResolver(reg1);
		EntityRegistry reg2 = new EntityRegistry();
		reg2.register("my", MyEntity.class);
		resolver.addChildResolver(reg2);
		assertEquals(FakeEntity.class, resolver.find("fake"));
		assertEquals(MyEntity.class, resolver.find("my"));
		assertNull(resolver.find("noEntity"));
	}
	
	public void testConstructorInjectedChildren() {
		List<EntityResolver> children = new ArrayList<EntityResolver>();
		EntityRegistry reg1 = new EntityRegistry();
		reg1.register("fake", FakeEntity.class);
		children.add(reg1);
		EntityRegistry reg2 = new EntityRegistry();
		reg2.register("my", MyEntity.class);
		children.add(reg2);
		CompositeEntityResolver resolver = new CompositeEntityResolver(children);
		assertEquals(FakeEntity.class, resolver.find("fake"));
		assertEquals(MyEntity.class, resolver.find("my"));
		assertNull(resolver.find("noEntity"));
	}
	
}
