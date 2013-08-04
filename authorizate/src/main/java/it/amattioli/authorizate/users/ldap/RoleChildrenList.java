package it.amattioli.authorizate.users.ldap;

import it.amattioli.authorizate.users.DefaultRole;
import it.amattioli.dominate.lazy.LazyList;

import java.util.List;

public class RoleChildrenList extends LazyList<DefaultRole> {
	private String roleDn;
	
	public RoleChildrenList(String roleDn) {
		this.roleDn = roleDn;
	}

	@Override
	protected List<DefaultRole> findTarget() {
		LdapRoleRepository rep = new LdapRoleRepository();
		return rep.getRolesFor(roleDn);
	}

}
