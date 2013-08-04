package it.amattioli.dominate.hibernate.specifications;

import java.io.Serializable;

import org.hibernate.criterion.Restrictions;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.EntitySpecification;

public class CriteriaEntitySpecification<T extends Entity<?>, I extends Serializable, U extends Entity<I>> extends EntitySpecification<T, I, U> {

	public CriteriaEntitySpecification() {

	}

	public CriteriaEntitySpecification(String propertyName, Class<U> entityClass) {
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
        	assembler.addCriterion(Restrictions.eq(getPropertyName(), getValue()));
        }
    }

}
