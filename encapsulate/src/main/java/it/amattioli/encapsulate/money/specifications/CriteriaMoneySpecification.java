package it.amattioli.encapsulate.money.specifications;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.hibernate.specifications.CriteriaAssembler;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class CriteriaMoneySpecification<T extends Entity<?>> extends MoneySpecification<T> {

	public CriteriaMoneySpecification() {

	}

	public CriteriaMoneySpecification(String propertyName) {
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
        		crit.add(Restrictions.ge(getPropertyName()+".value", getValue().getLow().getValue()));
        	}
        	if (getValue().isHighBounded()) {
        		crit.add(Restrictions.lt(getPropertyName()+".value", getValue().getHigh().getValue()));
        	}
        }
    }

}
