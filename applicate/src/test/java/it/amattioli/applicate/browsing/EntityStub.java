package it.amattioli.applicate.browsing;

import java.util.ArrayList;
import java.util.Collection;

import it.amattioli.dominate.EntityImpl;

public class EntityStub extends EntityImpl {
	private String property1;
	private String property2;
	private Collection<DetailEntityStub> details = new ArrayList<DetailEntityStub>();

	public String getProperty1() {
		return property1;
	}

	public void setProperty1(String property1) {
		this.property1 = property1;
	}

	public String getProperty2() {
		return property2;
	}

	public void setProperty2(String property2) {
		this.property2 = property2;
	}

	public Collection<DetailEntityStub> getDetails() {
		return details;
	}

	public void setDetails(Collection<DetailEntityStub> details) {
		this.details = details;
	}
}
