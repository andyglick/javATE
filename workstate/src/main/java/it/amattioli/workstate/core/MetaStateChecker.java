package it.amattioli.workstate.core;

import it.amattioli.workstate.info.Receiver;
import it.amattioli.workstate.info.Visitor;

public class MetaStateChecker implements Visitor {
	private MetaState matchingMetaState;
	private boolean result = false;
	
	public MetaStateChecker(MetaState matchingMetaState) {
		this.matchingMetaState = matchingMetaState;
	}
	
	@Override
	public void visit(Receiver receiver) {
		if (((State)receiver).hasMetaState(matchingMetaState)) {
			result = true;
		} else {
			receiver.receive(this);
		}
	}
	
	public boolean getResult() {
		return result;
	}

}
