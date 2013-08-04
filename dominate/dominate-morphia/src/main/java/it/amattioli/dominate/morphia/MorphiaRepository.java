package it.amattioli.dominate.morphia;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.Specification;
import it.amattioli.dominate.repositories.Order;
import it.amattioli.dominate.repositories.OrderList;
import it.amattioli.dominate.sessions.SessionManagerRegistry;
import it.amattioli.dominate.specifications.ObjectSpecification;

public class MorphiaRepository<I extends Serializable, T extends Entity<I>> implements Repository<I,T>{
	private static final Logger logger = LoggerFactory.getLogger(MorphiaRepository.class);
	private int first;
    private int last;
    private OrderList orders = new OrderList();
    private Class<T> entityClass;
	private SessionManagerRegistry sessionManagerRegistry = new SessionManagerRegistry();
	
	public MorphiaRepository(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	private Datastore getDatastore() {
		return sessionManagerRegistry.currentSessionManager().getSession(Datastore.class);
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
		return getDatastore().get(entityClass, id);
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

	private String getOrderString() {
		StringBuffer orderString = new StringBuffer();
		for (Order curr:orders.getOrders()) {
			if (orderString.length() > 0) {
				orderString.append(",");
			}
			if (curr.isReverse()) {
				orderString.append("-");
			}
			orderString.append(curr.getProperty());
		}
		return orderString.toString();
	}
	
	private void setLimitsOnQuery(Query q) {
		if (getFirst() > 0) {
			q.offset(getFirst());
		}
		if (getLast() > 0) {
			q.limit(getLast() - getFirst() + 1);
		}
	}
	
	public List<T> list() {
		Query<T> query = getDatastore().createQuery(entityClass);
		query.order(getOrderString());
		setLimitsOnQuery(query);
		logger.debug(query.toString());
		return query.asList();
	}

	public List<T> list(Specification<T> spec) {
		Query<T> query = getDatastore().createQuery(entityClass);
		MorphiaAssembler<T> asm = new MorphiaAssembler<T>(query);
		spec.assembleQuery(asm);
		query = asm.getAssembledQuery();
		query.order(getOrderString());
		setLimitsOnQuery(query);
		logger.debug(query.toString());
		return query.asList();
	}

	public void put(T object) {
		getDatastore().save(object);
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
		getDatastore().delete(entityClass, id);
	}

	public void remove(T entity) {
		getDatastore().delete(entity);
	}
	
	public void fillCollection(Collection<? super T>... arg0) {
		throw new UnsupportedOperationException();
	}
	
	public <J extends Serializable, E extends Entity<J>> Repository<J, E> getDetailRepository(Collection<E> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
