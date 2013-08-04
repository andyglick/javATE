package it.amattioli.dominate.jpa;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.Specification;
import it.amattioli.dominate.jpa.specifications.JpaqlAssembler;
import it.amattioli.dominate.jpa.specifications.JpaqlObjectSpecification;
import it.amattioli.dominate.repositories.Order;
import it.amattioli.dominate.repositories.OrderList;
import it.amattioli.dominate.sessions.SessionManager;
import it.amattioli.dominate.sessions.SessionManagerRegistry;
import it.amattioli.dominate.specifications.ObjectSpecification;
import it.amattioli.dominate.util.GenericComparator;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

public class JpaRepository<I extends Serializable, T extends Entity<I>> implements Repository<I, T> {
	private int first;
    private int last;
    private OrderList orders = new OrderList();
    private boolean dbOrder = true;
    private SessionManager sessionMgr;
	private SessionManagerRegistry sessionManagerRegistry = new SessionManagerRegistry();
	private Class<T> repositoryClass;
	
	/**
     * Create a repository for the entities whose class is passed as parameter
     * 
     * @param repositoryClass the class of the entities that can be accessed
     *        using this repository
     */
    public JpaRepository(final Class<T> repositoryClass) {
        this.repositoryClass = repositoryClass;
    }
    
    /**
     * Retrieves the class of the entities that can be accessed using this repository
     *
     * @return the class of the entities that can be accessed using this repository
     */
    public Class<T> getRepositoryClass() {
        return repositoryClass;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getFirst() {
        return first;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public int getLast() {
        return last;
    }

    public void setOrder(final String property, final boolean reverse) {
    	orders.clear();
    	addOrder(property,reverse);
    }

    @Override
	public void addOrder(String property, boolean reverse) {
    	orders.add(property,reverse);
	}

	@Override
	public void removeLastOrder() {
		orders.removeLast();
	}

	public String getOrderProperty() {
		return orders.getLastProperty();
    }

    public boolean isReverseOrder() {
    	return orders.isLastReverse();
    }
    
    protected List<Order> getOrders() {
    	return orders.getOrders();
    }

    /**
     * Builds the "order by" clause of an JPA QL query using the informations set
     * with {@link #setOrder(String, boolean)}.
     * Subclasses can use this method in {@link Repository#list()} and
     * {@link Repository#list(it.maticasrl.appctrl.persistence.Filter)} if they
     * need to implement them using HQL. 
     *
     * @return the "order by" clause of a HQL query
     */
    protected String getJpaqlOrder() {
    	StringBuilder builder = new StringBuilder("");
		for (Order order: getOrders()) {
			if (builder.length() > 0) {
				builder.append(", ");
			} else {
				builder.append("order by ");
			}
			builder.append(order.getProperty() + (order.isReverse() ? " desc" : " asc"));
		}
		return builder.toString();
    }

    /**
     * Set the session manager that will be used by {@link #getHibernateSession()}.
     *
     * @param newSessionMgr the session manager that will be used by {@link #getHibernateSession()}
     */
    public void setSessionManager(final SessionManager newSessionMgr) {
        this.sessionMgr = newSessionMgr;
    }

    /**
     * Retrieves the session manager set by
     * {@link #setSessionManager(HibernateSessionManager)}.
     * If no session manager has been set an instance of {@link SessionManagerRegistry}
     * will be used to get one.
     *
     * @return the session manager to use to get a Hibernate session
     */
    public SessionManager getSessionManager() {
    	if (this.sessionMgr != null) {
    		return this.sessionMgr;
    	} else {
    		return sessionManagerRegistry.currentSessionManager();
    	}
    }

    public EntityManager getEntityManager() {
        return getSessionManager().getSession(EntityManager.class);
    }

    @Override
    protected JpaRepository<I,T> clone() throws CloneNotSupportedException {
        return (JpaRepository<I,T>)super.clone();
    }
    
    public T getByPropertyValue(String propertyName, Object value) {
    	ObjectSpecification<T> spec = new JpaqlObjectSpecification<T>(propertyName);
    	spec.setValue(value);
    	List<T> all = list(spec);
    	if (all == null || all.isEmpty()) {
    		return null;
    	} else {
    		return all.get(0);
    	}
    }

    public T get(final I id) {
        return getEntityManager().find(getRepositoryClass(), id);
    }

    public void put(final T object) {
        getEntityManager().persist(object);
    }

    public void refresh(final I objectId) {
        refresh(get(objectId));
    }

    public void refresh(final T object) {
        getEntityManager().detach(object);
    }

    @Override
	public boolean isRemoveAllowed() {
		return true;
	}

	@Override
	public void remove(I objectId) {
		remove(get(objectId));
	}

	@Override
	public void remove(T object) {
		getEntityManager().remove(object);
	}

    public boolean isDbOrder() {
		return dbOrder;
	}

	public void setDbOrder(boolean dbOrder) {
		this.dbOrder = dbOrder;
	}
	
	protected String addOrder(String hqlQuery) {
		if (isDbOrder() && getOrderProperty() != null) {
		    hqlQuery = hqlQuery + " " + getJpaqlOrder();
		}
		return hqlQuery;
	}
    
    protected void inMemoryOrder(List<T> result) {
    	if (!isDbOrder() && getOrderProperty() != null) {
    		Collections.sort(result,new GenericComparator<T>(getOrderProperty()));
            if (isReverseOrder()) {
                Collections.reverse(result);
            }
    	}
    }
    
    @SuppressWarnings("unchecked")
    public List<T> list() {
        Query query = getEntityManager().createQuery("from " + getRepositoryClass().getName() + " " + getJpaqlOrder());
        setJpaqlLimits(query);
        List<T> result = query.getResultList();
        inMemoryOrder(result);
        return result;
    }

    private void setJpaqlLimits(Query query) {
		if (getFirst() != 0) {
		    query.setFirstResult(getFirst());
		}
		if (getLast() != 0) {
		    query.setMaxResults(getLast());
		}
	}

    private List<T> applyAdditionalPredicate(List<T> result, Predicate additional) {
		if (additional != null) {
            CollectionUtils.filter(result, additional);
            result = result.subList(getFirst(), getLast() == 0 ? result.size() : Math.min(getLast(), result.size()));
        }
		return result;
	}
    
    @Override
	public List<T> list(Specification<T> spec) {
    	List<T> result;
		JpaqlAssembler hAssembler = new JpaqlAssembler("from " + getRepositoryClass().getName() + " ", getOrders());
		if (!spec.supportsAssembler(hAssembler)) {
			return null;
		}
		spec.assembleQuery(hAssembler);
		Query query = hAssembler.assembledJpaqlQuery(getEntityManager());
		if (!hAssembler.hasAdditionalPredicate()) {
			setJpaqlLimits(query);
		}
		result = query.getResultList();
		result = applyAdditionalPredicate(result, hAssembler.additionalPredicate());
		inMemoryOrder(result);
		return result;
	}

	@Override
	public String toString() {
		return "JpaRepository[" + repositoryClass + "]";
	}
}
