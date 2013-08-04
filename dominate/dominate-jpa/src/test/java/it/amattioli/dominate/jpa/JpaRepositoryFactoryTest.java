package it.amattioli.dominate.jpa;

import it.amattioli.dominate.Repository;
import it.amattioli.dominate.RepositoryFactory;
import it.amattioli.dominate.memory.CollectionRepository;
import junit.framework.TestCase;

public class JpaRepositoryFactoryTest extends TestCase {

    @Override
	protected void setUp() throws Exception {
    	JpaTestInit.initWithAssoc();
	}

	public void testDefaultRepositoryConstruction() {
        RepositoryFactory fac = new JpaRepositoryFactory();
        Repository<Long, FakeEntity> rep = fac.getRepository(FakeEntity.class);
        assertTrue(rep instanceof JpaRepository);
        assertEquals(new it.amattioli.dominate.sessions.SessionManagerRegistry().currentSessionManager(),
                     ((JpaRepository<Long, FakeEntity>)rep).getSessionManager());
    }

	public void testCollectionRepositoryConstruction() {
		RepositoryFactory fac = new JpaRepositoryFactory();
		FakeEntity f = new FakeEntity();
        Repository<Long, AssociatedEntity> rep = fac.getRepository(f.getAssoc());
        assertNull(rep);
	}
	
//    public void testCustomRepositoryConstruction() {
//        RepositoryFactory fac = new CustomRepositoryFactoryStub();
//        Repository<Long, FakeEntity> rep = fac.getRepository(FakeEntity.class);
//        assertTrue(rep instanceof HibernateRepositoryStub);
//    }
    
    @Override
	protected void tearDown() throws Exception {
		JpaSessionManager.disconnectAll();
		JpaSessionManager.setPersistenceUnitName(null);
	}

}
