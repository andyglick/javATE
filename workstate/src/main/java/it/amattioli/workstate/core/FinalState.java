package it.amattioli.workstate.core;

import it.amattioli.workstate.exceptions.WorkflowException;

/**
 * 
 * @author a.mattioli
 */
public class FinalState extends PseudoState {

	public FinalState(MetaFinalState metaState, CompositeState parent) {
		super(metaState, parent);
	}

	public void enter() throws WorkflowException {
		super.enter();
		getParent().terminate();
	}

}
