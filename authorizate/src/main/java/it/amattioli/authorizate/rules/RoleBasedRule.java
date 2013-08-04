package it.amattioli.authorizate.rules;

import it.amattioli.authorizate.users.User;

/**
 * A rule that check if the user possess a certain authorization role.
 * 
 * 
 * @author a.mattioli
 */
public class RoleBasedRule implements Rule {
	private String role;

	/**
	 * Build a rule that verifies if the user possess the role identified
	 * by the parameter string. 
	 * 
	 * @param role the role identifier
	 */
	public RoleBasedRule(String role) {
		this.role = role;
	}

	/**
     *
     */
	public boolean check(User user, Object subject) {
		return user.hasRole(role);
	}

}
