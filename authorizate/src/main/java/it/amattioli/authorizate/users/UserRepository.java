package it.amattioli.authorizate.users;

import it.amattioli.dominate.Repository;

public interface UserRepository<U extends User> extends Repository<String, U> {

	public U getByName(String name);
	
}
