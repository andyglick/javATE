package it.amattioli.dominate.jpa.specifications;

import java.io.Serializable;

import javax.persistence.Query;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.MultipleEntitySpecification;

import static it.amattioli.dominate.jpa.specifications.JpaqlUtils.*;

public class JpaqlMultipleEntitySpecification<T extends Entity<?>, I extends Serializable, U extends Entity<I>> extends MultipleEntitySpecification<T, I, U> {
	private String alias;
	
	public JpaqlMultipleEntitySpecification() {

	}
	
	public JpaqlMultipleEntitySpecification(String propertyName, Class<U> enumClass) {
		super(propertyName, enumClass);
	}
	
	public JpaqlMultipleEntitySpecification(String propertyName, Class<U> enumClass, String alias) {
		this(propertyName, enumClass);
		this.alias = alias;
	}
	
	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		addJpaqlCondition((JpaqlAssembler)assembler);
		setJpaqlParam((JpaqlAssembler)assembler);
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
			         .append(" in ( ");
	        for (int i = 0; i < getValue().size(); i++) {
	        	assembler.append(":").append(normalizedPropertyName(getPropertyName())).append(Integer.toString(i));
	        	if (i != getValue().size() - 1) {
	        		assembler.append(" , ");
	        	}
	        }
	        assembler.append(" )");
		}
    }
	
	public void setJpaqlParam(JpaqlAssembler assembler) {
		if (wasSet()) {
	    	assembler.addParameter(new JpaqlAssembler.ParameterSetter() {
	
				@Override
				public void setParameter(Query query) {
					int i = 0;
		            for (U value : getValue()) {
		                query.setParameter(normalizedPropertyName(getPropertyName()) + (i++), value);
		            }
				}
	    		
	    	});
		}
    }

}
