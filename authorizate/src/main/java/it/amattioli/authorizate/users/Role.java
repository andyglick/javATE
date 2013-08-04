package it.amattioli.authorizate.users;

import it.amattioli.dominate.Entity;

import java.util.List;

public interface Role extends Entity<String> {

	public List<? extends Role> getChildren();
	
	public boolean hasRole(Role role);
	
}
