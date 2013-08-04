package it.amattioli.dominate.repositories;

public class Order {
	private String property;
	private boolean reverse;
	
	public Order(String property, boolean reverse) {
		this.property = property;
		this.reverse = reverse;
	}

	public String getProperty() {
		return property;
	}

	public boolean isReverse() {
		return reverse;
	}
}
