package it.amattioli.authorizate.rules;

/**
 * 
 * @author a.mattioli
 */
public class AndRule extends CompositeRule {

	public AndRule(Rule a, Rule b) {
		addRule(a);
		addRule(b);
	}

	protected boolean initResult() {
		return true;
	}

	protected boolean compose(boolean a, boolean b) {
		return a && b;
	}

}
