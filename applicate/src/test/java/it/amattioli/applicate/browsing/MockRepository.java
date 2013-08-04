package it.amattioli.applicate.browsing;

import java.util.List;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.EntityImpl;
import it.amattioli.dominate.Specification;
import it.amattioli.dominate.memory.MemoryRepository;

public class MockRepository extends MemoryRepository<Long, Entity<Long>> {
	private int listCalls = 0;
	private int listSpecificationCalls = 0;
	
	public MockRepository(int num) {
		for (int i = 0; i < num; i++) {
            Entity<Long> e = new EntityImpl();
            e.setId(new Long(i));
            put(e);
        }
	}

	@Override
	public List<Entity<Long>> list() {
		listCalls++;
		return super.list();
	}

	@Override
	public List<Entity<Long>> list(Specification<Entity<Long>> spec) {
		listSpecificationCalls++;
		return super.list(spec);
	}

	public int getListCalls() {
		return listCalls;
	}

	public int getListSpecificationCalls() {
		return listSpecificationCalls;
	}

}
