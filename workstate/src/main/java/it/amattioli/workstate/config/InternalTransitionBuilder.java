package it.amattioli.workstate.config;

import it.amattioli.workstate.core.*;

public class InternalTransitionBuilder extends TransitionBuilder {
	private MetaState state;

	public InternalTransitionBuilder(MetaEvent event, MetaState state) {
		super(event);
		this.state = state;
	}

	public Transition getBuiltTransition() {
		return new InternalTransition(getEvent(), state, getAction(), getGuard());
	}

}
