package it.amattioli.workstate.core;

import it.amattioli.workstate.exceptions.*;

import java.util.*;

public class ConcurrentState extends CompositeState {
	private MetaConcurrentState metaState;

	public ConcurrentState(MetaConcurrentState metaState, CompositeState parent) {
		super(metaState, parent);
		this.metaState = metaState;
	}

	private void initSubstates(Map<String,StateMemento> children) throws WorkflowException {
		try {
			for (MetaState curr: metaState.getSubstates()) {
				// Sono certo che i sottostati di un concurrent sono sequenziali
				// perche' lo verifico nel checkAllowedSubstate
				MetaSequentialState currMetaSeq = (MetaSequentialState)curr;
				State currState = currMetaSeq.newState(this);
				substates.put(currMetaSeq.getTag(), currState);
				StateMemento childMemento = (StateMemento) children.get(currMetaSeq.getTag());
				if (childMemento == null) {
					currState.enter();
				} else {
					currState.restore(childMemento);
				}
			}
		} catch (WorkflowException e) {
			for (Map.Entry<String,State> curr: substates.entrySet()) {
				String currTag = curr.getKey();
				State currState = curr.getValue();
				if ((currState.isActive()) && (children.get(currTag) == null)) {
					currState.reExit();
				}
			}
			reExit();
			throw e;
		}
	}

	/**
	 * Enter this state and position itself in the default sub-state of every
	 * region.
	 * 
	 */
	public void enter() throws WorkflowException {
		super.enter();
		Map<String,StateMemento> children = Collections.emptyMap();
		initSubstates(children);
	}

	public void restore(StateMemento memento) throws WorkflowException {
		super.restore(memento);
		initSubstates(memento.getChildren());
	}

	public void terminate() throws WorkflowException {
		for (State curr: substates.values()) {
			CompositeState currComposite = (CompositeState) curr;
			if (!currComposite.isComplete())
				return;
		}
		super.terminate();
	}
}
