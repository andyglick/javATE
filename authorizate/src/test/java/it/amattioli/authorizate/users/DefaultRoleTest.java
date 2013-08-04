package it.amattioli.authorizate.users;

import junit.framework.TestCase;

public class DefaultRoleTest extends TestCase {

	public void testCompositeRole() {
		DefaultRole parent = new DefaultRole();
		parent.setId("parent");
		DefaultRole firstChild = new DefaultRole();
		firstChild.setId("firstChild");
		parent.addChild(firstChild);
		DefaultRole secondChild = new DefaultRole();
		secondChild.setId("secondChild");
		parent.addChild(secondChild);
		assertTrue(parent.hasRole("firstChild"));
	}
	
}
