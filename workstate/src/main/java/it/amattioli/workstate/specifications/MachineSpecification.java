package it.amattioli.workstate.specifications;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.specifications.ChainedSpecification;
import it.amattioli.workstate.config.Registry;
import it.amattioli.workstate.core.Machine;
import it.amattioli.workstate.core.MetaState;

public abstract class MachineSpecification <T extends Entity<?>> extends ChainedSpecification<T> {
	private String propertyName;
	private String workflowName;
	private String stateName;
	
	public static <T extends Entity<?>> MachineSpecification<T> newInstance(String propertyName, String workflowName) {
		MachineSpecification<T> result = ChainedSpecification.createChain(MachineSpecification.class);
		result.setPropertyName(propertyName);
		result.setWorkflowName(workflowName);
		return result;
	}
	
	public MachineSpecification() {
	}
	
	public MachineSpecification(String propertyName, String workflowName) {
		this.propertyName = propertyName;
		this.workflowName = workflowName;
	}
	
	public MachineSpecification(String propertyName, String workflowName, MachineSpecification<T> chained) {
		super(chained);
		this.propertyName = propertyName;
		this.workflowName = workflowName;
	}

	protected String getPropertyName() {
		return propertyName;
	}
	
	protected void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
		if (getNextInChain() != null) {
			((MachineSpecification<T>)getNextInChain()).setPropertyName(propertyName);
		}
	}
	
	protected String getWorkflowName() {
		return workflowName;
	}
	
	protected void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
		if (getNextInChain() != null) {
			((MachineSpecification<T>)getNextInChain()).setWorkflowName(workflowName);
		}
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
		if (getNextInChain() != null) {
			((MachineSpecification<T>)getNextInChain()).setStateName(stateName);
		}
	}
	
	protected MetaState getMetaState() {
		return Registry.instance().getMetaMachine(workflowName).findMetaState(stateName);
	}

	@Override
	public boolean isSatisfiedBy(T entity) {
		if (!wasSet()) {
			return isSatisfiedIfNotSet();
		}
		try {
			Machine m = (Machine)PropertyUtils.getProperty(entity, getPropertyName());
			return m.isInState(getMetaState());
		} catch (IllegalAccessException e) {
			throw new RuntimeException();
		} catch (InvocationTargetException e) {
			throw new RuntimeException();
		} catch (NoSuchMethodException e) {
			throw new RuntimeException();
		}
	}

	public boolean wasSet() {
		return getStateName() != null && !getStateName().equals("");
	}
}
