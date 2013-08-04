package it.amattioli.dominate.hibernate.specifications;

import java.io.Serializable;

import org.hibernate.criterion.Restrictions;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.MultipleEntitySpecification;

public class CriteriaMultipleEntitySpecification<T extends Entity<?>, I extends Serializable, U extends Entity<I>> extends MultipleEntitySpecification<T, I, U> {

	public CriteriaMultipleEntitySpecification() {

	}

	public CriteriaMultipleEntitySpecification(String propertyName, Class<U> entityClass) {
		super(propertyName, entityClass);
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
