package it.amattioli.authorizate.rules;

/**
 * 
 * @author a.mattioli
 */
public class OrRule extends CompositeRule {

	public OrRule(Rule a, Rule b) {
		addRule(a);
		addRule(b);
	}

	protected boolean initResult() {
		return false;
	}

	protected boolean compose(boolean a, boolean b) {
		return a || b;
	}

}
