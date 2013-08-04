package it.amattioli.workstate.config;

import it.amattioli.workstate.core.*;

/**
 * Allows the construction of a {@link MetaState} instance given the configuration
 * strings read from a datasource. 
 * 
 */
public abstract class MetaStateBuilder {
	private String id;
	protected MetaState builtState;

	/**
	 * Construct a builder given the id of the state.
	 * 
	 */
	public MetaStateBuilder(String id) {
		this.id = id;
	}

	/**
	 * Return the identifier of the state that is being built
	 * 
	 */
	public String getId() {
		return id;
	}

	protected abstract MetaState createMetaState();

	/**
	 * Return the built state.
	 */
	public MetaState getBuiltMetaState() {
		if (builtState == null) {
			builtState = createMetaState();
		}
		return builtState;
	}

	public void setEntryAction(String entryAction) {
		// TODO: ECCEZIONE !!!!!!!
	}

	public void setExitAction(String exitAction) {
		// TODO: ECCEZIONE !!!!!!!
	}

}
