package it.amattioli.dominate.util;

import it.amattioli.dominate.properties.Properties;
import it.amattioli.dominate.repositories.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * A comparator that is able to compare two beans based on the values of one or more
 * properties.
 * 
 * @author andrea
 *
 * @param <T> the class of the beans to be compared
 */
public class MultiPropertyComparator<T> implements Comparator<T> {
    private static final int LESS_THAN = -1;
    private static final int GREATER_THAN = 1;
    private List<Order> orders = new ArrayList<Order>();
    private boolean nullFirst = false;

    public MultiPropertyComparator(final String property) {
        orders.add(new Order(property,false));
    }

    public MultiPropertyComparator(final String property, boolean reverse, final boolean nullFirst) {
    	orders.add(new Order(property, reverse));
        this.nullFirst = nullFirst;
    }
    
    public MultiPropertyComparator(Order... orders) {
    	this(Arrays.asList(orders));
    }
    
    public MultiPropertyComparator(List<Order> orders) {
    	this.orders = new ArrayList<Order>(orders);
    }
    
    private int getSign(int idx) {
    	return orders.get(idx).isReverse() ? -1 : 1;
    }

    private Comparable<?> getProperty(final T o, int idx) {
    	String property = orders.get(idx).getProperty();
        return (Comparable<?>) Properties.get(o, property);
    }

    @SuppressWarnings("unchecked")
    public int compare(final T o1, final T o2) {
    	for (int i = 0; i < orders.size(); i++) {
	        Comparable p1 = getProperty(o1,i);
	        Comparable p2 = getProperty(o2,i);
	        if (p1 == null && p2 != null) {
	            return nullFirst ? LESS_THAN : GREATER_THAN;
	        }
	        if (p1 != null && p2 == null) {
	            return nullFirst ? GREATER_THAN : LESS_THAN;
	        }
	        int result = getSign(i) * p1.compareTo(p2);
	        if (result != 0) {
	        	return result;
	        }
    	}
    	return 0;
    }

}
