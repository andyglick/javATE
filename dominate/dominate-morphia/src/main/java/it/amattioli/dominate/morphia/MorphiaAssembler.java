package it.amattioli.dominate.morphia;

import java.util.Stack;

import com.google.code.morphia.query.Criteria;
import com.google.code.morphia.query.CriteriaContainer;
import com.google.code.morphia.query.Query;

import it.amattioli.dominate.specifications.Assembler;

public class MorphiaAssembler<T> implements Assembler {
	private Query<T> query;
	private boolean negated = false;
	private Stack<CriteriaContainer> stackedJunctions = new Stack<CriteriaContainer>();
	private CriteriaContainer currentJunction;
	
	public MorphiaAssembler(Query<T> query) {
		this.query = query;
		currentJunction = query.and();
	}

	public Query<T> getQuery() {
		return query;
	}
	
	public Query<T> getAssembledQuery() {
		query.and(currentJunction);
		return query;
	}
	
	public void addCriteria(Criteria crit) {
		currentJunction.add(crit);
	}
	
	public void startConjunction() {
		stackedJunctions.push(currentJunction);
		currentJunction = query.and();
	}
	
	public void endConjunction() {
		endJunction();
	}
	
	private void endJunction() {
		Criteria toBeAdded = currentJunction;
		if (negated) {
			//toBeAdded = Restrictions.not(toBeAdded);
		}
		stackedJunctions.peek().add(toBeAdded);
		currentJunction = stackedJunctions.pop();
	}

	public void startDisjunction() {
		stackedJunctions.push(currentJunction);
		currentJunction = query.or();
	}
	
	public void endDisjunction() {
		endJunction();
	}

	public void startNegation() {
		negated = true;
	}

	public void endNegation() {
		negated = false;
	}

	public void noResults() {
		getQuery().where("1 == 0");
	}

}
