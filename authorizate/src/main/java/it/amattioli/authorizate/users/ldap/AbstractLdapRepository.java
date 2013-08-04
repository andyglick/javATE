package it.amattioli.authorizate.users.ldap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.repositories.Order;

public abstract class AbstractLdapRepository<I extends Serializable,E extends Entity<I>> implements Repository<I,E>{
	protected int first = 0;
	protected int last = -1;
	private List<Order> orders = new ArrayList<Order>();

	public AbstractLdapRepository() {
		super();
	}

	@Override
	public int getFirst() {
		return first;
	}

	@Override
	public void setFirst(int first) {
		this.first = first;
	}

	@Override
	public int getLast() {
		return last;
	}

	@Override
	public void setLast(int last) {
		this.last = last;
	}

	public void setOrder(String property, boolean reverse) {
        orders.clear();
        addOrder(property, reverse);
    }

    @Override
	public void addOrder(String property, boolean reverse) {
    	orders.add(new Order(property,reverse));
	}

	public String getOrderProperty() {
		if (orders.isEmpty()) {
			return null;
		}
        return orders.get(orders.size()-1).getProperty();
    }

    public boolean isReverseOrder() {
    	if (orders.isEmpty()) {
			return false;
		}
        return orders.get(orders.size()-1).isReverse();
    }
    
    protected List<Order> getOrders() {
    	return orders;
    }
    
    @Override
	public void removeLastOrder() {
		if (!orders.isEmpty()) {
			orders.remove(orders.size() - 1);
		}
	}

	@Override
	public void refresh(I objectId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(E object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}