package it.amattioli.dominate.specifications;

import it.amattioli.dominate.specifications.dflt.DefaultEnumSpecification;
import it.amattioli.dominate.specifications.dflt.DefaultStringSpecification;

public class MyConjunctionSpec extends ConjunctionSpecification<MyEntity> {
	private StringSpecification<MyEntity> descriptionSpecification = new DefaultStringSpecification<MyEntity>("description");
	private EnumSpecification<MyEntity, MyEnum> enumSpecification = new DefaultEnumSpecification<MyEntity, MyEnum>("enumProperty", null);
	
	public MyConjunctionSpec() {
		addSpecification(descriptionSpecification);
		addSpecification(enumSpecification);
	}
	
	public void setDescription(String description) {
		descriptionSpecification.setValue(description);
	}
	
	public String getDescription() {
		return descriptionSpecification.getValue();
	}
	
	public void setEnumProperty(MyEnum value) {
		enumSpecification.setValue(value);
	}
	
	public MyEnum getEnumProperty() {
		return enumSpecification.getValue();
	}

}
