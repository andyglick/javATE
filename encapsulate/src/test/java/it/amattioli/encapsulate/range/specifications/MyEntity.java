package it.amattioli.encapsulate.range.specifications;

import it.amattioli.dominate.EntityImpl;

public class MyEntity extends EntityImpl {
	private Long longAttribute;

	public Long getLongAttribute() {
		return longAttribute;
	}

	public void setLongAttribute(Long longAttribute) {
		this.longAttribute = longAttribute;
	}
}
