package it.amattioli.authorizate.users.hibernate;

import it.amattioli.authorizate.users.DefaultUser;
import it.amattioli.authorizate.users.UserRepository;
import it.amattioli.dominate.hibernate.ClassHibernateRepository;

public class HibernateUserRepository extends ClassHibernateRepository<String, DefaultUser> implements UserRepository<DefaultUser> {

	public HibernateUserRepository() {
		super(DefaultUser.class);
	}

	@Override
	public DefaultUser getByName(String name) {
		return get(name);
	}

}
