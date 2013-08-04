package it.amattioli.authorizate.rules;

import it.amattioli.authorizate.users.User;

/**
 * An authorization rule
 * 
 * @author a.mattioli
 */
public interface Rule {

	/**
	 * Check if this rule is satisfied by a user and for a certain object.
	 * 
	 * @param user the user that would perform the operation to which this rule
	 *        is bound
	 * @param subject the object on which the operation should be performed
	 * @return true if the rule is satisfied, false otherwise
	 */
	public boolean check(User user, Object subject);

}
