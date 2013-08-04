package it.amattioli.workstate.config;

import it.amattioli.workstate.core.MetaAttribute;
import it.amattioli.workstate.core.MetaReferenceState;
import it.amattioli.workstate.core.MetaState;

/**
 * 
 * @author a.mattioli
 */
public class MetaReferenceStateBuilder extends MetaRealStateBuilder {
	private String ref;

	public MetaReferenceStateBuilder(String tag, String id) {
		super(tag, id);
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	protected MetaState createMetaState() {
		MetaReferenceState result = new MetaReferenceState(getTag(), getEntryAction(), getExitAction(), ref);
		for (MetaAttribute currAttribute: getTempAttributeRepository()) {
			result.addAttribute(currAttribute);
		}
		return result;
	}

}
