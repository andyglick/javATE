package it.amattioli.dominate.memory;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.repositories.Order;
import it.amattioli.dominate.repositories.OrderList;
import it.amattioli.dominate.specifications.ObjectSpecification;
import it.amattioli.dominate.specifications.dflt.PredicateAssembler;
import it.amattioli.dominate.util.MultiPropertyComparator;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.Specification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.map.ListOrderedMap;

public class MemoryRepository<I extends Serializable,T extends Entity<I>> implements Repository<I,T> {
	@SuppressWarnings("unchecked") private Map<I,T> content = new ListOrderedMap();
    private int first = 0;
    private int last = -1;
    private OrderList orders = new OrderList();
    
    public MemoryRepository() {
    }
    
    public MemoryRepository(Collection<T> init) {
    	for (T curr: init) {
            content.put(curr.getId(),curr);
        }
    }
    
    protected MemoryRepository(Map<I,T> content) {
    	this.content = content;
    }
    
    public T get(I id) {
        return content.get(id);
    }
    
    private I getNewId() {
    	/*
        Long max = null;
        for (Iterator iter = content.keySet().iterator(); iter.hasNext();) {
            Long curr = (Long)iter.next();
            if (max == null || curr.compareTo(max)>0) {
                max = curr;
            }
        }
        if (max == null) max = new Long(-1);
        return new Long(max.longValue()+1);
        */
    	return null; //TODO: to be developed
    }

    public void put(T object) {
        if (object.getId() == null) {
            object.setId(getNewId());
        }
        content.put(object.getId(),object);
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
		content.remove(objectId);
	}

	@Override
	public void remove(T object) {
		if (object != null) {
			remove(object.getId());
		}
	}

	public List<T> list() {
		int firstIdx = first;
        if (firstIdx > content.values().size()) {
        	firstIdx = Math.max(0, content.values().size());
        }
        int lastIdx = last+1;
        if (lastIdx <= 0 || lastIdx > content.values().size()) {
        	lastIdx = content.values().size();
        }
        if (lastIdx < firstIdx) {
        	lastIdx = firstIdx;
        }
        List<T> result = new ArrayList<T>(content.values());
        if (!orders.isEmpty()) {
            Collections.sort(result,new MultiPropertyComparator<T>(getOrders()));
        }
        result = result.subList(firstIdx,lastIdx);
        return result;
    }


    @Override
	public List<T> list(Specification<T> spec) {
    	List<T> result = new ArrayList<T>(content.values());
    	PredicateAssembler assembler = new PredicateAssembler();
    	spec.assembleQuery(assembler);
    	Predicate predicate = assembler.assembledPredicate();
    	CollectionUtils.filter(result,predicate);
        //int first = criteria.getFirst() == 0 ? getFirst() : criteria.getFirst();
    	int first = getFirst();
        //int last = criteria.getLast() == 0 ? getLast() : criteria.getLast();
    	int last = getLast();
        if (!orders.isEmpty()) {
            Collections.sort(result,new MultiPropertyComparator<T>(getOrders()));
        }
        int firstIdx = first;
        if (firstIdx > result.size()) {
        	firstIdx = Math.max(0, result.size());
        }
        int lastIdx = last + 1;
        if (lastIdx <= 0 || lastIdx > result.size()) {
            lastIdx = result.size(); 
        }
        if (lastIdx < firstIdx) {
        	lastIdx = firstIdx;
        }
        result = result.subList(firstIdx,lastIdx);
        return result;
	}

	public void setFirst(int first) {
        this.first = first;
    }
    
    public int getFirst() {
    	return this.first;
    }

    public void setLast(int last) {
        this.last = last;
    }
    
    public int getLast() {
    	return this.last;
    }

    public void setOrder(String property, boolean reverse) {
        orders.clear();
        addOrder(property, reverse);
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

    @Override
    protected MemoryRepository<I,T> clone() throws CloneNotSupportedException {
        return (MemoryRepository<I,T>)super.clone();
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
