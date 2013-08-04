package it.amattioli.authorizate;

import it.amattioli.authorizate.rules.Rule;
import it.amattioli.authorizate.users.User;

/**
 * An authorization manager let an application knows if a user is authorized at 
 * performing a certain operation on an object.
 * <p> 
 * The security manager performs its job applying a set of of rules.
 * These rules are configurable and can be added to the authorization manager
 * using the {@link #addRule(String, Rule)} method.
 * 
 * The operations are identified by a string id (a name). This id is set when you
 * add the rule and must be used subsequently to check the rules.
 * 
 * @author a.mattioli
 */
public interface AuthorizationManager {

	/**
	 * Check the rules to know if a certain user is authorized at performing a 
	 * given operation on a given object.
	 * 
	 * @param user the user that want to perform the operation
	 * @param operation the operation to be checked
	 * @param subject the object on which the operation should be performed
	 * @return true if the operation is authorized, false otherwise
	 */
	public boolean checkRule(User user, String operation, Object subject);

	/**
	 * Add a rule for a given operation. The operation is identified by a string.
	 * 
	 * @param operation the operation id
	 * @param newRule the adding rule
	 */
	public void addRule(String operation, Rule newRule);

}
