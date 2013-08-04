package it.amattioli.dominate.hibernate;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.hibernate.specifications.CriteriaAssembler;
import it.amattioli.dominate.hibernate.specifications.HqlAssembler;
import it.amattioli.dominate.util.GenericComparator;
import it.amattioli.dominate.Specification;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;

/**
 * A repository to access all the entities of a certain class persisted
 * with Hibernate.
 * 
 * @param <T> The class of the objects that can be accessed using this repository
 * @param <I> The class of the identifier of the objects that can be accessed using this repository
 * @author a.mattioli
 *
 */
public class ClassHibernateRepository<I extends Serializable, T extends Entity<I>> extends AbstractHibernateRepository<I, T> {
    private Class<T> repositoryClass;
    private boolean dbOrder = true;
    private boolean cacheable = false;

    /**
     * Create a repository for the entities whose class is passed as parameter
     * 
     * @param repositoryClass the class of the entities that can be accessed
     *        using this repository
     */
    public ClassHibernateRepository(final Class<T> repositoryClass) {
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

    @SuppressWarnings("unchecked")
    public T get(final I id) {
        return (T) getHibernateSession().get(getRepositoryClass(), id);
    }

    public void put(final T object) {
        getHibernateSession().saveOrUpdate(object);
    }

    public void refresh(final I objectId) {
        refresh(get(objectId));
    }

    public void refresh(final T object) {
        getHibernateSession().evict(object);
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
		getHibernateSession().delete(object);
	}

	protected org.hibernate.Criteria defaultCriteria() {
        return getHibernateSession().createCriteria(getRepositoryClass());
    }

    public boolean isDbOrder() {
		return dbOrder;
	}

	public void setDbOrder(boolean dbOrder) {
		this.dbOrder = dbOrder;
	}
	
	protected String addOrder(String hqlQuery) {
		if (isDbOrder() && getOrderProperty() != null) {
		    hqlQuery = hqlQuery + " " + getHqlOrder();
		}
		return hqlQuery;
	}
    
    protected void addOrder(final org.hibernate.Criteria crit) {
        if (isDbOrder()) {
        	for (it.amattioli.dominate.repositories.Order order: getOrders()) {
	            if (order.isReverse()) {
	                crit.addOrder(Order.desc(order.getProperty()));
	            } else {
	                crit.addOrder(Order.asc(order.getProperty()));
	            }
        	}
        }
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
        org.hibernate.Criteria crit = defaultCriteria();
        addOrder(crit);
        setCriteriaLimits(crit);
        crit.setCacheable(cacheable);
        List<T> result = crit.list();
        inMemoryOrder(result);
        return result;
    }
    
    private List<T> listByCriteria(Specification<T> spec) {
    	CriteriaAssembler cAssembler = new CriteriaAssembler(defaultCriteria());
    	if (!spec.supportsAssembler(cAssembler)) {
    		return null;
    	}
    	List<T> result;
    	spec.assembleQuery(cAssembler);
    	Criteria crit = cAssembler.assembledCriteria();
    	addOrder(crit);
    	if (!cAssembler.hasAdditionalPredicate()) {
        	setCriteriaLimits(crit);
    	}
        crit.setCacheable(cacheable);
        result = crit.list();
        result = applyAdditionalPredicate(result, cAssembler.additionalPredicate());
        inMemoryOrder(result);
		return result;
    }

	private void setCriteriaLimits(Criteria crit) {
		if (getFirst() != 0) {
		    crit.setFirstResult(getFirst());
		}
		if (getLast() != 0) {
		    crit.setMaxResults(getLast());
		}
	}

    private List<T> listByHql(Specification<T> spec) {
    	List<T> result;
		HqlAssembler hAssembler = new HqlAssembler("from " + getRepositoryClass().getName() + " ", getOrders());
    	if (!spec.supportsAssembler(hAssembler)) {
    		return null;
    	}
		spec.assembleQuery(hAssembler);
		Query query = hAssembler.assembledHqlQuery(getHibernateSession());
		if (!hAssembler.hasAdditionalPredicate()) {
    		setHqlLimits(query);
		}
        query.setCacheable(cacheable);
        result = query.list();
        result = applyAdditionalPredicate(result, hAssembler.additionalPredicate());
        inMemoryOrder(result);
		return result;
    }

	private void setHqlLimits(Query query) {
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
    	List<T> result = listByCriteria(spec);
    	if (result == null) {
    		result = listByHql(spec);
    	}
    	if (result == null) {
    		throw new IllegalArgumentException("Unsupported specification");
    	}
    	return result;
	}

	public boolean isCacheable() {
		return cacheable;
	}

	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}

	@Override
	public String toString() {
		return "ClassHibernateRepository[" + repositoryClass + "]";
	}
}
