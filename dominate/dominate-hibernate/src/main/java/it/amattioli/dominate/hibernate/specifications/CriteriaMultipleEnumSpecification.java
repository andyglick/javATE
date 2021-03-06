package it.amattioli.dominate.hibernate.specifications;

import org.hibernate.criterion.Restrictions;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.MultipleEnumSpecification;

public class CriteriaMultipleEnumSpecification<T extends Entity<?>, U extends Enum<U>> extends MultipleEnumSpecification<T, U> {

	public CriteriaMultipleEnumSpecification() {

	}

	public CriteriaMultipleEnumSpecification(String propertyName, Class<U> enumClass) {
		super(propertyName, enumClass);
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
        	assembler.addCriterion(Restrictions.in(getPropertyName(), getValue()));
        }
    }

}
