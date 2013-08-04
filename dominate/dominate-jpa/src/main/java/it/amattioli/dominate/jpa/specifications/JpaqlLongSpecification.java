package it.amattioli.dominate.jpa.specifications;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.LongSpecification;

import static it.amattioli.dominate.jpa.specifications.JpaqlUtils.*;

public class JpaqlLongSpecification<T extends Entity<?>> extends LongSpecification<T> {
	private String alias;
	
	public JpaqlLongSpecification() {

	}
	
	public JpaqlLongSpecification(String propertyName) {
		super(propertyName);
	}
	
	public JpaqlLongSpecification(String propertyName, String alias) {
		super(propertyName);
		this.alias = alias;
	}
	
	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		addJpaqlCondition((JpaqlAssembler)assembler);
		setJpaqlParam((JpaqlAssembler)assembler);
	}
	
	public boolean itselfSupportsAssembler(Assembler assembler) {
		return assembler instanceof JpaqlAssembler;
	}

	public void addJpaqlCondition(JpaqlAssembler assembler) {
		if (wasSet()) {
	        assembler.newCriteria();
	        if (alias != null) {
	        	assembler.append(alias).append(".");
	        } else {
            	assembler.append(assembler.getAliasPrefix());
            }
	        assembler.append(jpaqlPropertyName(getPropertyName()))
	              .append(" " + getJpaqlOperator() + " :")
	              .append(normalizedPropertyName(getPropertyName()))
	              .append(" ");
		}
    }
	
	public void setJpaqlParam(JpaqlAssembler assembler) {
		if (wasSet()) {
			assembler.addParameter(normalizedPropertyName(getPropertyName()), getValue());
		}
    }
	
	private String getJpaqlOperator() {
		return JpaqlUtils.getTotalOrderOperator(getComparisonType());
	}

}
