package it.amattioli.authorizate.users.ldap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.commons.lang.StringUtils;

import it.amattioli.authorizate.users.DefaultRole;
import it.amattioli.authorizate.users.Role;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.Specification;
import it.amattioli.dominate.util.GenericComparator;
import it.amattioli.dominate.util.MultiPropertyComparator;

public class LdapRoleRepository extends AbstractLdapRepository<String, DefaultRole> {
	private static final String[] attrList = {"cn","description"};
	
	@Override
	public DefaultRole get(String id) {
		DefaultRole result = new DefaultRole();
		DirContext ctx = LdapServer.getServerContext();
		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		sc.setReturningAttributes(attrList);
		try {
			NamingEnumeration<SearchResult> en = ctx.search(LdapServer.getRoleBase(), "cn={0}", new Object[] {id}, sc);
			if (en.hasMoreElements()) {
				SearchResult dirObj = (SearchResult)en.next();
				fillRole(result, dirObj);
			}
		} catch(NamingException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	protected void fillRole(DefaultRole result, NameClassPair dirObj) throws NamingException {
		Attributes attributes;
		if (dirObj instanceof SearchResult) {
			attributes = ((SearchResult)dirObj).getAttributes();
		} else {
			attributes = LdapServer.getServerContext().getAttributes(dirObj.getNameInNamespace(), attrList);
		}
		Attribute cn = attributes.get("cn");
		if (cn != null) {
			result.setId((String)cn.get());
		}
		Attribute description = attributes.get("description");
		if (description != null) {
			result.setDescription((String)description.get());
		}
		result.setChildren(new RoleChildrenList(dirObj.getNameInNamespace()));
	}
	
	@Override
	public DefaultRole getByPropertyValue(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(DefaultRole role) {
		DirContext ctx = LdapServer.getServerContext();
		String dn = "cn="+role.getId()+","+LdapServer.getRoleBase();
		Attributes attrs = new BasicAttributes();
		Attribute objectClass = new BasicAttribute("objectClass");
		objectClass.add("top");
		objectClass.add("groupOfUniqueNames");
		attrs.put(objectClass);
		Attribute description = new BasicAttribute("description", role.getDescription());
		attrs.put(description);
		try {
			Attribute uniquemember = ctx.getAttributes(dn, new String[] {"uniquemember"}).get("uniquemember");
			attrs.put(uniquemember);
		} catch (NameNotFoundException e) {
			Attribute uniquemember = new BasicAttribute("uniquemember", "cn=dummy");
			attrs.put(uniquemember);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
		try {
			ctx.rebind(dn, null, attrs);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
		
		Collection<DefaultRole> currRoles = new RoleChildrenList(dn);
		
		Collection<DefaultRole> addedRoles = new ArrayList<DefaultRole>(role.getChildren());
		addedRoles.removeAll(currRoles);
		modifyRoles(dn, DirContext.ADD_ATTRIBUTE, addedRoles);
		
		Collection<DefaultRole> removedRoles = new ArrayList<DefaultRole>(currRoles);
		removedRoles.removeAll(role.getChildren());
		modifyRoles(dn, DirContext.REMOVE_ATTRIBUTE, removedRoles);
	}
	
	private void modifyRoles(String dn, int modOp, Collection<DefaultRole> roles) {
		DirContext ctx = LdapServer.getServerContext();
		for (DefaultRole role: roles) {
			String roleDn = "cn="+role.getId()+","+LdapServer.getRoleBase();
			ModificationItem mod = new ModificationItem(modOp, new BasicAttribute("uniqueMember",dn));
			try {
				ctx.modifyAttributes(roleDn, new ModificationItem[] {mod} );
			} catch (NamingException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	@Override
	public void remove(String objectId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(DefaultRole object) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isRemoveAllowed() {
		return false;
	}

	@Override
	public List<DefaultRole> list() {
		return list((Specification<DefaultRole>)null);
	}

	@Override
	public List<DefaultRole> list(Specification<DefaultRole> spec) {
		List<DefaultRole> result = new ArrayList<DefaultRole>();
		DirContext ctx = LdapServer.getServerContext();
		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		sc.setReturningAttributes(attrList);
		try {
			NamingEnumeration<? extends NameClassPair> en = null;
			if (spec == null) {
				en = ctx.list(LdapServer.getRoleBase());
			} else {
				LdapQueryAssembler assembler = new LdapQueryAssembler();
				spec.assembleQuery(assembler);
				if (StringUtils.isBlank(assembler.getAssembledQuery())) {
					en = ctx.list(LdapServer.getRoleBase());
				} else {
					en = ctx.search(LdapServer.getRoleBase(), 
							        assembler.getAssembledQuery(), 
							        sc);
				}
			}
			while (en.hasMoreElements()) {
				DefaultRole curr = new DefaultRole();
				NameClassPair dirObj = (NameClassPair)en.next();
				fillRole(curr, dirObj);
				result.add(curr);
			}
		} catch(NamingException e) {
			throw new RuntimeException(e);
		}
		if (spec != null) {
			
		}
		if (getOrderProperty() != null) {
			Collections.sort(result,new MultiPropertyComparator<DefaultRole>(getOrders()));
        }
		int lastIdx = last+1;
        if (lastIdx <= 0) {
            lastIdx = result.size(); 
        }
        if (lastIdx > result.size()) {
        	lastIdx = result.size();
        }
        result = result.subList(first,lastIdx);
		return result;
	}
	
	public List<DefaultRole> getRolesFor(String memberDn) {
		DirContext ctx = LdapServer.getServerContext();
		List<DefaultRole> result = new ArrayList<DefaultRole>();
		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		sc.setReturningAttributes(attrList);
		try {
			NamingEnumeration<SearchResult> en = ctx.search(LdapServer.getRoleBase(), "(uniqueMember={0})", new Object[] {memberDn}, sc);
			while (en.hasMoreElements()) {
				SearchResult dirObj = (SearchResult)en.next();
				DefaultRole role = new DefaultRole();
				fillRole(role, dirObj);
				result.add(role);
			}
		} catch(NamingException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
