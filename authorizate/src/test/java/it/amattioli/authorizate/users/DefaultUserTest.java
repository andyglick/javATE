package it.amattioli.authorizate.users;

import junit.framework.TestCase;

public class DefaultUserTest extends TestCase {

	public void testPassword() {
		DefaultUser user = new DefaultUser();
		user.setPassword("mypassword");
		assertTrue(user.checkPassword("mypassword"));
	}
	
	public void testNoPassword() {
		DefaultUser user = new DefaultUser();
		assertTrue(user.checkPassword(""));
	}
	
	public void testHasRole() {
		DefaultRole role = new DefaultRole();
		role.setId("myrole");
		DefaultUser user = new DefaultUser();
		user.addRole(role);
		assertTrue(user.hasRole("myrole"));
		assertFalse(user.hasRole("otherrole"));
	}
	
}
