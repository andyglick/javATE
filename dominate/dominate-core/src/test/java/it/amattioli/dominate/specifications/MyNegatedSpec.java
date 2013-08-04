package it.amattioli.dominate.specifications;

import it.amattioli.dominate.specifications.dflt.DefaultStringSpecification;

public class MyNegatedSpec extends NegatedSpecification<MyEntity> {
	private StringSpecification<MyEntity> descriptionSpecification = new DefaultStringSpecification<MyEntity>("description");
	
	public MyNegatedSpec() {
		setSpecification(descriptionSpecification);
	}
	
	public void setDescription(String description) {
		descriptionSpecification.setValue(description);
	}
	
	public String getDescription() {
		return descriptionSpecification.getValue();
	}

}
