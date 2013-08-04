package it.amattioli.workstate.hibernate;

import org.hibernate.Hibernate;
import org.hibernate.Query;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.hibernate.specifications.HqlAssembler;
import it.amattioli.dominate.hibernate.specifications.HqlAssembler.ParameterSetter;
import it.amattioli.workstate.core.MetaRealState;
import it.amattioli.workstate.specifications.MachineSpecification;

import static it.amattioli.dominate.hibernate.specifications.HqlUtils.*;

public class HqlMachineSpecification<T extends Entity<?>> extends MachineSpecification<T> {
	private String alias;
	
	public HqlMachineSpecification() {
	}
	
	public HqlMachineSpecification(String propertyName, String workflowName) {
		super(propertyName, workflowName);
	}
	
	public HqlMachineSpecification(String propertyName, String workflowName, MachineSpecification<T> chained) {
		super(propertyName, workflowName, chained);
	}

	public HqlMachineSpecification(String propertyName, String workflowName, String alias) {
		super(propertyName, workflowName);
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
            assembler.append(hqlPropertyName(getPropertyName()))
                  .append(" like :")
                  .append(normalizedPropertyName(getPropertyName()))
                  .append(" ");
		}
    }
	
	public void setHqlParam(HqlAssembler assembler) {
		if (wasSet()) {
        	assembler.addParameter(new ParameterSetter() {

				@Override
				public void setParameter(Query query) {
					query.setParameter(normalizedPropertyName(getPropertyName()), 
							           MetaStateQueryBuilder.buildQueryString((MetaRealState)getMetaState()),
							           Hibernate.STRING);
				}
				
			});
		}
    }

}
