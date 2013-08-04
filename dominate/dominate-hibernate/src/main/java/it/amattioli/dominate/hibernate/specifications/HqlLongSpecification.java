package it.amattioli.dominate.hibernate.specifications;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.LongSpecification;

import static it.amattioli.dominate.hibernate.specifications.HqlUtils.*;

public class HqlLongSpecification<T extends Entity<?>> extends LongSpecification<T> {
	private String alias;
	
	public HqlLongSpecification() {

	}
	
	public HqlLongSpecification(String propertyName) {
		super(propertyName);
	}
	
	public HqlLongSpecification(String propertyName, String alias) {
		super(propertyName);
		this.alias = alias;
	}
	
	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		addHqlCondition((HqlAssembler)assembler);
		setHqlParam((HqlAssembler)assembler);
	}
	
	public boolean itselfSupportsAssembler(Assembler assembler) {
		return assembler instanceof HqlAssembler;
	}

	public void addHqlCondition(HqlAssembler assembler) {
		if (wasSet()) {
	        assembler.newCriteria();
	        if (alias != null) {
	        	assembler.append(alias).append(".");
	        } else {
            	assembler.append(assembler.getAliasPrefix());
            }
	        assembler.append(hqlPropertyName(getPropertyName()))
	              .append(" " + getHqlOperator() + " :")
	              .append(normalizedPropertyName(getPropertyName()))
	              .append(" ");
		}
    }
	
	public void setHqlParam(HqlAssembler assembler) {
		if (wasSet()) {
			assembler.addParameter(normalizedPropertyName(getPropertyName()), getValue());
		}
    }
	
	private String getHqlOperator() {
		return HqlUtils.getTotalOrderOperator(getComparisonType());
	}

}
