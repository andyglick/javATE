package it.amattioli.dominate.jpa.specifications;

import javax.persistence.Query;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.ObjectSpecification;

import static it.amattioli.dominate.jpa.specifications.JpaqlUtils.*;

public class JpaqlObjectSpecification<T extends Entity<?>> extends ObjectSpecification<T> {
	private String alias;
	
	public JpaqlObjectSpecification() {

	}
	
	public JpaqlObjectSpecification(String propertyName) {
		super(propertyName);
	}

	public JpaqlObjectSpecification(String propertyName, String alias) {
		this(propertyName);
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
	                 .append(" = :")
	                 .append(normalizedPropertyName(getPropertyName()))
	                 .append(" ");
		}
    }
	
	public void setJpaqlParam(JpaqlAssembler assembler) {
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
