package it.amattioli.dominate.hibernate.specifications;

import org.hibernate.criterion.Restrictions;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.LongSpecification;

public class CriteriaLongSpecification<T extends Entity<?>> extends LongSpecification<T> {

	public CriteriaLongSpecification() {

	}

	public CriteriaLongSpecification(String propertyName) {
		super(propertyName);
	}

	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		addCriteria((CriteriaAssembler) assembler);
	}

	@Override
	public boolean itselfSupportsAssembler(Assembler assembler) {
		return assembler instanceof CriteriaAssembler;
	}
	
	private void addCriteria(CriteriaAssembler assembler) {
        if (wasSet()) {
        	switch (getComparisonType()) {
        	case EQUAL:
        		assembler.addCriterion(Restrictions.eq(getPropertyName(), getValue()));
        		break;
        	case GREATER:
        		assembler.addCriterion(Restrictions.gt(getPropertyName(), getValue()));
        		break;
        	case GREATER_EQ:
        		assembler.addCriterion(Restrictions.ge(getPropertyName(), getValue()));
        		break;
        	case LOWER:
        		assembler.addCriterion(Restrictions.lt(getPropertyName(), getValue()));
        		break;
        	case LOWER_EQ:
        		assembler.addCriterion(Restrictions.le(getPropertyName(), getValue()));
        		break;
        	}
        }
    }

}
