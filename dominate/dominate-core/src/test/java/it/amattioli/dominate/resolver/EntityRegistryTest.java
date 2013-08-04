package it.amattioli.dominate.resolver;

import java.util.HashMap;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.FakeEntity;
import junit.framework.TestCase;

public class EntityRegistryTest extends TestCase {

	public void testRegisteredEntity() {
		EntityRegistry reg = new EntityRegistry();
		reg.register("fake", FakeEntity.class);
		assertEquals(FakeEntity.class, reg.find("fake"));
	}
	
	public void testConstructorInjectedEntity() {
		HashMap<String, Class<? extends Entity<?>>> entities = new HashMap<String, Class<? extends Entity<?>>>();
		entities.put("fake", FakeEntity.class);
		EntityRegistry reg = new EntityRegistry(entities);
		assertEquals(FakeEntity.class, reg.find("fake"));
	}
	
	public void testUnknownEntity() {
		EntityRegistry reg = new EntityRegistry();
		assertNull(reg.find("fake"));
	}
}
