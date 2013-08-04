package it.amattioli.workstate.core;

import it.amattioli.workstate.actions.StateAction;
import it.amattioli.workstate.config.Configuration;
import it.amattioli.workstate.info.Visitor;

/**
 * 
 * @author a.mattioli
 */
public class MetaReferenceState extends MetaRealState {
	private String referencedModel;

	public MetaReferenceState(String tag, StateAction entry, StateAction exit, String referencedModel) {
		super(tag, entry, exit);
		this.referencedModel = referencedModel;
	}

	private Configuration buildConfiguration() {
		Configuration r;
		try {
			Class<? extends Configuration> configClass = getConfig().getClass();
			r = (Configuration) configClass.newInstance();
		} catch (InstantiationException ie) {
			throw new RuntimeException(ie);
		} catch (IllegalAccessException iae) {
			throw new RuntimeException(iae);
		}
		r.setSource(referencedModel);
		r.setId(referencedModel);
		return r;
	}

	public State newState(CompositeState parent) {
		Configuration r = buildConfiguration();
		return new ReferenceState(this, parent, r);
	}
	
	@Override
	public void receive(Visitor visitor) {
		// TODO: Must be implemented
	}

}
