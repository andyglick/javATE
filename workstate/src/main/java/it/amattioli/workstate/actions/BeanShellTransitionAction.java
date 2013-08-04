package it.amattioli.workstate.actions;

import java.util.*;
import bsh.Interpreter;
import it.amattioli.workstate.exceptions.*;

public class BeanShellTransitionAction extends AbstractTransitionAction {
	private String code;

	public BeanShellTransitionAction(String code) {
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

	public void doAction(AttributeReader event, AttributeHandler state) throws WorkflowException {
		try {
			Interpreter i = new Interpreter();
			registerAttributes(i, state);
			registerAttributes(i, event);
			i.eval(code);
			for (String currKey : state.getAllAttributes().keySet()) {
				state.setAttribute(currKey, i.get(currKey));
			}
		} catch (Exception e) {
			throw new WorkflowException("", e);
		}
	}

	@Override
	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof BeanShellTransitionAction) {
			BeanShellTransitionAction action = (BeanShellTransitionAction) o;
			result = this.code.equals(action.code);
		}
		return result;
	}

	@Override
	public String toString() {
		return "{ " + code + " }";
	}

	private static final int PRIME = 31;
	
	@Override
	public int hashCode() {
		return PRIME + ((code == null) ? 0 : code.hashCode());
	}

}
