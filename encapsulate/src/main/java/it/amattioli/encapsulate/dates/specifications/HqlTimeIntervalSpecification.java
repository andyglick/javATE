package it.amattioli.encapsulate.dates.specifications;

import java.util.Date;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.hibernate.specifications.HqlAssembler;
import it.amattioli.encapsulate.dates.TimeInterval;

import static it.amattioli.dominate.hibernate.specifications.HqlUtils.*;

public class HqlTimeIntervalSpecification<T extends Entity<?>> extends TimeIntervalSpecification<T> {
	private static final String LOW = "Low";
    private static final String HIGH = "High";
	private String alias;
	
	public HqlTimeIntervalSpecification() {

	}
	
	public HqlTimeIntervalSpecification(TimeIntervalSpecification<T> chained) {
		super(chained);
	}

	public HqlTimeIntervalSpecification(String alias) {
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
	        if (alias != null) {
	        	assembler.append(alias).append(".");
	        }
	        if (getIncludedValue() != null) {
		        assembler.append(hqlPropertyName(getPropertyName()+"."+LOW))
		        .append(" <= :")
		        .append(normalizedPropertyName(getPropertyName()))
		        .append(" and ")
		        .append(hqlPropertyName(getPropertyName()+"."+HIGH))
		        .append(" > :")
		        .append(normalizedPropertyName(getPropertyName()))
		        .append(")");
	        } else {
	        	assembler.append(hqlPropertyName(getPropertyName()+"."+LOW))
		        .append(" >= :")
		        .append(normalizedPropertyName(getPropertyName()+LOW))
		        .append(" and ")
		        .append(hqlPropertyName(getPropertyName()+"."+HIGH))
		        .append(" <= :")
		        .append(normalizedPropertyName(getPropertyName()+HIGH))
		        .append(")");
	        }
		}
    }
	
	public void setHqlParam(HqlAssembler assembler) {
		if (wasSet()) {
			if (getIncludedValue() != null) {
		    	Date param = getIncludedValue();
		    	assembler.addParameter(normalizedPropertyName(getPropertyName()), param);
			} else {
				TimeInterval param = getIncludingInterval();
				assembler.addParameter(normalizedPropertyName(getPropertyName()+LOW), param.getLow());
				assembler.addParameter(normalizedPropertyName(getPropertyName()+HIGH), param.getHigh());
			}
		}
    }

}
