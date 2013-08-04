package it.amattioli.authorizate.rules;

import it.amattioli.authorizate.users.User;

/**
 * A rule that is never satisfied
 * 
 * @author andrea
 *
 */
public class AlwaysFalseRule implements Rule {

	public boolean check(User user, Object subject) {
		return false;
	}

}
