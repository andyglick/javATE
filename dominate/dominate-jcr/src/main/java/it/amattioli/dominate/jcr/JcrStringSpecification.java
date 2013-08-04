package it.amattioli.dominate.jcr;

import org.apache.jackrabbit.ocm.query.Filter;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.StringSpecification;

public class JcrStringSpecification<T extends Entity<?>> extends StringSpecification<T> {
	
	public JcrStringSpecification() {
		
	}
	
	public JcrStringSpecification(String propertyName) {
		super(propertyName);
	}
	
	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		if (wasSet()) {
			JcrAssembler jcrAsm = (JcrAssembler)assembler;
			Filter filter = jcrAsm.createFilter();
			switch(getComparisonType()) {
			case CONTAINS: 
				filter.addContains(getPropertyName(), getValue());
				break;
			case EXACT:
				filter.addEqualTo(getPropertyName(), getValue());
				break;
			case STARTS:
				filter.addLike(getPropertyName(), getValue()+"%");
				break;
			}
			jcrAsm.addFilter(filter);
		}
	}

	@Override
	public boolean itselfSupportsAssembler(Assembler assembler) {
		return assembler instanceof JcrAssembler;
	}
}
