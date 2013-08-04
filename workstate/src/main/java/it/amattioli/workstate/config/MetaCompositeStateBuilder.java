package it.amattioli.workstate.config;

import it.amattioli.workstate.core.*;

import java.util.*;

public abstract class MetaCompositeStateBuilder extends MetaRealStateBuilder {
	private Collection<MetaState> tempSubstateRepository = new ArrayList<MetaState>();

	public MetaCompositeStateBuilder(String tag, String id) {
		super(tag, id);
	}

	protected Collection<MetaState> getTempSubstateRepository() {
		return tempSubstateRepository;
	}

	public void addSubstate(MetaState substate) {
		if (builtState == null) {
			getTempSubstateRepository().add(substate);
		} else {
			((MetaCompositeState) builtState).addMetaState(substate);
		}
	}

}
