package it.amattioli.dominate.repositories;

import java.util.ArrayList;
import java.util.List;

public class OrderList {
	private List<Order> orders = new ArrayList<Order>();
	
	public void clear() {
		orders.clear();
	}
	
	public void add(String property, boolean reverse) {
    	orders.add(new Order(property,reverse));
	}
    
    public void removeLast() {
		if (!isEmpty()) {
			orders.remove(orders.size() - 1);
		}
	}
    
    public String getFirstProperty() {
    	if (isEmpty()) {
			return null;
		}
        return orders.get(0).getProperty();
    }

	public String getLastProperty() {
		if (isEmpty()) {
			return null;
		}
        return orders.get(orders.size()-1).getProperty();
    }
	
	public boolean isFirstReverse() {
		if (isEmpty()) {
			return false;
		}
        return orders.get(0).isReverse();
	}

    public boolean isLastReverse() {
    	if (isEmpty()) {
			return false;
		}
        return orders.get(orders.size()-1).isReverse();
    }
    
    public List<Order> getOrders() {
    	return orders;
    }
    
    public boolean isEmpty() {
    	return orders.isEmpty();
    }
}
