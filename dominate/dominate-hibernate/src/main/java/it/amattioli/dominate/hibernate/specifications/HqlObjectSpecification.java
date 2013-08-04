package it.amattioli.dominate.hibernate.specifications;

import org.hibernate.Query;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.ObjectSpecification;

import static it.amattioli.dominate.hibernate.specifications.HqlUtils.*;

public class HqlObjectSpecification<T extends Entity<?>> extends ObjectSpecification<T> {
	private String alias;
	
	public HqlObjectSpecification() {

	}
	
	public HqlObjectSpecification(String propertyName) {
		super(propertyName);
	}

	public HqlObjectSpecification(String propertyName, String alias) {
		this(propertyName);
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
	                 .append(" = :")
	                 .append(normalizedPropertyName(getPropertyName()))
	                 .append(" ");
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
