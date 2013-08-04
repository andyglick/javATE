package it.amattioli.authorizate.rules;

import it.amattioli.authorizate.users.User;

/**
 * A rule that is always satisfied
 * 
 * @author andrea
 *
 */
public class AlwaysTrueRule implements Rule {

	public boolean check(User user, Object subject) {
		return true;
	}

}
