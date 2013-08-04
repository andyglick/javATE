package it.amattioli.dominate.jcr;

import it.amattioli.dominate.sessions.SessionMode;

import java.util.ArrayList;
import java.util.List;

import org.apache.jackrabbit.core.TransientRepository;
import org.apache.jackrabbit.ocm.manager.ObjectContentManager;
import org.apache.jackrabbit.ocm.mapper.Mapper;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.AnnotationMapperImpl;
import org.apache.jackrabbit.ocm.query.Filter;
import org.apache.jackrabbit.ocm.query.QueryManager;

import junit.framework.TestCase;

public class JcrAssemblerTest extends TestCase {
	
	@Override
	public void setUp() throws Exception {
		System.setProperty("org.apache.jackrabbit.repository.conf", "src/test/resources/repository.xml");
		System.setProperty("org.apache.jackrabbit.repository.home", "target/jackrabbit");
		JcrSessionManager.setJcrRepository(new TransientRepository());
		List<Class> classes = new ArrayList<Class>();	
		classes.add(MyEntity.class);
		Mapper mapper = new AnnotationMapperImpl(classes);
		JcrSessionManager.setMapper(mapper);
	}
	
	public QueryManager createQueryManager() throws Exception {
		JcrSessionManager sessionMgr = new JcrSessionManager(SessionMode.THREAD_LOCAL);
		ObjectContentManager ocm = sessionMgr.getSession();
		return ocm.getQueryManager();
	}

	public void testEmptyQuery() throws Exception {
		QueryManager queryManager = createQueryManager();
		JcrAssembler asm = new JcrAssembler(queryManager, MyEntity.class);
		String jcrExpression = queryManager.buildJCRExpression(asm.getAssembledQuery());
		assertEquals("//element(*, nt:unstructured) [@ocm_classname='it.amattioli.dominate.jcr.MyEntity']", jcrExpression);
	}
	
	public void testSimpleQuery() throws Exception {
		QueryManager queryManager = createQueryManager();
		JcrAssembler asm = new JcrAssembler(queryManager, MyEntity.class);
		Filter filter = asm.createFilter();
		filter.addEqualTo("description", "my descr");
		asm.addFilter(filter);
		String jcrExpression = queryManager.buildJCRExpression(asm.getAssembledQuery());
		assertEquals("//element(*, nt:unstructured) [(@description = 'my descr') and  ( @ocm_classname='it.amattioli.dominate.jcr.MyEntity')]", jcrExpression);
	}
	
	public void testConjunction() throws Exception {
		QueryManager queryManager = createQueryManager();
		JcrAssembler asm = new JcrAssembler(queryManager, MyEntity.class);
		asm.startConjunction();
		Filter filter1 = asm.createFilter();
		filter1.addGreaterOrEqualThan("description", "b");
		asm.addFilter(filter1);
		Filter filter2 = asm.createFilter();
		filter2.addLessOrEqualThan("description", "e");
		asm.addFilter(filter2);
		asm.endConjunction();
		String jcrExpression = queryManager.buildJCRExpression(asm.getAssembledQuery());
		assertEquals("//element(*, nt:unstructured) [((@description >= 'b') and  ( @description <= 'e')) and  ( @ocm_classname='it.amattioli.dominate.jcr.MyEntity')]", jcrExpression);
	}
	
	public void testDisjunction() throws Exception {
		QueryManager queryManager = createQueryManager();
		JcrAssembler asm = new JcrAssembler(queryManager, MyEntity.class);
		asm.startDisjunction();
		Filter filter1 = asm.createFilter();
		filter1.addGreaterOrEqualThan("description", "b");
		asm.addFilter(filter1);
		Filter filter2 = asm.createFilter();
		filter2.addLessOrEqualThan("description", "e");
		asm.addFilter(filter2);
		asm.endDisjunction();
		String jcrExpression = queryManager.buildJCRExpression(asm.getAssembledQuery());
		assertEquals("//element(*, nt:unstructured) [((@description >= 'b')  or ( @description <= 'e')) and  ( @ocm_classname='it.amattioli.dominate.jcr.MyEntity')]", jcrExpression);
	}
	
	@Override
	protected void tearDown() throws Exception {
		((TransientRepository)JcrSessionManager.getJcrRepository()).shutdown();
	}
}
