package it.amattioli.encapsulate.dates.specifications;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.jpa.specifications.JpaqlAssembler;
import it.amattioli.encapsulate.dates.TimeInterval;

import static it.amattioli.dominate.jpa.specifications.JpaqlUtils.*;

public class JpaqlDateSpecification<T extends Entity<?>> extends DateSpecification<T> {
	private static final String LOW = "Low";
    private static final String HIGH = "High";
	private String alias;
	
	public JpaqlDateSpecification() {

	}
	
	public JpaqlDateSpecification(String propertyName) {
		super(propertyName);
	}

	public JpaqlDateSpecification(String propertyName, String alias) {
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
		try {
			return assembler instanceof JpaqlAssembler;
		} catch(Throwable e) {
			return false;
		}
	}

	public void addJpaqlCondition(JpaqlAssembler assembler) {
		if (wasSet()) {
	        assembler.newCriteria();
	        TimeInterval param = getValue();
	        if (param.isLowBounded()) {
		        if (alias != null) {
		        	assembler.append(alias).append(".");
		        } else {
	            	assembler.append(assembler.getAlias()).append(".");
	            }
		        assembler.append(jpaqlPropertyName(getPropertyName()))
		        .append(" >= :")
		        .append(normalizedPropertyName(getPropertyName())).append(LOW)
		        .append(" ");
	        }
	        if (param.isHighBounded()) {
	        	if (param.isLowBounded()) {
	        		assembler.append("and ");
	        	}
		        if (alias != null) {
		        	assembler.append(alias).append(".");
		        } else {
	            	assembler.append(assembler.getAlias()).append(".");
	            }
		        assembler.append(jpaqlPropertyName(getPropertyName()))
		        .append(" < :")
		        .append(normalizedPropertyName(getPropertyName())).append(HIGH)
		        .append(" ");
	        }
		}
    }
	
	public void setJpaqlParam(JpaqlAssembler assembler) {
		if (wasSet()) {
	    	TimeInterval param = getValue();
	    	if (param.isLowBounded()) {
	    		assembler.addParameter(normalizedPropertyName(getPropertyName())+LOW, param.getLow());
	    	}
	    	if (param.isHighBounded()) {
	    		assembler.addParameter(normalizedPropertyName(getPropertyName())+HIGH, param.getHigh());
	    	}
		}
    }

}
