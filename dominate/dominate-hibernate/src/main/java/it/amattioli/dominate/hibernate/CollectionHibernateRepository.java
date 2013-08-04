package it.amattioli.dominate.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Specification;
import it.amattioli.dominate.hibernate.specifications.HqlAssembler;

public class CollectionHibernateRepository<I extends Serializable,T extends Entity<I>> extends AbstractHibernateRepository<I,T> {
	private Collection<T> content;

    public CollectionHibernateRepository(Collection<T> content) {
        this.content = content;
    }
    
    public T get(I id) {
    	for (T curr : content) {
            if (curr.getId().equals(id)) {
                return curr;
            }
        }
        return null;
    }

    public void put(T object) {
        content.add(object);
    }

    public void refresh(I objectId) {
		// Non fa nulla, il contenuto del repository è fissato in fase di 
    	// costruzione e non può essere riletto
	}

	public void refresh(T object) {
		// Non fa nulla, il contenuto del repository è fissato in fase di 
    	// costruzione e non può essere riletto
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
		content.remove(object);
	}

	@SuppressWarnings("unchecked")
	public List<T> list() {
        String hqlQuery = "";
        if (getOrderProperty() != null) {
            hqlQuery = hqlQuery + getHqlOrder();
        }
        Query query = getHibernateSession().createFilter(content,hqlQuery);
        if (getFirst() != 0) {
        	query.setFirstResult(getFirst());
        }
        if (getLast() != 0) {
        	query.setMaxResults(getLast());
        }
        List<T> result = query.list();
        return result;
    }
	
	@Override
	public List<T> list(Specification<T> spec) {
    	List<T> result;
    	//int first = criteria.getFirst() == 0 ? getFirst() : criteria.getFirst();
    	int first = getFirst();
        //int last = criteria.getLast() == 0 ? getLast() : criteria.getLast();
    	int last = getLast();
		HqlAssembler hAssembler = new HqlAssembler("", getOrders(), false);
    	if (spec.supportsAssembler(hAssembler)) {
    		spec.assembleQuery(hAssembler);
    		Query query = hAssembler.assembledHqlFilter(getHibernateSession(), content);
    		if (first != 0) {
                query.setFirstResult(first);
            }
            if (last != 0) {
                query.setMaxResults(last);
            }
            result = query.list();
    	} else {
    		throw new IllegalArgumentException("Unsupported specification");
    	}
		return result;
	}

}
