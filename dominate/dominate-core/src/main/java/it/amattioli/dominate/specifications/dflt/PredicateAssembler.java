package it.amattioli.dominate.specifications.dflt;

import it.amattioli.dominate.specifications.Assembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.AllPredicate;
import org.apache.commons.collections.functors.AnyPredicate;
import org.apache.commons.collections.functors.FalsePredicate;
import org.apache.commons.collections.functors.NotPredicate;

public class PredicateAssembler implements Assembler {
	private Stack<Collection<Predicate>> stackedPredicates = new Stack<Collection<Predicate>>();
	private Collection<Predicate> predicates = new ArrayList<Predicate>();
	
	public void addAssembledPredicate(Predicate assembled) {
		predicates.add(assembled);
	}
	
	public Predicate assembledPredicate() {
		return AllPredicate.getInstance(predicates);
	}
	
	@Override
	public void startNegation() {
		stackedPredicates.push(predicates);
		predicates = new ArrayList<Predicate>();
	}
	
	@Override
	public void endNegation() {
		if (predicates.size() > 1) {
			throw new IllegalStateException("Cannot negate more than one specification");
		} else if (predicates.size() == 1) {
			Predicate collected = NotPredicate.getInstance(predicates.iterator().next());
			predicates = stackedPredicates.pop();
			predicates.add(collected);
		} else {
			predicates = stackedPredicates.pop();
		}
	}

	@Override
	public void startConjunction() {
		stackedPredicates.push(predicates);
		predicates = new ArrayList<Predicate>();
	}
	
	@Override
	public void endConjunction() {
		Predicate collected = AllPredicate.getInstance(predicates);
		predicates = stackedPredicates.pop();
		predicates.add(collected);
	}

	@Override
	public void startDisjunction() {
		stackedPredicates.push(predicates);
		predicates = new ArrayList<Predicate>();
	}

	@Override
	public void endDisjunction() {
		Predicate collected = AnyPredicate.getInstance(predicates);
		predicates = stackedPredicates.pop();
		predicates.add(collected);
	}

	@Override
	public void noResults() {
		predicates.add(FalsePredicate.getInstance());
	}
	
}
