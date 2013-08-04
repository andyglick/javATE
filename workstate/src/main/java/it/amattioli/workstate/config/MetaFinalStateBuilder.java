package it.amattioli.workstate.config;

import it.amattioli.workstate.core.MetaFinalState;
import it.amattioli.workstate.core.MetaState;

/**
 * 
 * @author a.mattioli
 */
public class MetaFinalStateBuilder extends MetaStateBuilder {

	public MetaFinalStateBuilder(String id) {
		super(id);
	}

	protected MetaState createMetaState() {
		return new MetaFinalState();
	}

}
