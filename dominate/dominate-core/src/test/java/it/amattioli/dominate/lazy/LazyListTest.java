package it.amattioli.dominate.lazy;

import it.amattioli.dominate.FakeEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

public class LazyListTest extends TestCase {

	private List<FakeEntity> create(final List<FakeEntity> target) {
		return new LazyList<FakeEntity>() {

			@Override
			protected List<FakeEntity> findTarget() {
				return target;
			}
			
		};
	}
	
	public void testSize() {
		List<FakeEntity> l = create(Collections.nCopies(5, new FakeEntity()));
		assertEquals(5, l.size());
	}
	
	public void testGet() {
		FakeEntity e = new FakeEntity();
		List<FakeEntity> l = create(Arrays.asList(new FakeEntity[] {e}));
		assertEquals(e, l.get(0));
	}
	
	public void testAdd() {
		List<FakeEntity> l = create(new ArrayList<FakeEntity>());
		FakeEntity e = new FakeEntity();
		l.add(e);
		assertTrue(l.contains(e));
	}
	
}
