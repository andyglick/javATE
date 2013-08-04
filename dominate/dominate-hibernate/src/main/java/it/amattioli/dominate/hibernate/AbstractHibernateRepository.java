package it.amattioli.dominate.hibernate;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.repositories.Order;
import it.amattioli.dominate.repositories.OrderList;
import it.amattioli.dominate.sessions.SessionManager;
import it.amattioli.dominate.specifications.ObjectSpecification;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

/**
 * Abstract class that implements basic methods for a repository whose content
 * is persisted on a relational database using Hibernate.
 * <p>That sort of repository needs a {@link Session} object to do its job.
 * To retrieve such an object it will use a {@link HibernateSessionManager}
 * that must be injected in the repository. After the injection the repository
 * can use {@link #getHibernateSession()} to retrieve the session from the
 * session manager. If no {@link HibernateSessionManager} has been injected
 * the {@link SessionManagerRegistry} will be used to get one.</p>
 *
 * @author a.mattioli
 *
 */
public abstract class AbstractHibernateRepository<I extends Serializable, T extends Entity<I>> implements Repository<I, T> {
    private int first;
    private int last;
    private OrderList orders = new OrderList();
    private SessionManager sessionMgr;
	private it.amattioli.dominate.sessions.SessionManagerRegistry sessionManagerRegistry = new it.amattioli.dominate.sessions.SessionManagerRegistry();

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
     * Builds the "order by" clause of an HQL query using the informations set
     * with {@link #setOrder(String, boolean)}.
     * Subclasses can use this method in {@link Repository#list()} and
     * {@link Repository#list(it.maticasrl.appctrl.persistence.Filter)} if they
     * need to implement them using HQL. 
     *
     * @return the "order by" clause of a HQL query
     */
    protected String getHqlOrder() {
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

    /**
     * Retrieves a Hibernate session 
     *
     * @return a Hibernate session
     */
    public Session getHibernateSession() {
        return getSessionManager().getSession(Session.class);
    }

    @Override
    protected AbstractHibernateRepository<I,T> clone() throws CloneNotSupportedException {
        return (AbstractHibernateRepository<I,T>)super.clone();
    }
    
    public T getByPropertyValue(String propertyName, Object value) {
    	ObjectSpecification<T> spec = ObjectSpecification.newInstance(propertyName);
    	spec.setValue(value);
    	List<T> all = list(spec);
    	if (all.isEmpty()) {
    		return null;
    	} else {
    		return all.get(0);
    	}
    }

}
