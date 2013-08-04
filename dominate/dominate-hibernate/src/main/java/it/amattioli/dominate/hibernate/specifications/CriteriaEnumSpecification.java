package it.amattioli.dominate.hibernate.specifications;

import org.hibernate.criterion.Restrictions;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.EnumSpecification;

public class CriteriaEnumSpecification<T extends Entity<?>, U extends Enum<U>> extends EnumSpecification<T, U> {

	public CriteriaEnumSpecification() {

	}

	public CriteriaEnumSpecification(String propertyName, Class<U> enumClass) {
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
        	assembler.addCriterion(Restrictions.eq(getPropertyName(), getValue()));
        }
    }

}
