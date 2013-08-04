package it.amattioli.workstate.config;

import it.amattioli.workstate.core.*;

public class MetaConcurrentStateBuilder extends MetaCompositeStateBuilder {

	public MetaConcurrentStateBuilder(String tag, String id) {
		super(tag, id);
	}

	protected MetaState createMetaState() {
		MetaConcurrentState result = new MetaConcurrentState(getTag(), getEntryAction(), getExitAction());
		for (MetaAttribute currAttribute: getTempAttributeRepository()) {
			result.addAttribute(currAttribute);
		}
		for (MetaState currSubstate: getTempSubstateRepository()) {
			result.addMetaState(currSubstate);
		}
		return result;
	}

}
