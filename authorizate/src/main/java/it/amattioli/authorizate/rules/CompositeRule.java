package it.amattioli.authorizate.rules;

import it.amattioli.authorizate.users.User;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author a.mattioli
 */
public abstract class CompositeRule implements Rule {
	private Collection<Rule> rules = new ArrayList<Rule>();

	public boolean check(User user, Object subject) {
		boolean result = initResult();
		for (Rule curr: rules) {
			result = compose(result, curr.check(user, subject));
		}
		return result;
	}

	public void addRule(Rule rule) {
		rules.add(rule);
	}

	protected abstract boolean initResult();

	protected abstract boolean compose(boolean a, boolean b);

}
