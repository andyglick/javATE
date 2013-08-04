package it.amattioli.dominate.morphia;

import com.google.code.morphia.query.CriteriaContainerImpl;
import com.google.code.morphia.query.FieldEnd;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.StringSpecification;

public class MorphiaStringSpecification <T extends Entity<?>> extends StringSpecification<T> {

	public MorphiaStringSpecification() {
		
	}
	
	public MorphiaStringSpecification(String propertyName) {
		super(propertyName);
	}
	
	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		if (wasSet()) {
			MorphiaAssembler mAsm = (MorphiaAssembler)assembler;
			FieldEnd<? extends CriteriaContainerImpl> criteriaField = mAsm.getQuery().criteria(getPropertyName());
			CriteriaContainerImpl criteria = null;
			switch(getComparisonType()) {
			case CONTAINS: 
				criteria = criteriaField.containsIgnoreCase(getValue());
				break;
			case EXACT:
				criteria = criteriaField.equal(getValue());
				break;
			case STARTS:
				criteria = criteriaField.startsWithIgnoreCase(getValue());
				break;
			}
			mAsm.addCriteria(criteria);
		}
	}

	@Override
	public boolean itselfSupportsAssembler(Assembler assembler) {
		return assembler instanceof MorphiaAssembler;
	}

}
