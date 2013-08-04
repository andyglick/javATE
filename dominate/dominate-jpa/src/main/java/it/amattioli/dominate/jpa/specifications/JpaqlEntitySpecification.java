package it.amattioli.dominate.jpa.specifications;

import java.io.Serializable;

import javax.persistence.Query;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.EntitySpecification;

import static it.amattioli.dominate.jpa.specifications.JpaqlUtils.*;

public class JpaqlEntitySpecification<T extends Entity<?>, I extends Serializable, U extends Entity<I>> extends EntitySpecification<T, I, U> {
	private String alias;
	
	public JpaqlEntitySpecification() {
		
	}
	
	public JpaqlEntitySpecification(String propertyName, Class<U> entityClass) {
		super(propertyName, entityClass);
	}

	public JpaqlEntitySpecification(String propertyName, Class<U> enumClass, String alias) {
		this(propertyName, enumClass);
		this.alias = alias;
	}
	
	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		addHqlCondition((JpaqlAssembler)assembler);
		setHqlParam((JpaqlAssembler)assembler);
	}
	
	public boolean itselfSupportsAssembler(Assembler assembler) {
		return assembler instanceof JpaqlAssembler;
	}

	public void addHqlCondition(JpaqlAssembler assembler) {
		if (wasSet()) {
	        assembler.newCriteria();
	        if (alias != null) {
	        	assembler.append(alias).append(".");
	        } else {
            	assembler.append(assembler.getAliasPrefix());
            }
	        assembler.append(jpaqlPropertyName(getPropertyName()))
	                 .append(" = :")
	                 .append(normalizedPropertyName(getPropertyName()))
	                 .append(" ");
		}
    }
	
	public void setHqlParam(JpaqlAssembler assembler) {
		if (wasSet()) {
	    	assembler.addParameter(new JpaqlAssembler.ParameterSetter() {
	
				@Override
				public void setParameter(Query query) {
					query.setParameter(normalizedPropertyName(getPropertyName()), getValue());
				}
	    		
	    	});
		}
    }

}
