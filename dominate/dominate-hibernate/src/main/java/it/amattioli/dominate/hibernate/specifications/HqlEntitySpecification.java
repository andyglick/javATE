package it.amattioli.dominate.hibernate.specifications;

import java.io.Serializable;

import org.hibernate.Query;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.EntitySpecification;

import static it.amattioli.dominate.hibernate.specifications.HqlUtils.*;

public class HqlEntitySpecification<T extends Entity<?>, I extends Serializable, U extends Entity<I>> extends EntitySpecification<T, I, U> {
	private String alias;
	
	public HqlEntitySpecification() {
		
	}
	
	public HqlEntitySpecification(String propertyName, Class<U> entityClass) {
		super(propertyName, entityClass);
	}

	public HqlEntitySpecification(String propertyName, Class<U> enumClass, String alias) {
		this(propertyName, enumClass);
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
