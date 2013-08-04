package it.amattioli.dominate.groups;

import it.amattioli.dominate.Repository;
import it.amattioli.dominate.memory.MemoryRepository;
import it.amattioli.dominate.specifications.EnumSpecification;
import it.amattioli.dominate.specifications.MyEntity;
import it.amattioli.dominate.specifications.MyEnum;
import junit.framework.TestCase;

public class GroupTest extends TestCase {

	public void testContains() {
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setEnumProperty(MyEnum.VALUE1);
		
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setEnumProperty(MyEnum.VALUE2);
		
		Repository<Long,MyEntity> rep = new MemoryRepository<Long, MyEntity>();
		rep.put(e1);
		rep.put(e2);
		
		EnumSpecification<MyEntity, MyEnum> spec = EnumSpecification.newInstance("enumProperty", MyEnum.class);
		spec.setValue(MyEnum.VALUE1);
		
		EntityGroup<Long, MyEntity> g = new EntityGroup<Long,MyEntity>(rep, spec, "");
		
		assertTrue(g.contains(e1));
		assertFalse(g.contains(e2));	
	}
	
	public void testAdd() {
		EnumSpecification<MyEntity, MyEnum> spec = EnumSpecification.newInstance("enumProperty", MyEnum.class);
		spec.setValue(MyEnum.VALUE1);
		
		EntityGroup<Long, MyEntity> g = new EntityGroup<Long,MyEntity>(spec, "");
		
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setEnumProperty(MyEnum.VALUE1);
		g.add(e1);
		
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setEnumProperty(MyEnum.VALUE2);
		g.add(e2);
		
		assertTrue(g.contains(e1));
		assertFalse(g.contains(e2));
	}
	
}
