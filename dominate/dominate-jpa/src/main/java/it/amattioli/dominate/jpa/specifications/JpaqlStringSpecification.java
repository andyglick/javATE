package it.amattioli.dominate.jpa.specifications;

import org.apache.commons.lang.StringUtils;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.ComparisonType;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.StringSpecification;

import static it.amattioli.dominate.jpa.specifications.JpaqlUtils.*;

public class JpaqlStringSpecification<T extends Entity<?>> extends StringSpecification<T> {
	private String alias;
	
	public JpaqlStringSpecification() {

	}
	
	public JpaqlStringSpecification(String propertyName) {
		super(propertyName);
	}
	
	public JpaqlStringSpecification(String propertyName, String alias) {
		super(propertyName);
		this.alias = alias;
	}
	
	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		addJpaqlCondition((JpaqlAssembler)assembler);
		setJpaqlParam((JpaqlAssembler)assembler);
	}
	
	@Override
	public boolean itselfSupportsAssembler(Assembler assembler) {
		return assembler instanceof JpaqlAssembler;
	}

	public void addJpaqlCondition(JpaqlAssembler assembler) {
		if (wasSet()) {
            assembler.newCriteria();
            assembler.append("upper(");
            if (alias != null) {
            	assembler.append(alias).append(".");
            } else {
            	assembler.append(assembler.getAliasPrefix());
            }
            assembler.append(jpaqlPropertyName(getPropertyName()))
                     .append(") like :")
                     .append(normalizedPropertyName(getPropertyName()))
                     .append(" ");
		}
    }
	
	public void setJpaqlParam(JpaqlAssembler assembler) {
		if (wasSet()) {
        	String param = StringUtils.upperCase(getValue());
        	if (ComparisonType.CONTAINS.equals(getComparisonType())) {
        		param = "%"+param;
        	}
        	if (ComparisonType.CONTAINS.equals(getComparisonType()) || ComparisonType.STARTS.equals(getComparisonType())) {
        		param = param+"%";
        	}
            assembler.addParameter(normalizedPropertyName(getPropertyName()), param);
		}
    }

}
