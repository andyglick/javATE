package it.amattioli.encapsulate.range.specifications;

import static it.amattioli.dominate.hibernate.specifications.HqlUtils.hqlPropertyName;
import static it.amattioli.dominate.hibernate.specifications.HqlUtils.normalizedPropertyName;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.hibernate.specifications.HqlAssembler;
import it.amattioli.encapsulate.range.Range;

public class HqlComparableSpecification<T extends Entity<?>, N extends Comparable<? super N>> extends ComparableSpecification<T, N> {
	private static final String LOW = "Low";
    private static final String HIGH = "High";
	private String alias;
	
	public HqlComparableSpecification() {
		
	}
	
	public HqlComparableSpecification(String propertyName) {
		super(propertyName);
	}

	public HqlComparableSpecification(String propertyName, String alias) {
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
	        Range<N> param = getValue();
	        if (param.isLowBounded()) {
		        if (alias != null) {
		        	assembler.append(alias).append(".");
		        } else {
	            	assembler.append(assembler.getAliasPrefix());
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
	            	assembler.append(assembler.getAliasPrefix());
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
	    	Range<N> param = getValue();
	    	if (param.isLowBounded()) {
	    		assembler.addParameter(normalizedPropertyName(getPropertyName())+LOW, param.getLow());
	    	}
	    	if (param.isHighBounded()) {
	    		assembler.addParameter(normalizedPropertyName(getPropertyName())+HIGH, param.getHigh());
	    	}
		}
    }
}
