package it.amattioli.dominate.properties;

import static org.mockito.Mockito.*;

import it.amattioli.dominate.EntityImpl;

import java.util.ArrayList;

import junit.framework.TestCase;

public class BoundListDecoratorTest extends TestCase {
	
	public void testSize() {
		ArrayList<String> coll = new ArrayList<String>() {{
			add("S1");
		}};
		BoundListDecorator<String> boundColl = new BoundListDecorator<String>(coll);
		
		int size = boundColl.size();
		
		assertEquals(1, size);
	}
	
	public void testAdd() {
		CollectionChangeListener listener = mock(CollectionChangeListener.class);
		ArrayList<String> coll = new ArrayList<String>();
		BoundListDecorator<String> boundColl = new BoundListDecorator<String>(coll);
		boundColl.addCollectionChangeListener(listener);
		
		boundColl.add("S1");
		
		assertTrue(coll.contains("S1"));
		verify(listener).collectionChanged(new CollectionChangeEvent(boundColl));
	}
	
	public void testSet() {
		CollectionChangeListener listener = mock(CollectionChangeListener.class);
		ArrayList<String> coll = new ArrayList<String>() {{
			add("S0");
		}};
		BoundListDecorator<String> boundColl = new BoundListDecorator<String>(coll);
		boundColl.addCollectionChangeListener(listener);
		
		boundColl.set(0,"S1");
		
		assertTrue(coll.get(0).equals("S1"));
		verify(listener).collectionChanged(new CollectionChangeEvent(boundColl));
	}
	
	public void testRemove() {
		CollectionChangeListener listener = mock(CollectionChangeListener.class);
		ArrayList<String> coll = new ArrayList<String>() {{
			add("S1");
		}};
		BoundListDecorator<String> boundColl = new BoundListDecorator<String>(coll);
		boundColl.addCollectionChangeListener(listener);
		
		boundColl.remove("S1");
		
		assertFalse(coll.contains("S1"));
		verify(listener).collectionChanged(new CollectionChangeEvent(boundColl));
	}
	
	public void testElementChanged() {
		CollectionChangeListener listener = mock(CollectionChangeListener.class);
		ArrayList<MyEntity> coll = new ArrayList<MyEntity>() {{
			add(new MyEntity());
		}};
		BoundListDecorator<MyEntity> boundColl = new BoundListDecorator<MyEntity>(coll);
		boundColl.addCollectionChangeListener(listener);
		
		MyEntity element = boundColl.get(0);
		element.setDescription("New Description");
		
		verify(listener).collectionChanged(new CollectionChangeEvent(boundColl));
	}
	
	public void testElementChangedAfterAddition() {
		CollectionChangeListener listener = mock(CollectionChangeListener.class);
		ArrayList<MyEntity> coll = new ArrayList<MyEntity>();
		BoundListDecorator<MyEntity> boundColl = new BoundListDecorator<MyEntity>(coll);
		
		MyEntity element = new MyEntity();
		boundColl.add(element);
		boundColl.addCollectionChangeListener(listener);
		element.setDescription("New Description");
		
		verify(listener, times(1)).collectionChanged(new CollectionChangeEvent(boundColl));
	}
	
	public void testElementChangedAfterSet() {
		CollectionChangeListener listener = mock(CollectionChangeListener.class);
		ArrayList<MyEntity> coll = new ArrayList<MyEntity>() {{
			add(new MyEntity());
		}};
		BoundListDecorator<MyEntity> boundColl = new BoundListDecorator<MyEntity>(coll);
		
		MyEntity element = new MyEntity();
		boundColl.set(0,element);
		boundColl.addCollectionChangeListener(listener);
		element.setDescription("New Description");
		
		verify(listener, times(1)).collectionChanged(new CollectionChangeEvent(boundColl));
	}
	
	public void testNoEventFiredIfElementChangedAfterRemoval() {
		CollectionChangeListener listener = mock(CollectionChangeListener.class);
		ArrayList<MyEntity> coll = new ArrayList<MyEntity>() {{
			add(new MyEntity());
		}};
		BoundListDecorator<MyEntity> boundColl = new BoundListDecorator<MyEntity>(coll);
		
		MyEntity element = boundColl.get(0);
		boundColl.remove(element);
		boundColl.addCollectionChangeListener(listener);
		element.setDescription("New Description");
		
		verify(listener, times(0)).collectionChanged(new CollectionChangeEvent(boundColl));
	}
	
	public void testElementChangedAfterGettingTwice() {
		CollectionChangeListener listener = mock(CollectionChangeListener.class);
		ArrayList<MyEntity> coll = new ArrayList<MyEntity>() {{
			add(new MyEntity());
		}};
		BoundListDecorator<MyEntity> boundColl = new BoundListDecorator<MyEntity>(coll);
		boundColl.addCollectionChangeListener(listener);
		
		MyEntity element1 = boundColl.get(0);
		MyEntity element2 = boundColl.get(0);
		element2.setDescription("New Description");
		
		verify(listener).collectionChanged(new CollectionChangeEvent(boundColl));
	}
	
	public void testRemoveListener() {
		CollectionChangeListener listener = mock(CollectionChangeListener.class);
		ArrayList<String> coll = new ArrayList<String>();
		BoundListDecorator<String> boundColl = new BoundListDecorator<String>(coll);
		boundColl.addCollectionChangeListener(listener);
		
		boundColl.removeCollectionChangeListener(listener);
		boundColl.add("S1");
		
		verify(listener, times(0)).collectionChanged(new CollectionChangeEvent(boundColl));
	}
	
	public static class MyEntity extends EntityImpl {
		private String description;

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			String old = getDescription();
			this.description = description;
			firePropertyChange("description", old, description);
		}
		
	}

}
