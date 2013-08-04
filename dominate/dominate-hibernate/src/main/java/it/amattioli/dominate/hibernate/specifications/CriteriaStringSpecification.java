package it.amattioli.dominate.hibernate.specifications;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.ComparisonType;
import it.amattioli.dominate.specifications.StringSpecification;

public class CriteriaStringSpecification<T extends Entity<?>> extends StringSpecification<T> {
	private static final Map<ComparisonType,MatchMode> MATCH_MODES = new HashMap<ComparisonType, MatchMode>() {{
		put(ComparisonType.EXACT,MatchMode.EXACT);
		put(ComparisonType.STARTS,MatchMode.START);
		put(ComparisonType.CONTAINS,MatchMode.ANYWHERE);
	}};

	public CriteriaStringSpecification() {
		
	}
	
	public CriteriaStringSpecification(String propertyName) {
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
        	assembler.addCriterion(Restrictions.ilike(getPropertyName(), getValue(), getMatchMode(getComparisonType())));
        }
    }
	
	private MatchMode getMatchMode(ComparisonType type) {
		return MATCH_MODES.get(type);
	}

}
