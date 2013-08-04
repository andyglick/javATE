package it.amattioli.dominate.specifications;

import it.amattioli.dominate.Specification;

import java.util.EventObject;

public class SpecificationChangeEvent extends EventObject {

	public SpecificationChangeEvent(Specification<?> source) {
		super(source);
	}

}
