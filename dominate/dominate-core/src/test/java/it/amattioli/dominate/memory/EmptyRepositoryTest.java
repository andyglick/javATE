package it.amattioli.dominate.memory;

import it.amattioli.dominate.EntityImpl;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.Specification;
import it.amattioli.dominate.specifications.dflt.BeanShellSpecification;
import junit.framework.TestCase;

public class EmptyRepositoryTest extends TestCase {

	public void testGet() {
		Repository<Long, EntityImpl> rep = new EmptyRepository<Long, EntityImpl>();
		assertNull(rep.get(1L));
	}
	
	public void testPut() {
		Repository<Long, EntityImpl> rep = new EmptyRepository<Long, EntityImpl>();
		try {
			rep.put(new EntityImpl());
			fail();
		} catch(UnsupportedOperationException e) {
			
		}
	}
	
	public void testList() {
		Repository<Long, EntityImpl> rep = new EmptyRepository<Long, EntityImpl>();
		assertTrue(rep.list().isEmpty());
	}

	public void testListWithSpecification() {
		Repository<Long, EntityImpl> rep = new EmptyRepository<Long, EntityImpl>();
		Specification<EntityImpl> spec = new BeanShellSpecification<EntityImpl>("true");
		assertTrue(rep.list(spec).isEmpty());
	}
	
	public void testRemoveAllowed() {
		Repository<Long, EntityImpl> rep = new EmptyRepository<Long, EntityImpl>();
		assertFalse(rep.isRemoveAllowed());
	}
	
	public void testRemoveById() {
		Repository<Long, EntityImpl> rep = new EmptyRepository<Long, EntityImpl>();
		try {
			rep.remove(1L);
			fail();
		} catch(UnsupportedOperationException e) {
			
		}
	}
	
	public void testGetByPropertyValue() {
		Repository<Long, EntityImpl> rep = new EmptyRepository<Long, EntityImpl>();
		assertNull(rep.getByPropertyValue("id", 1L));
	}
}
