package it.amattioli.dominate.jcr;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.Specification;
import it.amattioli.dominate.repositories.Order;
import it.amattioli.dominate.repositories.OrderList;
import it.amattioli.dominate.sessions.SessionManagerRegistry;
import it.amattioli.dominate.specifications.ObjectSpecification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.jackrabbit.ocm.manager.ObjectContentManager;
import org.apache.jackrabbit.ocm.query.Query;
import org.apache.jackrabbit.ocm.query.QueryManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JcrRepository<I extends Serializable,T extends Entity<I>> implements Repository<I,T> {
	private static final Logger logger = LoggerFactory.getLogger(JcrRepository.class);
	private int first;
    private int last;
    private OrderList orders = new OrderList();
    private Class<T> entityClass;
	private SessionManagerRegistry sessionManagerRegistry = new SessionManagerRegistry();

	public JcrRepository(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	private ObjectContentManager getObjectContentManager() {
		return sessionManagerRegistry.currentSessionManager().getSession(ObjectContentManager.class);
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

    public void addOrder(String property, boolean reverse) {
    	orders.add(property,reverse);
	}

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

	public T get(I id) {
		return (T)getObjectContentManager().getObject(entityClass, (String)id);
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

	public void put(T object) {
		if (getObjectContentManager().objectExists((String)object.getId())) {
			getObjectContentManager().update(object);
		} else {
			getObjectContentManager().insert(object);
		}
	}

	public void refresh(I arg0) {
		// TODO Auto-generated method stub
		
	}

	public void refresh(T arg0) {
		// TODO Auto-generated method stub
		
	}

	public boolean isRemoveAllowed() {
		return true;
	}
	
	public void remove(I id) {
		getObjectContentManager().remove(get(id));
	}

	public void remove(T entity) {
		getObjectContentManager().remove(entity);
	}
	
	@Override
	public List<T> list() {
		ObjectContentManager ocm = getObjectContentManager();
		QueryManager mgr = ocm.getQueryManager();
		org.apache.jackrabbit.ocm.query.Filter filter = mgr.createFilter(entityClass);
		Query query = mgr.createQuery(filter);
		addOrder(query);
		logger.debug("Jcr Query: "+mgr.buildJCRExpression(query));
		return new ArrayList<T>(ocm.getObjects(query));
	}

	@Override
	public List<T> list(Specification<T> spec) {
		ObjectContentManager ocm = getObjectContentManager();
		QueryManager mgr = ocm.getQueryManager();
		JcrAssembler asm = new JcrAssembler(mgr, entityClass);
		spec.assembleQuery(asm);
		Query query = asm.getAssembledQuery();
		addOrder(query);
		logger.debug("Jcr Query: "+mgr.buildJCRExpression(query));
		return new ArrayList<T>(ocm.getObjects(query));
	}
	
	public void addOrder(Query query) {
		for (Order order: orders.getOrders()) {
			if (order.isReverse()) {
				query.addOrderByDescending(getOrderProperty());
			} else {
				query.addOrderByAscending(getOrderProperty());
			}
		}
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
