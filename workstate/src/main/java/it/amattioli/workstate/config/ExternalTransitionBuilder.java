package it.amattioli.workstate.config;

import it.amattioli.workstate.core.*;

public class ExternalTransitionBuilder extends TransitionBuilder {
	private MetaState start;
	private MetaState end;

	public ExternalTransitionBuilder(MetaEvent event, MetaState start, MetaState end) {
		super(event);
		this.start = start;
		this.end = end;
	}

	public Transition getBuiltTransition() {
		return new ExternalTransition(getEvent(), start, end, getAction(), getGuard());
	}

}
