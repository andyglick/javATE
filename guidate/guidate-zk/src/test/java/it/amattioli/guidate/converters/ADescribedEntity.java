package it.amattioli.guidate.converters;

import it.amattioli.dominate.Described;
import it.amattioli.dominate.EntityImpl;

public class ADescribedEntity extends EntityImpl implements Described {

	@Override
	public String getDescription() {
		return "description";
	}

}
