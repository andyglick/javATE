package it.amattioli.encapsulate.dates.specifications;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.hibernate.specifications.CriteriaAssembler;
import it.amattioli.dominate.specifications.Assembler;

public class CriteriaTimeIntervalSpecification<T extends Entity<?>> extends TimeIntervalSpecification<T> {

	public CriteriaTimeIntervalSpecification() {

	}

	public CriteriaTimeIntervalSpecification(TimeIntervalSpecification<T> chained) {
		super(chained);
	}

	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		addCriteria(((CriteriaAssembler) assembler).assembledCriteria());
	}

	@Override
	public boolean itselfSupportsAssembler(Assembler assembler) {
		return assembler instanceof CriteriaAssembler;
	}
	
	private void addCriteria(Criteria crit) {
        if (wasSet()) {
        	if (getIncludedValue() != null) {
        		crit.add(Restrictions.le(getPropertyName()+".low", getIncludedValue()));
        		crit.add(Restrictions.gt(getPropertyName()+".high", getIncludedValue()));
        	} else {
        		crit.add(Restrictions.ge(getPropertyName()+".low", getIncludingInterval().getLow()));
        		crit.add(Restrictions.le(getPropertyName()+".high", getIncludingInterval().getHigh()));
        	}
        }
    }

}
