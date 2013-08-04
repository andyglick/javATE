package it.amattioli.authorizate.users.ldap;

import it.amattioli.authorizate.users.DefaultRole;
import it.amattioli.dominate.lazy.LazyList;

import java.util.List;

public class UserRolesList extends LazyList<DefaultRole> {
	private String userDn;
	
	public UserRolesList(String userDn) {
		this.userDn = userDn;
	}

	@Override
	protected List<DefaultRole> findTarget() {
		LdapRoleRepository rep = new LdapRoleRepository();
		return rep.getRolesFor(userDn);
	}

}
