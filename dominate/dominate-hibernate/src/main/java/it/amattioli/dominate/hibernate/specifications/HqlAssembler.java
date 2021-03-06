package it.amattioli.dominate.hibernate.specifications;

import it.amattioli.dominate.repositories.Order;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.dflt.PredicateAssembler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import org.apache.commons.collections.Predicate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HqlAssembler implements Assembler {
	private static final Logger logger = LoggerFactory.getLogger(HqlAssembler.class);
	private static final String WHERE = " where ";
	private static final String AND = " and ";
	private static final String OR = " or ";
	private static final String ALIAS = "entity_alias";
	private String initialHqlQuery;
	private StringBuilder joinsBuilder;
	private Stack<String> stackedJunctions = new Stack<String>();
	private String currentJunction = AND;
	private Collection<ParameterSetter> paramSetters = new ArrayList<ParameterSetter>();
	private PredicateAssembler additional;
	private List<String> toBeAppended = new ArrayList<String>();
	private Stack<Integer> stackedPositions = new Stack<Integer>();
	private List<Order> orders = new ArrayList<Order>();
	private boolean useAlias;
	
	public HqlAssembler(String hqlQuery, List<Order> orders) {
		this(hqlQuery,orders,true);
	}
	
	public HqlAssembler(String hqlQuery, List<Order> orders, boolean useAlias) {
		initialHqlQuery = hqlQuery;
		joinsBuilder = new StringBuilder("");
//		whereClauseBuilder = new StringBuilder("");
		this.orders = orders;
		this.useAlias = useAlias;
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
	
	private void push(String junction) {
		toBeAppended.add("(");
		stackedJunctions.push(currentJunction);
		currentJunction = junction;
		stackedPositions.push(toBeAppended.size()-1);
	}
	
	private void pop() {
		if (!nothingToAppend()) {
			toBeAppended.add(")");
		} else {
			int lastPosition = stackedPositions.peek();
			while(toBeAppended.size() > lastPosition) {
				toBeAppended.remove(toBeAppended.size() - 1);
			}
		}
		stackedPositions.pop();
		currentJunction = stackedJunctions.pop();
		removeUnneeded();
	}

	private boolean nothingToAppend() {
		int lastPosition = stackedPositions.peek();
		for (String curr: toBeAppended.subList(lastPosition, toBeAppended.size())) {
			if (!"(".equals(curr) && !" not ".equals(curr)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void startNegation() {
		newCriteria();
		toBeAppended.add(" not ");
		push("");
		stackedPositions.push(stackedPositions.pop() - 1);
	}
	
	@Override
	public void endNegation() {
		pop();
	}

	@Override
	public void startConjunction() {
		newCriteria();
		push(AND);
	}
	
	@Override
	public void endConjunction() {
		pop();
	}
	
	@Override
	public void startDisjunction() {
		newCriteria();
		push(OR);
	}
	
	@Override
	public void endDisjunction() {
		pop();
	}

	public void newCriteria() {
		if (toBeAppended.isEmpty()) {
			toBeAppended.add(WHERE);
        } else if (!nothingToAppend()) {
        	toBeAppended.add(currentJunction);
        }
	}
	
	public HqlAssembler append(String hqlFragment) {
		toBeAppended.add(hqlFragment);
		return this;
	}
	
	public void addParameter(final String name, final Object value) {
		paramSetters.add(new ParameterSetter() {

			@Override
			public void setParameter(Query query) {
				query.setParameter(name, value);
			}
			
		});
	}
	
	public void addParameter(ParameterSetter setter) {
		paramSetters.add(setter);
	}
	
	public String assembledHqlQueryString() {
		StringBuilder result = new StringBuilder("");
		if (joinsBuilder.length() != 0) {
			result.append("select ").append(getAlias()).append(" ");
		}
		result.append(initialHqlQuery);
		if (useAlias) {
			result.append(" ").append(getAlias()).append(" ");
		}
		if (joinsBuilder.length() != 0) {
			result.append(joinsBuilder.toString()).append(" ");
		}
		appendWhereAndOrder(result);
		logger.debug("Assembled HQL query: ",result);
		return result.toString();
	}
	
	private void appendWhereAndOrder(StringBuilder result) {
		removeUnneeded();
		StringBuilder whereClauseBuilder = new StringBuilder();
		for (String curr: toBeAppended) {
			whereClauseBuilder.append(curr);
		}
		result.append(whereClauseBuilder.toString()).append(getOrderClause());
	}

	private void removeUnneeded() {
		ArrayList<String> toBeRemoved = new ArrayList<String>() {{ add(currentJunction); add(WHERE);}};
		if (!toBeAppended.isEmpty() && toBeRemoved.contains(toBeAppended.get(toBeAppended.size() - 1))) {
			toBeAppended.remove(toBeAppended.size() - 1);
		}
	}
	
	private String assembledHqlWhereAndOrder() {
		StringBuilder result = new StringBuilder("");
		appendWhereAndOrder(result);
		logger.debug("Assembled HQL query: ",result);
		return result.toString();
	}
	
	public Query assembledHqlQuery(Session session) {
		Query result = session.createQuery(assembledHqlQueryString());
		for (ParameterSetter setter : paramSetters) {
			setter.setParameter(result);
		}
		return result;
	}
	
	public Query assembledHqlFilter(Session session, Collection<?> content) {
		Query result = session.createFilter(content, assembledHqlWhereAndOrder());
		for (ParameterSetter setter : paramSetters) {
			setter.setParameter(result);
		}
		return result;
	}
	
	@Override
	public void noResults() {
		newCriteria();
		append("0 = 1");
	}

	public static interface ParameterSetter {
		public void setParameter(Query query);
	}

	public String getAlias() {
		return ALIAS;
	}
	
	public void addJoin(String join) {
		if (joinsBuilder.length() != 0) {
			joinsBuilder.append(" ");
		}
		joinsBuilder.append(join);
	}
	
	public String getAliasPrefix() {
		return useAlias ? getAlias() + "." : "";
	}
	
	private String getOrderClause() {
		StringBuilder builder = new StringBuilder("");
		for (Order order: orders) {
			if (builder.length() > 0) {
				builder.append(", ");
			} else {
				builder.append("order by ");
			}
			builder.append(getAliasPrefix() + order.getProperty() + (order.isReverse() ? " desc" : " asc"));
		}
		return builder.toString();
    }
	
}
