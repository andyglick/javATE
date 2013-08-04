package it.amattioli.dominate.hibernate.specifications;

import org.hibernate.criterion.Restrictions;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.ObjectSpecification;

public class CriteriaObjectSpecification<T extends Entity<?>> extends ObjectSpecification<T> {

	public CriteriaObjectSpecification() {

	}

	public CriteriaObjectSpecification(String propertyName) {
		super(propertyName);
	}

	@Override
	public void itselfAssembleQuery(Assembler assembler) {		
		addCriteria((CriteriaAssembler) assembler);
	}

	public boolean itselfSupportsAssembler(Assembler assembler) {
		return assembler instanceof CriteriaAssembler;
	}
	
	private void addCriteria(CriteriaAssembler assembler) {
        if (wasSet()) {
        	assembler.addCriterion(Restrictions.eq(getPropertyName(), getValue()));
        }
    }

}
