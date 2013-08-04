package it.amattioli.encapsulate.money.specifications;

import static it.amattioli.dominate.jpa.specifications.JpaqlUtils.jpaqlPropertyName;
import static it.amattioli.dominate.jpa.specifications.JpaqlUtils.normalizedPropertyName;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.jpa.specifications.JpaqlAssembler;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.encapsulate.money.Money;
import it.amattioli.encapsulate.range.Range;

public class JpaqlMoneySpecification<T extends Entity<?>> extends MoneySpecification<T> {
	private static final String LOW = "Low";
    private static final String HIGH = "High";
	private String alias;
	
	public JpaqlMoneySpecification() {
		
	}
	
	public JpaqlMoneySpecification(String propertyName) {
		super(propertyName);
	}

	public JpaqlMoneySpecification(String propertyName, String alias) {
		super(propertyName);
		this.alias = alias;
	}
	
	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		addHqlCondition((JpaqlAssembler)assembler);
		setHqlParam((JpaqlAssembler)assembler);
	}
	
	@Override
	public boolean itselfSupportsAssembler(Assembler assembler) {
		try {
			return assembler instanceof JpaqlAssembler;
		} catch(Throwable e) {
			return false;
		}
	}

	public void addHqlCondition(JpaqlAssembler assembler) {
		if (wasSet()) {
	        assembler.newCriteria();
	        Range<Money> param = getValue();
	        if (param.isLowBounded()) {
		        if (alias != null) {
		        	assembler.append(alias).append(".");
		        } else {
	            	assembler.append(assembler.getAliasPrefix());
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
	            	assembler.append(assembler.getAliasPrefix());
	            }
		        assembler.append(jpaqlPropertyName(getPropertyName()))
		        .append(" < :")
		        .append(normalizedPropertyName(getPropertyName())).append(HIGH)
		        .append(" ");
	        }
		}
    }
	
	public void setHqlParam(JpaqlAssembler assembler) {
		if (wasSet()) {
	    	Range<Money> param = getValue();
	    	if (param.isLowBounded()) {
	    		assembler.addParameter(normalizedPropertyName(getPropertyName())+LOW, param.getLow());
	    	}
	    	if (param.isHighBounded()) {
	    		assembler.addParameter(normalizedPropertyName(getPropertyName())+HIGH, param.getHigh());
	    	}
		}
    }
}
