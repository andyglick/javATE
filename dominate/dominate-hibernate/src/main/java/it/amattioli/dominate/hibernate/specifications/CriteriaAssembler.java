package it.amattioli.dominate.hibernate.specifications;

import java.util.Stack;

import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.dflt.PredicateAssembler;

import org.apache.commons.collections.Predicate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

public class CriteriaAssembler implements Assembler {
	private Criteria criteria;
	private boolean negated = false;
	private Stack<Junction> stackedJunctions = new Stack<Junction>();
	private Junction currentJunction;
	private PredicateAssembler additional;

	public CriteriaAssembler(Criteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public void startNegation() {
		negated = true;
	}
	
	@Override
	public void endNegation() {
		negated = false;
	}

	@Override
	public void startConjunction() {
		stackedJunctions.push(currentJunction);
		currentJunction = Restrictions.conjunction();
	}
	
	@Override
	public void endConjunction() {
		endJunction();
	}

	private void endJunction() {
		Criterion toBeAdded = currentJunction;
		if (negated) {
			toBeAdded = Restrictions.not(toBeAdded);
		}
		if (stackedJunctions.peek() != null) {
			stackedJunctions.peek().add(toBeAdded);
		} else {
			criteria.add(toBeAdded);
		}
		currentJunction = stackedJunctions.pop();
	}

	@Override
	public void startDisjunction() {
		stackedJunctions.push(currentJunction);
		currentJunction = Restrictions.disjunction();
	}
	
	@Override
	public void endDisjunction() {
		endJunction();
	}

	public void addCriterion(Criterion criterion) {
		Criterion toBeAdded = criterion;
		if (negated) {
			toBeAdded = Restrictions.not(toBeAdded);
		}
		if (currentJunction != null) {
			currentJunction.add(toBeAdded);
		} else {
			criteria.add(toBeAdded);
		}
	}
	
	public Criteria assembledCriteria() {
		return criteria;
	}
	
	public void addAdditionalPredicate(Predicate pred) {
		if (!hasAdditionalPredicate()) {
			additional = new PredicateAssembler();
		}
		additional.addAssembledPredicate(pred);
	}
	
	public Predicate additionalPredicate() {
		if (!hasAdditionalPredicate()) {
			return null;
		}
		return additional.assembledPredicate();
	}
	
	public boolean hasAdditionalPredicate() {
		return additional != null;
	}

	@Override
	public void noResults() {
		addCriterion(Restrictions.sqlRestriction("1 = 0"));
	}

}
