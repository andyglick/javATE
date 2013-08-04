package it.amattioli.dominate.morphia;

import com.google.code.morphia.query.CriteriaContainerImpl;
import com.google.code.morphia.query.FieldEnd;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.ObjectSpecification;

public class MorphiaObjectSpecification <T extends Entity<?>> extends ObjectSpecification<T> {

	public MorphiaObjectSpecification() {
		
	}
	
	public MorphiaObjectSpecification(String propertyName) {
		super(propertyName);
	}
	
	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		if (wasSet()) {
			MorphiaAssembler mAsm = (MorphiaAssembler)assembler;
			FieldEnd<? extends CriteriaContainerImpl> criteriaField = mAsm.getQuery().criteria(getPropertyName());
			CriteriaContainerImpl criteria = criteriaField.equal(getValue());
			mAsm.addCriteria(criteria);
		}
	}

	@Override
	public boolean itselfSupportsAssembler(Assembler assembler) {
		return assembler instanceof MorphiaAssembler;
	}

}
