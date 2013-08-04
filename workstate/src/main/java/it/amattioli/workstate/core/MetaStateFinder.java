package it.amattioli.workstate.core;

import it.amattioli.workstate.info.Receiver;
import it.amattioli.workstate.info.Visitor;

public class MetaStateFinder implements Visitor {
	private String matchingTag;
	private MetaState result;
	
	public MetaStateFinder(String matchingTag) {
		this.matchingTag = matchingTag;
	}
	
	@Override
	public void visit(Receiver receiver) {
		if (receiver instanceof MetaRealState && ((MetaRealState)receiver).getTag().equals(matchingTag)) {
			result = (MetaRealState)receiver;
		} else {
			receiver.receive(this);
		}
	}
	
	public MetaState getResult() {
		return result;
	}

}
