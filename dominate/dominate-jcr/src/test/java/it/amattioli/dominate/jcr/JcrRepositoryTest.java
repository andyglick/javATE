package it.amattioli.dominate.jcr;

import it.amattioli.dominate.sessions.SessionMode;
import it.amattioli.dominate.specifications.ComparisonType;
import it.amattioli.dominate.specifications.StringSpecification;

import java.util.ArrayList;
import java.util.List;

import org.apache.jackrabbit.core.TransientRepository;
import org.apache.jackrabbit.ocm.mapper.Mapper;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.AnnotationMapperImpl;
import org.apache.jackrabbit.ocm.query.Filter;
import org.apache.jackrabbit.ocm.query.Query;
import org.apache.jackrabbit.ocm.query.QueryManager;

import junit.framework.TestCase;

public class JcrRepositoryTest extends TestCase {

	@Override
	public void setUp() throws Exception {
		System.setProperty("org.apache.jackrabbit.repository.conf", "src/test/resources/repository.xml");
		System.setProperty("org.apache.jackrabbit.repository.home", "target/jackrabbit");
		JcrSessionManager.setJcrRepository(new TransientRepository());
		List<Class> classes = new ArrayList<Class>();	
		classes.add(MyEntity.class);
		Mapper mapper = new AnnotationMapperImpl(classes);
		JcrSessionManager.setMapper(mapper);
		
		JcrSessionManager mgr = new JcrSessionManager(SessionMode.THREAD_LOCAL);
		QueryManager queryManager = mgr.getSession().getQueryManager();
		Filter filter = queryManager.createFilter(MyEntity.class);
		Query query = queryManager.createQuery(filter);
		mgr.getSession().remove(query);
	}
	
	public void testSaveAndRetrieve() throws Exception {
		JcrSessionManager mgr = new JcrSessionManager(SessionMode.THREAD_LOCAL);
		JcrRepository<String,MyEntity> rep = new JcrRepository<String,MyEntity>(MyEntity.class);
		
		MyEntity e = new MyEntity();
		e.setId("/myTestEntity");
		e.setDescription("My description");
		rep.put(e);
		mgr.getSession().save();
		
		MyEntity er = rep.get(e.getId());
		assertEquals(e.getId(), er.getId());
		assertEquals(e.getDescription(), er.getDescription());
	}
	
	public void testRemove() throws Exception {
		JcrSessionManager mgr = new JcrSessionManager(SessionMode.THREAD_LOCAL);
		JcrRepository<String,MyEntity> rep = new JcrRepository<String,MyEntity>(MyEntity.class);
		
		MyEntity e = new MyEntity();
		e.setId("/myTestEntity");
		e.setDescription("My description");
		rep.put(e);
		mgr.getSession().save();
		
		rep.remove(e);
		mgr.getSession().save();
		
		assertNull(rep.get(e.getId()));
	}
	
	public void testRemoveById() throws Exception {
		JcrSessionManager mgr = new JcrSessionManager(SessionMode.THREAD_LOCAL);
		JcrRepository<String,MyEntity> rep = new JcrRepository<String,MyEntity>(MyEntity.class);
		
		MyEntity e = new MyEntity();
		e.setId("/myTestEntity");
		e.setDescription("My description");
		rep.put(e);
		mgr.getSession().save();
		
		rep.remove(e.getId());
		mgr.getSession().save();
		
		assertNull(rep.get(e.getId()));
	}
	
	public void testList() {
		JcrSessionManager mgr = new JcrSessionManager(SessionMode.THREAD_LOCAL);
		JcrRepository<String,MyEntity> rep = new JcrRepository<String,MyEntity>(MyEntity.class);
		
		MyEntity e1 = new MyEntity();
		e1.setId("/testEntity1");
		e1.setDescription("Hello");
		rep.put(e1);
		MyEntity e2 = new MyEntity();
		e2.setId("/testEntity2");
		e2.setDescription("World");
		rep.put(e2);
		mgr.getSession().save();
		
		List<MyEntity> expected = new ArrayList<MyEntity>();
		expected.add(e1);
		expected.add(e2);
		
		List<MyEntity> result = rep.list();
		
		assertTrue(result.containsAll(expected) &&	expected.containsAll(result));
	}
	
	public void testOrderedList() {
		JcrSessionManager mgr = new JcrSessionManager(SessionMode.THREAD_LOCAL);
		JcrRepository<String,MyEntity> rep = new JcrRepository<String,MyEntity>(MyEntity.class);
		
		MyEntity e1 = new MyEntity();
		e1.setId("/testEntity1");
		e1.setDescription("Hello");
		rep.put(e1);
		MyEntity e2 = new MyEntity();
		e2.setId("/testEntity2");
		e2.setDescription("World");
		rep.put(e2);
		MyEntity e3 = new MyEntity();
		e3.setId("/testEntity3");
		e3.setDescription("Andrea");
		rep.put(e3);
		mgr.getSession().save();
		
		List<MyEntity> expected = new ArrayList<MyEntity>();
		expected.add(e1);
		expected.add(e2);
		expected.add(e3);
		
		rep.setOrder("description", false);
		List<MyEntity> result = rep.list();
		
		assertTrue(result.containsAll(expected) &&	expected.containsAll(result));
		MyEntity prev = null;
		for (MyEntity curr: result) {
			if (prev != null) {
				assertTrue(prev.getDescription().compareTo(curr.getDescription()) <= 0);
			}
			prev = curr;
		}
	}
	
	public void testListBySpecification() {
		JcrSessionManager mgr = new JcrSessionManager(SessionMode.THREAD_LOCAL);
		JcrRepository<String,MyEntity> rep = new JcrRepository<String,MyEntity>(MyEntity.class);
		
		MyEntity e1 = new MyEntity();
		e1.setId("/testEntity1");
		e1.setDescription("Hello");
		rep.put(e1);
		MyEntity e2 = new MyEntity();
		e2.setId("/testEntity2");
		e2.setDescription("World");
		rep.put(e2);
		mgr.getSession().save();
		
		StringSpecification<MyEntity> spec = StringSpecification.newInstance("description");
		spec.setComparisonType(ComparisonType.EXACT);
		spec.setValue("World");
		List<MyEntity> result = rep.list(spec);
		
		for (MyEntity curr: result) {
			assertTrue(curr.getDescription().equals("World"));
		}
	}

	public void testListBySpecificationNoResult() {
		JcrSessionManager mgr = new JcrSessionManager(SessionMode.THREAD_LOCAL);
		JcrRepository<String,MyEntity> rep = new JcrRepository<String,MyEntity>(MyEntity.class);
		
		MyEntity e1 = new MyEntity();
		e1.setId("/testEntity1");
		e1.setDescription("Hello");
		rep.put(e1);
		MyEntity e2 = new MyEntity();
		e2.setId("/testEntity2");
		e2.setDescription("World");
		rep.put(e2);
		mgr.getSession().save();
		
		StringSpecification<MyEntity> spec = StringSpecification.newInstance("description");
		spec.setSatisfiedIfNotSet(false);
		List<MyEntity> result = rep.list(spec);
		
		assertTrue(result.isEmpty());
	}
	/*
	public void testRetrieveSameEntity() throws Exception {
		JcrSessionManager mgr = new JcrSessionManager(SessionMode.THREAD_LOCAL);
		JcrRepository<String,MyEntity> rep = new JcrRepository<String,MyEntity>(MyEntity.class);
		MyEntity e = new MyEntity();
		e.setId("/myTestEntity");
		e.setDescription("My description");
		rep.put(e);
		mgr.getSession().save();
		
		MyEntity e1 = rep.get(e.getId());
		MyEntity e2 = rep.get(e.getId());
		assertSame(e1,e2);
	}
	*/

	@Override
	protected void tearDown() throws Exception {
		((TransientRepository)JcrSessionManager.getJcrRepository()).shutdown();
	}
}
