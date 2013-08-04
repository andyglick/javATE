package it.amattioli.dominate.hibernate;

import it.amattioli.dominate.Repository;
import it.amattioli.dominate.RepositoryFactory;
import it.amattioli.dominate.memory.CollectionRepository;
import junit.framework.TestCase;

public class HibernateRepositoryFactoryTest extends TestCase {

    @Override
	protected void setUp() throws Exception {
    	HibernateTestInit.initWithAssoc();
	}

	public void testDefaultRepositoryConstruction() {
        RepositoryFactory fac = new HibernateRepositoryFactory();
        Repository<Long, FakeEntity> rep = fac.getRepository(FakeEntity.class);
        assertTrue(rep instanceof ClassHibernateRepository);
        assertEquals(new it.amattioli.dominate.sessions.SessionManagerRegistry().currentSessionManager(),
                     ((AbstractHibernateRepository<Long, FakeEntity>)rep).getSessionManager());
    }
	
	public void testPersistentCollectionRepositoryConstruction() {
		RepositoryFactory fac = new HibernateRepositoryFactory();
        Repository<Long, FakeEntity> frep = fac.getRepository(FakeEntity.class);
        FakeEntity f = frep.get(1L);
        Repository<Long, AssociatedEntity> rep = fac.getRepository(f.getAssoc());
        assertTrue(rep instanceof CollectionHibernateRepository);
        assertEquals(new it.amattioli.dominate.sessions.SessionManagerRegistry().currentSessionManager(),
                ((AbstractHibernateRepository<Long, AssociatedEntity>)rep).getSessionManager());
	}
	
	public void testNonPersistentCollectionRepositoryConstruction() {
		RepositoryFactory fac = new HibernateRepositoryFactory();
		FakeEntity f = new FakeEntity();
        Repository<Long, AssociatedEntity> rep = fac.getRepository(f.getAssoc());
        assertTrue(rep instanceof CollectionRepository);
	}
    
    public void testCustomRepositoryConstruction() {
        RepositoryFactory fac = new CustomRepositoryFactoryStub();
        Repository<Long, FakeEntity> rep = fac.getRepository(FakeEntity.class);
        assertTrue(rep instanceof HibernateRepositoryStub);
    }
    
    @Override
	protected void tearDown() throws Exception {
		HibernateSessionManager.disconnectAll();
		HibernateSessionManager.setCfgResource(null);
	}

}
