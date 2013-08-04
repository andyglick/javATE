package it.amattioli.workstate.config;

import it.amattioli.workstate.core.*;

public class MetaSequentialStateBuilder extends MetaCompositeStateBuilder {

	public MetaSequentialStateBuilder(String tag, String id) {
		super(tag, id);
	}

	protected MetaState createMetaState() {
		MetaSequentialState result = new MetaSequentialState(getTag(), getEntryAction(), getExitAction());
		for (MetaAttribute currAttribute: getTempAttributeRepository()) {
			result.addAttribute(currAttribute);
		}
		for (MetaState currSubstate: getTempSubstateRepository()) {
			result.addMetaState(currSubstate);
		}
		return result;
	}

}
