package it.amattioli.workstate.actions;

import java.util.*;
import bsh.Interpreter;
import it.amattioli.workstate.exceptions.*;

public class BeanShellStateAction extends AbstractStateAction {
	private String code;

	public BeanShellStateAction(String code) {
		this.code = code;
	}

	public void doAction(AttributeHandler state) throws WorkflowException {
		try {
			Interpreter i = new Interpreter();
			Map<String,?> attrMap = state.getAllAttributes();
			for (Map.Entry<String,?> curr: attrMap.entrySet()) {
				i.set(curr.getKey(), curr.getValue());
			}
			i.eval(code);
			for (String currKey: attrMap.keySet()) {
				state.setAttribute(currKey, i.get(currKey));
			}
		} catch (Exception e) {
			throw new WorkflowException("", e);
		}
	}
	
	@Override
	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof BeanShellStateAction) {
			BeanShellStateAction action = (BeanShellStateAction) o;
			result = this.code.equals(action.code);
		}
		return result;
	}

	private static final int PRIME = 31;
	
	@Override
	public int hashCode() {
		return PRIME + ((code == null) ? 0 : code.hashCode());
	}
}
