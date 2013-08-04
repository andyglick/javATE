package it.amattioli.workstate.hibernate;

import it.amattioli.workstate.core.MetaCompositeState;
import it.amattioli.workstate.core.MetaConcurrentState;
import it.amattioli.workstate.core.MetaRealState;

public class MetaStateQueryBuilder {
	
	private MetaStateQueryBuilder() {
		
	}

	public static String buildQueryString(MetaRealState m) {
		String stateTag = "";
		if (m instanceof MetaCompositeState) {
			stateTag = "%";
		}
		do {
			if (m instanceof MetaConcurrentState) {
				stateTag = "%" + stateTag + "%";
			}
			if (!stateTag.isEmpty()) {
				stateTag = "[" + stateTag + "]";
			}
			stateTag = m.getTag() + stateTag;
			m = m.getParent();
		} while (m != null);
		return stateTag;
	}
	
}
