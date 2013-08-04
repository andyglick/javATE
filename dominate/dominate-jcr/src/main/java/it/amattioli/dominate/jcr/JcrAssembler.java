package it.amattioli.dominate.jcr;

import java.util.Stack;

import org.apache.jackrabbit.ocm.query.Filter;
import org.apache.jackrabbit.ocm.query.Query;
import org.apache.jackrabbit.ocm.query.QueryManager;

import it.amattioli.dominate.specifications.Assembler;

public class JcrAssembler implements Assembler {
	private QueryManager queryMgr;
	private Class<?> entityClass;
	private boolean negated = false;
	private Stack<Junction> stackedJunctions = new Stack<Junction>();
	private Junction currentJunction;
	
	public JcrAssembler(QueryManager queryMgr, Class<?> entityClass) {
		this.queryMgr = queryMgr;
		this.entityClass = entityClass;
		currentJunction = new Conjunction(queryMgr.createFilter(entityClass));
	}
	
	public Filter createFilter() {
		Filter filter = queryMgr.createFilter(entityClass);
		return filter;
	}
	
	public void addFilter(Filter filter) {
		currentJunction.addFilter(filter);
	}
	
	public Query getAssembledQuery() {
		return queryMgr.createQuery(currentJunction.getFilter());
	}
	
	public void startConjunction() {
		stackedJunctions.push(currentJunction);
		currentJunction = new Conjunction(queryMgr.createFilter(entityClass));
	}
	
	public void endConjunction() {
		endJunction();
	}
	
	public void startDisjunction() {
		stackedJunctions.push(currentJunction);
		currentJunction = new Disjunction(queryMgr.createFilter(entityClass));
	}
	
	public void endDisjunction() {
		endJunction();
	}
	
	private void endJunction() {
		Filter toBeAdded = currentJunction.getFilter();
		if (negated) {
			//toBeAdded = Restrictions.not(toBeAdded);
		}
		stackedJunctions.peek().addFilter(toBeAdded);
		currentJunction = stackedJunctions.pop();
	}
	
	public void startNegation() {
		negated = true;
	}

	public void endNegation() {
		negated = false;
	}

	public void noResults() {
		Filter filter = createFilter();
		filter.addEqualTo("id", "");
		addFilter(filter);
	}
	
	private static abstract class Junction {
		private Filter filter;
		
		public Junction(Filter filter) {
			this.filter = filter;
		}
		
		public Filter getFilter() {
			return filter;
		}
		
		public abstract void addFilter(Filter filter);
	}
	
	private static class Conjunction extends Junction {
		
		public Conjunction(Filter filter) {
			super(filter);
		}
		
		public void addFilter(Filter filter) {
			getFilter().addAndFilter(filter);
		}
		
	}
	
	private static class Disjunction extends Junction {
		
		public Disjunction(Filter filter) {
			super(filter);
		}
		
		public void addFilter(Filter filter) {
			getFilter().addOrFilter(filter);
		}
		
	}
	
}
