package it.amattioli.workstate.actions;

import java.util.*;
import bsh.Interpreter;

/**
 * A guard implemented using a BeanShell script. This class allows to create
 * a guard using a Java expression written in a string without creating a
 * new class. The expression:
 * <ul>
 *   <li> must be BeanShell compatible</li>
 *   <li> must have a boolean return value</li>
 *   <li> can use state attributes and event parameters as variables</li>
 * </ul> 
 * For example, if "attr1" is the name of a state attribute and "param1" is the
 * name of an event parameter you can create a guard with:
 * <p>
 * new BeanShellGuard("attr1.equals(param1)");
 * 
 */
public class BeanShellGuard extends AbstractGuard {
	private String code;

	/**
	 * Build a guard using a BeanShell expression written in a string
	 * 
	 * @param code a BeanShell expression with boolean return value
	 * 
	 */
	public BeanShellGuard(String code) {
		if (code == null) {
			// TODO: EXCEPTION !!!!!!!!!
		}
		this.code = code.trim();
	}

	private void registerAttributes(Interpreter i, AttributeReader attrs) throws Exception {
		Map<String, ?> attrMap = attrs.getAllAttributes();
		for (Map.Entry<String, ?> curr : attrMap.entrySet()) {
			i.set(curr.getKey(), curr.getValue());
		}
	}

	public boolean check(AttributeReader event, AttributeReader state) {
		Boolean result = Boolean.FALSE;
		try {
			Interpreter i = new Interpreter();
			registerAttributes(i, state);
			registerAttributes(i, event);
			i.eval("boolean guardExpression = " + code);
			result = (Boolean) i.get("guardExpression");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result.booleanValue();
	}

	@Override
	public String toString() {
		return "{ " + code + " }";
	}

	@Override
	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof BeanShellGuard) {
			BeanShellGuard guard = (BeanShellGuard) o;
			result = this.code.equals(guard.code);
		}
		return result;
	}

	private static final int PRIME = 31;
	
	@Override
	public int hashCode() {
		return PRIME + ((code == null) ? 0 : code.hashCode());
	}

}
