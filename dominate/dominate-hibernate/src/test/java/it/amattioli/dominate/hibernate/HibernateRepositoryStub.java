package it.amattioli.dominate.hibernate;

public class HibernateRepositoryStub extends ClassHibernateRepository<Long, FakeEntity> {

    public HibernateRepositoryStub() {
        super(FakeEntity.class);
    }
    
}
