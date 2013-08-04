package it.amattioli.authorizate.users;

import it.amattioli.dominate.Entity;

/**
 * 
 * @author a.mattioli
 */
public interface User extends Entity<String> {

	public String getName();

	public boolean hasRole(String role);

}
