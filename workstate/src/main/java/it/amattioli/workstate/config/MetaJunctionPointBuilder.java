package it.amattioli.workstate.config;

import it.amattioli.workstate.core.*;

public class MetaJunctionPointBuilder extends MetaStateBuilder {

	public MetaJunctionPointBuilder(String id) {
		super(id);
	}

	protected MetaState createMetaState() {
		return new MetaJunctionPoint();
	}

}
