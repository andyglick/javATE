package it.amattioli.workstate.config;

import bsh.EvalError;
import it.amattioli.workstate.actions.*;
import it.amattioli.workstate.core.*;
import static it.amattioli.workstate.exceptions.ErrorMessage.*;
import static it.amattioli.workstate.exceptions.ErrorMessages.*;

public abstract class TransitionBuilder {
	private MetaEvent event;
	private TransitionAction action;
	private Guard guard;

	public TransitionBuilder(MetaEvent event) {
		this.event = event;
	}

	private TransitionAction newTransitionAction(String actionCode) {
		TransitionAction result = null;
		if (actionCode != null && !actionCode.equals("")) {
			if (actionCode.startsWith("{") && actionCode.endsWith("}")) {
				// E' uno script BeanShell
				result = new BeanShellTransitionAction(actionCode.substring(1, actionCode.length() - 1));
			} else {
				// E' il nome di una classe
				Object newAction = null;
				try {
					newAction = BeanShellHelper.evalExpr("new " + actionCode);
				} catch (EvalError e) {
					throw newIllegalArgumentException(SYNTAX_ERROR, "TransitionAction", actionCode);
				}
				if (newAction instanceof TransitionAction) {
					result = (TransitionAction) newAction;
				} else {
					throw newClassCastException(WRONG_CLASS, actionCode, "TransitionAction");
				}
			}
		}
		return result;
	}

	public void setAction(String actionCode) {
		action = newTransitionAction(actionCode);
	}

	private Guard newGuard(String guardCode) {
		Guard result = null;
		if (guardCode != null && !guardCode.equals("")) {
			if (guardCode.startsWith("{") && guardCode.endsWith("}")) {
				// E' uno script BeanShell
				String code = guardCode.substring(1, guardCode.length() - 1);
				result = new BeanShellGuard(code);
			} else {
				// E' il nome di una classe
				String code = guardCode;
				boolean negated = false;
				if (guardCode.startsWith("!")) {
					negated = true;
					code = guardCode.substring(1);
				}
				Object newGuard = null;
				try {
					newGuard = BeanShellHelper.evalExpr("new " + code);
				} catch (EvalError e) {
					throw newIllegalArgumentException(SYNTAX_ERROR, "Guard", guardCode);
				}
				if (newGuard instanceof Guard) {
					result = (Guard) newGuard;
				} else {
					throw newClassCastException(WRONG_CLASS, guardCode, "Guard");
				}
				if (negated) {
					result = new NegatedGuard(result);
				}
			}
		}
		return result;
	}

	public void setGuard(String guardCode) {
		guard = newGuard(guardCode);
	}

	protected MetaEvent getEvent() {
		return event;
	}

	protected TransitionAction getAction() {
		return action;
	}

	protected Guard getGuard() {
		return guard;
	}

	public abstract Transition getBuiltTransition();

}
