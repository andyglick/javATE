package it.amattioli.dominate.specifications;

import it.amattioli.dominate.Entity;

public class MyStringSpecification<T extends Entity<?>> extends StringSpecification<T> {

	public MyStringSpecification(String propertyName) {
		super(propertyName);
	}
	
	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean itselfSupportsAssembler(Assembler assembler) {
		// TODO Auto-generated method stub
		return false;
	}

}
