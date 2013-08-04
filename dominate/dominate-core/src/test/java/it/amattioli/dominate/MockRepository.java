package it.amattioli.dominate;

import java.util.List;

public class MockRepository implements Repository<Long, EntityImpl> {

	@Override
	public EntityImpl getByPropertyValue(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public EntityImpl get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFirst() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLast() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getOrderProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isReverseOrder() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EntityImpl> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EntityImpl> list(Specification<EntityImpl> spec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(EntityImpl object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(EntityImpl object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(Long objectId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFirst(int first) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLast(int last) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOrder(String property, boolean reverse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addOrder(String property, boolean reverse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeLastOrder() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRemoveAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void remove(EntityImpl object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Long objectId) {
		// TODO Auto-generated method stub
		
	}

}
