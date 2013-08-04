package it.amattioli.dominate.groups;

import java.util.ArrayList;
import java.util.List;

import it.amattioli.dominate.specifications.MyEntity;
import it.amattioli.dominate.specifications.MyEnum;
import junit.framework.TestCase;

public class GroupFactoryTest extends TestCase {

	public void testOneEntity() {
		ArrayList<MyEntity> l = new ArrayList<MyEntity>();
		MyEntity e = new MyEntity();
		e.setEnumProperty(MyEnum.VALUE1);
		l.add(e);
		GroupingFactory<Long,MyEntity> f = new GroupingFactory<Long,MyEntity>("enumProperty", l);
		List<EntityGroup<Long, MyEntity>> g = f.createGroups();
		assertEquals(1, g.size());
		assertTrue(g.get(0).contains(e));
	}
	
	public void testTwoEntitiesInTheSameGroup() {
		ArrayList<MyEntity> l = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setEnumProperty(MyEnum.VALUE1);
		l.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setEnumProperty(MyEnum.VALUE1);
		l.add(e2);
		GroupingFactory<Long,MyEntity> f = new GroupingFactory<Long,MyEntity>("enumProperty",l);
		List<EntityGroup<Long, MyEntity>> g = f.createGroups();
		assertEquals(1, g.size());
		assertTrue(g.get(0).contains(e1));
		assertTrue(g.get(0).contains(e2));
	}
	
	public void testTwoEntitiesInDifferentGroups() {
		ArrayList<MyEntity> l = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setEnumProperty(MyEnum.VALUE1);
		l.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setEnumProperty(MyEnum.VALUE2);
		l.add(e2);
		GroupingFactory<Long,MyEntity> f = new GroupingFactory<Long,MyEntity>("enumProperty",l);
		List<EntityGroup<Long, MyEntity>> g = f.createGroups();
		assertEquals(2, g.size());
		assertTrue(g.get(0).contains(e1));
		assertFalse(g.get(0).contains(e2));
		assertTrue(g.get(1).contains(e2));
		assertFalse(g.get(1).contains(e1));
	}
	
}
