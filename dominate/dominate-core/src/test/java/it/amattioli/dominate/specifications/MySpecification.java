package it.amattioli.dominate.specifications;

import it.amattioli.dominate.specifications.AbstractSpecification;
import it.amattioli.dominate.specifications.StringSpecification;

public class MySpecification extends AbstractSpecification<MyEntity> {
	private StringSpecification<MyEntity> descriptionSpecification = StringSpecification.newInstance("description");

	@Override
	public void assembleQuery(Assembler assembler) {
		descriptionSpecification.assembleQuery(assembler);
	}

	@Override
	public boolean isSatisfiedBy(MyEntity entity) {
		return entity != null && descriptionSpecification.isSatisfiedBy(entity);
	}

	@Override
	public boolean supportsAssembler(Assembler assembler) {
		return descriptionSpecification.supportsAssembler(assembler);
	}

	public void setDescription(String description) {
		descriptionSpecification.setValue(description);
	}
	
	public String getDescription() {
		return descriptionSpecification.getValue();
	}
	
	public boolean wasSet() {
		return descriptionSpecification.wasSet();
	}
}
