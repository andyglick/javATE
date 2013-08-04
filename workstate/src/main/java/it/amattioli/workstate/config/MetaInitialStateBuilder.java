package it.amattioli.workstate.config;

import it.amattioli.workstate.core.*;

public class MetaInitialStateBuilder extends MetaStateBuilder {

	public MetaInitialStateBuilder(String id) {
		super(id);
	}

	protected MetaState createMetaState() {
		return new MetaInitialState();
	}

}
