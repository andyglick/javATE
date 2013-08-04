package it.amattioli.authorizate.users.ldap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.naming.NameClassPair;
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
import it.amattioli.authorizate.users.DefaultUser;
import it.amattioli.authorizate.users.Role;
import it.amattioli.authorizate.users.User;
import it.amattioli.authorizate.users.UserRepository;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.Specification;
import it.amattioli.dominate.util.GenericComparator;
import it.amattioli.dominate.util.MultiPropertyComparator;

public class LdapUserRepository extends AbstractLdapRepository<String, DefaultUser> implements UserRepository<DefaultUser> {
	private static final String[] attrList = {"cn","sn","uid","mail","userpassword"};
	
		
	@Override
	public DefaultUser get(String id) {
		DefaultUser result = new DefaultUser();
		DirContext ctx = LdapServer.getServerContext();
		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		sc.setReturningAttributes(attrList);
		try {
			NamingEnumeration<SearchResult> en = ctx.search(LdapServer.getUserBase(), "uid={0}", new Object[] {id}, sc);
			if (en.hasMoreElements()) {
				SearchResult dirObj = (SearchResult)en.next();
				fillUser(result, dirObj);
			}
		} catch(NamingException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	private void fillUser(DefaultUser result, NameClassPair dirObj) throws NamingException {
		Attributes attributes;
		if (dirObj instanceof SearchResult) {
			attributes = ((SearchResult)dirObj).getAttributes();
		} else {
			attributes = LdapServer.getServerContext().getAttributes(dirObj.getNameInNamespace(), attrList);
		}
		Attribute cn = attributes.get("cn");
		if (cn != null) {
			result.setCommonName((String)cn.get());
		}
		Attribute sn = attributes.get("sn");
		if (sn != null) {
			result.setSurname((String)sn.get());
		}
		Attribute uid = attributes.get("uid");
		if (uid != null) {
			result.setId((String)uid.get());
		}
		Attribute pwd = attributes.get("userpassword");
		if (pwd != null) {
			result.setPassword((byte[])pwd.get());
		}
		Attribute mail = attributes.get("mail");
		if (mail != null) {
			result.setMailAddr((String)mail.get());
		}
		addRoles(result, dirObj.getNameInNamespace());
	}
	
	private void addRoles(DefaultUser user, String userDn) {
		user.setRoles(new UserRolesList(userDn));
	}
	
	@Override
	public DefaultUser getByPropertyValue(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(DefaultUser user) {
		DirContext ctx = LdapServer.getServerContext();
		String dn = "uid="+user.getId()+","+LdapServer.getUserBase();
		Attributes attrs = new BasicAttributes();
		Attribute objectClass = new BasicAttribute("objectClass");
		objectClass.add("top");
		objectClass.add("person");
		objectClass.add("organizationalPerson");
		objectClass.add("inetOrgPerson");
		objectClass.add("extensibleObject");
		attrs.put(objectClass);
		Attribute cn = new BasicAttribute("cn", user.getCommonName());
		attrs.put(cn);
		Attribute sn = new BasicAttribute("sn", user.getSurname());
		attrs.put(sn);
		if (!StringUtils.isEmpty(user.getMailAddr())) {
			Attribute mail = new BasicAttribute("mail", user.getMailAddr());
			attrs.put(mail);
		}
		Attribute password = new BasicAttribute("userpassword", user.getPassword());
		attrs.put(password);
		try {
			ctx.rebind(dn, null, attrs);
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
		
		Collection<DefaultRole> currRoles = new UserRolesList(dn);
		
		Collection<DefaultRole> addedRoles = new ArrayList<DefaultRole>(user.getRoles());
		addedRoles.removeAll(currRoles);
		modifyRoles(dn, DirContext.ADD_ATTRIBUTE, addedRoles);
		
		Collection<DefaultRole> removedRoles = new ArrayList<DefaultRole>(currRoles);
		removedRoles.removeAll(user.getRoles());
		modifyRoles(dn, DirContext.REMOVE_ATTRIBUTE, removedRoles);
		
	}
	
	private void modifyRoles(String dn, int modOp, Collection<DefaultRole> roles) {
		DirContext ctx = LdapServer.getServerContext();
		for (Role role: roles) {
			String roleDn = "cn="+((DefaultRole)role).getId()+","+LdapServer.getRoleBase();
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
	public void remove(DefaultUser object) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isRemoveAllowed() {
		return false;
	}
	
	@Override
	public DefaultUser getByName(String name) {
		return get(name);
	}

	@Override
	public List<DefaultUser> list() {
		return list((Specification<DefaultUser>)null);
	}

	@Override
	public List<DefaultUser> list(Specification<DefaultUser> spec) {
		List<DefaultUser> result = new ArrayList<DefaultUser>();
		DirContext ctx = LdapServer.getServerContext();
		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		sc.setReturningAttributes(attrList);
		try {
			NamingEnumeration<? extends NameClassPair> en = null;
			if (spec == null) {
				en = ctx.list(LdapServer.getUserBase());
			} else {
				LdapQueryAssembler assembler = new LdapQueryAssembler();
				spec.assembleQuery(assembler);
				if (StringUtils.isBlank(assembler.getAssembledQuery())) {
					en = ctx.list(LdapServer.getUserBase());
				} else {
					en = ctx.search(LdapServer.getUserBase(), 
							        assembler.getAssembledQuery(), 
							        sc);
				}
			}
			while (en.hasMoreElements()) {
				DefaultUser curr = new DefaultUser();
				NameClassPair dirObj = (NameClassPair)en.next();
				fillUser(curr, dirObj);
				result.add(curr);
			}
		} catch(NamingException e) {
			throw new RuntimeException(e);
		}
		if (spec != null) {
			
		}
		if (getOrderProperty() != null) {
			Collections.sort(result,new MultiPropertyComparator<DefaultUser>(getOrders()));
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

}
