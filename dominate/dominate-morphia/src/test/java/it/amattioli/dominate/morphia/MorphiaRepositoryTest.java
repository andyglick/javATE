package it.amattioli.dominate.morphia;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import it.amattioli.dominate.sessions.SessionMode;
import it.amattioli.dominate.specifications.StringSpecification;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;

import junit.framework.TestCase;

public class MorphiaRepositoryTest extends TestCase {
	
	@Override
	public void setUp() {
		MorphiaSessionManager.setDatabase("test_database");
		Morphia morphia = MorphiaSessionManager.getMorphia();
		morphia.map(MyEntity.class);
		Datastore ds = (new MorphiaSessionManager(SessionMode.THREAD_LOCAL)).getSession(Datastore.class);
		ds.delete(ds.createQuery(MyEntity.class));
	}
	
	public void testSaveAndRetrieve() throws Exception {
		MorphiaRepository<ObjectId, MyEntity> rep = new MorphiaRepository<ObjectId, MyEntity>(MyEntity.class);
		
		MyEntity p = new MyEntity();
		p.setStringProperty("Hello World");
		rep.put(p);
		
		MyEntity pr = rep.get(p.getId());
		assertEquals(p,pr);
	}
	
	public void testRemove() throws Exception {
		MorphiaRepository<ObjectId, MyEntity> rep = new MorphiaRepository<ObjectId, MyEntity>(MyEntity.class);
		
		MyEntity p = new MyEntity();
		p.setStringProperty("Hello World");
		rep.put(p);
		
		rep.remove(p);
		
		assertNull(rep.get(p.getId()));
	}
	
	public void testRemoveById() throws Exception {
		MorphiaRepository<ObjectId, MyEntity> rep = new MorphiaRepository<ObjectId, MyEntity>(MyEntity.class);
		
		MyEntity p = new MyEntity();
		p.setStringProperty("Hello World");
		rep.put(p);
		
		rep.remove(p.getId());
		
		assertNull(rep.get(p.getId()));
	}
	
	public void testList() throws Exception {
		
		MorphiaRepository<ObjectId, MyEntity> rep = new MorphiaRepository<ObjectId, MyEntity>(MyEntity.class);
		
		MyEntity p1 = new MyEntity();
		p1.setStringProperty("Hello");
		rep.put(p1);
		MyEntity p2 = new MyEntity();
		p2.setStringProperty("World");
		rep.put(p2);
		List<MyEntity> expected = new ArrayList<MyEntity>();
		expected.add(p1);
		expected.add(p2);
		
		List<MyEntity> result = rep.list();
		
		assertTrue(result.containsAll(expected) &&	expected.containsAll(result));
		
	}
	
	public void testOrderedList() throws Exception {
		MorphiaRepository<ObjectId, MyEntity> rep = new MorphiaRepository<ObjectId, MyEntity>(MyEntity.class);
		
		MyEntity p1 = new MyEntity();
		p1.setStringProperty("Hello");
		rep.put(p1);
		MyEntity p2 = new MyEntity();
		p2.setStringProperty("World");
		rep.put(p2);
		MyEntity p3 = new MyEntity();
		p3.setStringProperty("Andrea");
		rep.put(p3);
		List<MyEntity> expected = new ArrayList<MyEntity>();
		expected.add(p1);
		expected.add(p2);
		expected.add(p3);
		
		rep.setOrder("stringProperty", false);
		List<MyEntity> result = rep.list();
		
		assertTrue(result.containsAll(expected) &&	expected.containsAll(result));
		MyEntity prev = null;
		for (MyEntity curr: result) {
			if (prev != null) {
				assertTrue(prev.getStringProperty().compareTo(curr.getStringProperty()) <= 0);
			}
			prev = curr;
		}
	}
	
	public void testLimitedList() {
		MorphiaRepository<ObjectId, MyEntity> rep = new MorphiaRepository<ObjectId, MyEntity>(MyEntity.class);
		
		MyEntity p1 = new MyEntity();
		p1.setStringProperty("Hello");
		rep.put(p1);
		MyEntity p2 = new MyEntity();
		p2.setStringProperty("World");
		rep.put(p2);
		MyEntity p3 = new MyEntity();
		p3.setStringProperty("Andrea");
		rep.put(p3);
		MyEntity p4 = new MyEntity();
		p4.setStringProperty("Mattioli");
		rep.put(p4);
		List<MyEntity> expected = new ArrayList<MyEntity>();
		expected.add(p2);
		expected.add(p3);
		
		rep.setFirst(1);
		rep.setLast(2);
		List<MyEntity> result = rep.list();
		
		assertTrue(result.containsAll(expected) &&	expected.containsAll(result));
	}
	
	public void testListBySpecification() throws Exception {
		MorphiaRepository<ObjectId, MyEntity> rep = new MorphiaRepository<ObjectId, MyEntity>(MyEntity.class);
		
		MyEntity p1 = new MyEntity();
		p1.setStringProperty("Hello");
		rep.put(p1);
		MyEntity p2 = new MyEntity();
		p2.setStringProperty("World");
		rep.put(p2);
		
		StringSpecification<MyEntity> spec = new MorphiaStringSpecification<MyEntity>("stringProperty");
		spec.setValue("Hello");
		List<MyEntity> result = rep.list(spec);
		
		for (MyEntity curr: result) {
			curr.getStringProperty().equals("Hello");
		}
	}
	
	public void testOrderedListBySpecification() throws Exception {
		MorphiaRepository<ObjectId, MyEntity> rep = new MorphiaRepository<ObjectId, MyEntity>(MyEntity.class);
		
		MyEntity p1 = new MyEntity();
		p1.setStringProperty("Hello");
		p1.setLongProperty(10L);
		rep.put(p1);
		MyEntity p2 = new MyEntity();
		p2.setStringProperty("World");
		p2.setLongProperty(5L);
		rep.put(p2);
		MyEntity p3 = new MyEntity();
		p3.setStringProperty("Hello");
		p3.setLongProperty(1L);
		rep.put(p3);
		
		StringSpecification<MyEntity> spec = new MorphiaStringSpecification<MyEntity>("stringProperty");
		spec.setValue("Hello");
		rep.setOrder("longProperty", false);
		List<MyEntity> result = rep.list(spec);
		
		for (MyEntity curr: result) {
			curr.getStringProperty().equals("Hello");
		}
		MyEntity prev = null;
		for (MyEntity curr: result) {
			if (prev != null) {
				assertTrue(prev.getLongProperty().compareTo(curr.getLongProperty()) <= 0);
			}
			prev = curr;
		}
	}
	
	public void testLimitedListBySpecification() throws Exception {
		MorphiaRepository<ObjectId, MyEntity> rep = new MorphiaRepository<ObjectId, MyEntity>(MyEntity.class);
		
		MyEntity p1 = new MyEntity();
		p1.setStringProperty("Hello");
		p1.setLongProperty(10L);
		rep.put(p1);
		MyEntity p2 = new MyEntity();
		p2.setStringProperty("World");
		p2.setLongProperty(5L);
		rep.put(p2);
		MyEntity p3 = new MyEntity();
		p3.setStringProperty("Hello");
		p3.setLongProperty(1L);
		rep.put(p3);
		MyEntity p4 = new MyEntity();
		p4.setStringProperty("World");
		p4.setLongProperty(7L);
		rep.put(p4);
		MyEntity p5 = new MyEntity();
		p5.setStringProperty("Hello");
		p5.setLongProperty(11L);
		rep.put(p5);
		MyEntity p6 = new MyEntity();
		p6.setStringProperty("World");
		p6.setLongProperty(13L);
		rep.put(p6);
		List<MyEntity> expected = new ArrayList<MyEntity>();
		expected.add(p3);
		expected.add(p5);
		
		StringSpecification<MyEntity> spec = new MorphiaStringSpecification<MyEntity>("stringProperty");
		spec.setValue("Hello");
		rep.setFirst(1);
		rep.setLast(2);
		List<MyEntity> result = rep.list(spec);
		
		assertTrue(result.containsAll(expected) &&	expected.containsAll(result));
	}
	
	public void testListBySpecificationNoResults() throws Exception {
		
		MorphiaRepository<ObjectId, MyEntity> rep = new MorphiaRepository<ObjectId, MyEntity>(MyEntity.class);
		
		MyEntity p1 = new MyEntity();
		p1.setStringProperty("Hello");
		rep.put(p1);
		MyEntity p2 = new MyEntity();
		p2.setStringProperty("World");
		rep.put(p2);
		
		StringSpecification<MyEntity> spec = new MorphiaStringSpecification<MyEntity>("stringProperty");
		spec.setSatisfiedIfNotSet(false);
		List<MyEntity> result = rep.list(spec);
		
		assertTrue(result.isEmpty());
		
	}
	
	public void testGetByPropertyValue() throws Exception {
		MorphiaRepository<ObjectId, MyEntity> rep = new MorphiaRepository<ObjectId, MyEntity>(MyEntity.class);
		
		MyEntity p1 = new MyEntity();
		p1.setStringProperty("Hello");
		rep.put(p1);
		MyEntity p2 = new MyEntity();
		p2.setStringProperty("World");
		rep.put(p2);
		
		MyEntity result = rep.getByPropertyValue("stringProperty", "Hello");
		
		assertEquals(p1, result);
	}

}
