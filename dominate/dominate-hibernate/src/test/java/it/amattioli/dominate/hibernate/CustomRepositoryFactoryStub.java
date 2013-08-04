package it.amattioli.dominate.hibernate;

import it.amattioli.dominate.Repository;

public class CustomRepositoryFactoryStub extends HibernateRepositoryFactory {

    public Repository<Long, FakeEntity> getFakeEntityRepository() {
        return new HibernateRepositoryStub();
    }
    
}
