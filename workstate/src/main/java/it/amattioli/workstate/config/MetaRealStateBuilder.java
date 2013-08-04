package it.amattioli.workstate.config;

import java.util.*;
import bsh.EvalError;
import it.amattioli.workstate.actions.*;
import it.amattioli.workstate.core.*;
import static it.amattioli.workstate.exceptions.ErrorMessage.*;
import static it.amattioli.workstate.exceptions.ErrorMessages.*;

public abstract class MetaRealStateBuilder extends MetaStateBuilder implements AttributeOwnerBuilder {
	private String tag;
	private StateAction entryAction;
	private StateAction exitAction;
	private Collection<MetaAttribute> tempAttributeRepository = new ArrayList<MetaAttribute>();

	public MetaRealStateBuilder(String tag, String id) {
		super(id);
		this.tag = tag;
	}

	protected String getTag() {
		return tag;
	}

	protected StateAction getEntryAction() {
		return entryAction;
	}

	protected StateAction getExitAction() {
		return exitAction;
	}

	protected Collection<MetaAttribute> getTempAttributeRepository() {
		return tempAttributeRepository;
	}

	private StateAction newStateAction(String actionCode) {
		StateAction result = null;
		if (actionCode != null && !actionCode.equals("")) {
			if (actionCode.startsWith("{") && actionCode.endsWith("}")) {
				// E' uno script BeanShell
				result = new BeanShellStateAction(actionCode.substring(1,
						actionCode.length() - 1));
			} else {
				// E' il nome di una classe
				Object newAction = null;
				try {
					newAction = BeanShellHelper.evalExpr("new " + actionCode);
				} catch (EvalError e) {
					throw newIllegalArgumentException(SYNTAX_ERROR, "StateAction", actionCode);
				}
				if (newAction instanceof StateAction) {
					result = (StateAction) newAction;
				} else {
					throw newClassCastException(WRONG_CLASS, actionCode, "StateAction");
				}
			}
		}
		return result;
	}

	public void setEntryAction(String entryAction) {
		if (builtState != null) {
			throw newIllegalStateException(OBJECT_ALREADY_BUILT, "EntryAction", "MetaRealState");
		}
		this.entryAction = newStateAction(entryAction);
	}

	public void setExitAction(String exitAction) {
		if (builtState != null) {
			throw newIllegalStateException(OBJECT_ALREADY_BUILT, "ExitAction", "MetaRealState");
		}
		this.exitAction = newStateAction(exitAction);
	}

	public void addAttribute(MetaAttribute attr) {
		if (builtState == null) {
			getTempAttributeRepository().add(attr);
		} else {
			((MetaRealState) builtState).addAttribute(attr);
		}
	}

}
