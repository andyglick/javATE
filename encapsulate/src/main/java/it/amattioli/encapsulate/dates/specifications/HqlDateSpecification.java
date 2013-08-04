package it.amattioli.encapsulate.dates.specifications;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.hibernate.specifications.HqlAssembler;
import it.amattioli.encapsulate.dates.TimeInterval;

import static it.amattioli.dominate.hibernate.specifications.HqlUtils.*;

public class HqlDateSpecification<T extends Entity<?>> extends DateSpecification<T> {
	private static final String LOW = "Low";
    private static final String HIGH = "High";
	private String alias;
	
	public HqlDateSpecification() {

	}
	
	public HqlDateSpecification(String propertyName) {
		super(propertyName);
	}

	public HqlDateSpecification(String propertyName, String alias) {
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
		try {
			return assembler instanceof HqlAssembler;
		} catch(Throwable e) {
			return false;
		}
	}

	public void addHqlCondition(HqlAssembler assembler) {
		if (wasSet()) {
	        assembler.newCriteria();
	        TimeInterval param = getValue();
	        if (param.isLowBounded()) {
		        if (alias != null) {
		        	assembler.append(alias).append(".");
		        } else {
	            	assembler.append(assembler.getAlias()).append(".");
	            }
		        assembler.append(hqlPropertyName(getPropertyName()))
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
		        assembler.append(hqlPropertyName(getPropertyName()))
		        .append(" < :")
		        .append(normalizedPropertyName(getPropertyName())).append(HIGH)
		        .append(" ");
	        }
		}
    }
	
	public void setHqlParam(HqlAssembler assembler) {
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
