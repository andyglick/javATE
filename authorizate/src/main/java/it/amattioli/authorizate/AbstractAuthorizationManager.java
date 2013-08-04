package it.amattioli.authorizate;

import it.amattioli.authorizate.rules.Rule;
import it.amattioli.authorizate.users.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author a.mattioli
 */
public abstract class AbstractAuthorizationManager implements AuthorizationManager {
	private Map<String, Collection<Rule>> opRules = new HashMap<String, Collection<Rule>>();

	@Override
	public boolean checkRule(User user, String operation, Object subject) {
		Collection<Rule> rules = (Collection<Rule>) opRules.get(operation);
		if (rules != null) {
			for (Rule curr: rules) {
				if (!curr.check(user, subject)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void addRule(String operation, Rule newRule) {
		Collection<Rule> rules = (Collection<Rule>) opRules.get(operation);
		if (rules == null) {
			rules = new ArrayList<Rule>();
			opRules.put(operation, rules);
		}
		rules.add(newRule);
	}

}
