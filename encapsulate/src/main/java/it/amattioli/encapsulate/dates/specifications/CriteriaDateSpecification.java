package it.amattioli.encapsulate.dates.specifications;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.hibernate.specifications.CriteriaAssembler;
import it.amattioli.dominate.specifications.Assembler;

public class CriteriaDateSpecification<T extends Entity<?>> extends DateSpecification<T> {

	public CriteriaDateSpecification() {

	}

	public CriteriaDateSpecification(String propertyName) {
		super(propertyName);
	}

	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		addCriteria(((CriteriaAssembler) assembler).assembledCriteria());
	}

	@Override
	public boolean itselfSupportsAssembler(Assembler assembler) {
		try {
			return assembler instanceof CriteriaAssembler;
		} catch(Throwable e) {
			return false;
		}
	}
	
	private void addCriteria(Criteria crit) {
        if (wasSet()) {
        	if (getValue().isLowBounded()) {
        		crit.add(Restrictions.ge(getPropertyName(), getValue().getLow()));
        	}
        	if (getValue().isHighBounded()) {
        		crit.add(Restrictions.lt(getPropertyName(), getValue().getHigh()));
        	}
        }
    }

}
