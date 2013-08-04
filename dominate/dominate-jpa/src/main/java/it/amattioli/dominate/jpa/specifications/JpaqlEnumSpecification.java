package it.amattioli.dominate.jpa.specifications;

import javax.persistence.Query;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.EnumSpecification;

import static it.amattioli.dominate.jpa.specifications.JpaqlUtils.*;

public class JpaqlEnumSpecification<T extends Entity<?>, U extends Enum<U>> extends EnumSpecification<T, U> {
	private String alias;
	
	public JpaqlEnumSpecification() {
		
	}
	
	public JpaqlEnumSpecification(String propertyName, Class<U> enumClass) {
		super(propertyName, enumClass);
	}
	
	public JpaqlEnumSpecification(String propertyName, Class<U> enumClass, String alias) {
		this(propertyName, enumClass);
		this.alias = alias;
	}
	
	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		addJpaqlCondition((JpaqlAssembler)assembler);
		setHqlParam((JpaqlAssembler)assembler);
	}
	
	public boolean itselfSupportsAssembler(Assembler assembler) {
		return assembler instanceof JpaqlAssembler;
	}

	public void addJpaqlCondition(JpaqlAssembler assembler) {
		if (wasSet()) {
	        assembler.newCriteria();
	        if (alias != null) {
	        	assembler.append(alias).append(".");
	        } else {
            	assembler.append(assembler.getAliasPrefix());
            }
	        assembler.append(jpaqlPropertyName(getPropertyName()))
	                 .append(" like :")
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
