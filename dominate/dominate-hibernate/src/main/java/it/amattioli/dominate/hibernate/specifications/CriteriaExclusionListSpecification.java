package it.amattioli.dominate.hibernate.specifications;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.criterion.Restrictions;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.ExclusionListSpecification;

public class CriteriaExclusionListSpecification<T extends Entity<?>> extends ExclusionListSpecification<T> {

	public CriteriaExclusionListSpecification() {
		
	}
	
	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		addCriteria((CriteriaAssembler) assembler);
	}

	public boolean itselfSupportsAssembler(Assembler assembler) {
		return assembler instanceof CriteriaAssembler;
	}
	
	private Collection<?> getIdExclusionList() {
		Collection<Object> result = new ArrayList<Object>();
		for (Entity<?> curr: getExclusionList()) {
			result.add(curr.getId());
		}
		return result;
	}
	
	private void addCriteria(CriteriaAssembler assembler) {
        if (wasSet()) {
        	assembler.addCriterion(Restrictions.not(Restrictions.in("id", getIdExclusionList())));
        }
    }

}
