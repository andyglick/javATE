package it.amattioli.dominate.hibernate.specifications;

import java.io.Serializable;

import org.hibernate.Query;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.MultipleEntitySpecification;

import static it.amattioli.dominate.hibernate.specifications.HqlUtils.*;

public class HqlMultipleEntitySpecification<T extends Entity<?>, I extends Serializable, U extends Entity<I>> extends MultipleEntitySpecification<T, I, U> {
	private String alias;
	
	public HqlMultipleEntitySpecification() {

	}
	
	public HqlMultipleEntitySpecification(String propertyName, Class<U> enumClass) {
		super(propertyName, enumClass);
	}
	
	public HqlMultipleEntitySpecification(String propertyName, Class<U> enumClass, String alias) {
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
	
	public void setHqlParam(HqlAssembler assembler) {
		if (wasSet()) {
	    	assembler.addParameter(new HqlAssembler.ParameterSetter() {
	
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
