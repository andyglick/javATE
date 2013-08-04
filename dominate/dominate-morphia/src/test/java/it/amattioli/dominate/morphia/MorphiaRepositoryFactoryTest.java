package it.amattioli.dominate.morphia;

import org.bson.types.ObjectId;

import com.google.code.morphia.Morphia;

import it.amattioli.dominate.Repository;
import it.amattioli.dominate.RepositoryFactory;
import junit.framework.TestCase;

public class MorphiaRepositoryFactoryTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		MorphiaSessionManager.setMorphia(new Morphia());
	}

	public void testForMappedClass() {
		MorphiaSessionManager.getMorphia().map(MyEntity.class);
		RepositoryFactory factory = new MorphiaRepositoryFactory();
		Repository<ObjectId, MyEntity> rep = factory.getRepository(MyEntity.class);
		assertNotNull(rep);
	}
	
	public void testForNotMappedClass() {
		RepositoryFactory factory = new MorphiaRepositoryFactory();
		Repository<ObjectId, MyEntity> rep = factory.getRepository(MyEntity.class);
		assertNull(rep);
	}
	
}
