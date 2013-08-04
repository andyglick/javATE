package it.amattioli.dominate.hibernate.specifications;

import org.apache.commons.lang.StringUtils;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.ComparisonType;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.StringSpecification;

import static it.amattioli.dominate.hibernate.specifications.HqlUtils.*;

public class HqlStringSpecification<T extends Entity<?>> extends StringSpecification<T> {
	private String alias;
	
	public HqlStringSpecification() {

	}
	
	public HqlStringSpecification(String propertyName) {
		super(propertyName);
	}
	
	public HqlStringSpecification(String propertyName, String alias) {
		super(propertyName);
		this.alias = alias;
	}
	
	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		addHqlCondition((HqlAssembler)assembler);
		setHqlParam((HqlAssembler)assembler);
	}
	
	@Override
	public boolean itselfSupportsAssembler(Assembler assembler) {
		return assembler instanceof HqlAssembler;
	}

	public void addHqlCondition(HqlAssembler assembler) {
		if (wasSet()) {
            assembler.newCriteria();
            assembler.append("upper(");
            if (alias != null) {
            	assembler.append(alias).append(".");
            } else {
            	assembler.append(assembler.getAliasPrefix());
            }
            assembler.append(hqlPropertyName(getPropertyName()))
                     .append(") like :")
                     .append(normalizedPropertyName(getPropertyName()))
                     .append(" ");
		}
    }
	
	public void setHqlParam(HqlAssembler assembler) {
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
