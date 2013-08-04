package it.amattioli.dominate.jcr;

import it.amattioli.dominate.EntityImpl;
import it.amattioli.dominate.Repository;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.jackrabbit.core.TransientRepository;
import org.apache.jackrabbit.ocm.mapper.Mapper;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.AnnotationMapperImpl;

public class JcrRepositoryFactoryTest extends TestCase {

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
	
	public void testWithMappedEntity() {
		JcrRepositoryFactory factory = new JcrRepositoryFactory();
		Repository<String, MyEntity> repository = factory.getRepository(MyEntity.class);
		assertNotNull(repository);
		assertEquals(JcrRepository.class, repository.getClass());
	}
	
	public void testWithNotMappedEntity() {
		JcrRepositoryFactory factory = new JcrRepositoryFactory();
		Repository<Long, EntityImpl> repository = factory.getRepository(EntityImpl.class);
		assertNull(repository);
	}
	
	@Override
	protected void tearDown() throws Exception {
		((TransientRepository)JcrSessionManager.getJcrRepository()).shutdown();
	}
}
