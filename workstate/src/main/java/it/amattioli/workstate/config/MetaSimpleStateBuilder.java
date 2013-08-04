package it.amattioli.workstate.config;

import it.amattioli.workstate.core.*;

public class MetaSimpleStateBuilder extends MetaRealStateBuilder {

	public MetaSimpleStateBuilder(String tag, String id) {
		super(tag, id);
	}

	protected MetaState createMetaState() {
		MetaSimpleState result = new MetaSimpleState(getTag(), getEntryAction(), getExitAction());
		for (MetaAttribute currAttribute: getTempAttributeRepository()) {
			result.addAttribute(currAttribute);
		}
		return result;
	}

}
