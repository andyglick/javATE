package it.amattioli.dominate.hibernate.specifications;

import static it.amattioli.dominate.hibernate.specifications.HqlUtils.hqlPropertyName;
import static it.amattioli.dominate.hibernate.specifications.HqlUtils.normalizedPropertyName;

import org.hibernate.Query;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.CollectionSpecification;

public class HqlCollectionSpecification<T extends Entity<?>> extends CollectionSpecification<T> {

	public HqlCollectionSpecification() {
		super();
	}

	public HqlCollectionSpecification(String propertyName) {
		super(propertyName);
	}

	@Override
	public boolean itselfSupportsAssembler(Assembler assembler) {
		return assembler instanceof HqlAssembler;
	}

	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		addHqlCondition((HqlAssembler)assembler);
		setHqlParam((HqlAssembler)assembler);
	}
	
	public void addHqlCondition(HqlAssembler assembler) {
		if (wasSet()) {	
			HqlAssembler hqlAssembler = (HqlAssembler)assembler;
			hqlAssembler.newCriteria();
			hqlAssembler.append(":" + normalizedPropertyName(getPropertyName()) + " in elements");
			hqlAssembler.append("("+hqlPropertyName(getPropertyName())+")");
		}
	}
	
	public void setHqlParam(HqlAssembler assembler) {
		if (wasSet()) {
	    	assembler.addParameter(new HqlAssembler.ParameterSetter() {
	
				@Override
				public void setParameter(Query query) {
					query.setParameter(normalizedPropertyName(getPropertyName()), getValue());
				}
	    		
	    	});
		}
	}

}
