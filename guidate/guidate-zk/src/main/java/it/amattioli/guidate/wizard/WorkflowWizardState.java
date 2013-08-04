package it.amattioli.guidate.wizard;

import java.util.Collections;

import it.amattioli.workstate.config.Registry;
import it.amattioli.workstate.core.Machine;
import it.amattioli.workstate.exceptions.WorkflowException;

public class WorkflowWizardState implements WizardState {
	private String id;
	private Object owner;
	private Machine machine;
	
	public WorkflowWizardState(String id) {
		this(id, null);
	}
	
	public WorkflowWizardState(String id, Object owner) {
		this.id = id;
		this.owner = owner;
	}
	
	public void setBackBean(Object backBean) {
		this.owner = backBean;
	}

	private void setMachine(Machine machine) {
		this.machine = machine;
	}

	private Machine getMachine() {
		if (machine == null) {
			try {
				setMachine(Registry.instance().newMachine(id, owner));
			} catch(WorkflowException e) {
				throw new RuntimeException(e);
			}
		}
		return machine;
	}

	@Override
	public String getCurrentPage() {
		WorkflowPageRetriever retriever = new WorkflowPageRetriever();
		retriever.visit(getMachine());
		return retriever.getPageUrl();
	}

	@Override
	public boolean hasNext() {
		try {
			return getMachine().admitEvent("next", Collections.EMPTY_MAP);
		} catch(WorkflowException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean hasPrevious() {
		try {
			return getMachine().admitEvent("previous", Collections.EMPTY_MAP);
		} catch(WorkflowException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public boolean hasFinish() {
		try {
			return getMachine().admitEvent("finish", Collections.EMPTY_MAP);
		} catch(WorkflowException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void next() {
		try {
			getMachine().postEvent("next", Collections.EMPTY_MAP);
			getMachine().processEvents();
		} catch(WorkflowException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void previous() {
		try {
			getMachine().postEvent("previous", Collections.EMPTY_MAP);
			getMachine().processEvents();
		} catch(WorkflowException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void finish() {
		try {
			getMachine().postEvent("finish", Collections.EMPTY_MAP);
			getMachine().processEvents();
		} catch(WorkflowException e) {
			throw new RuntimeException(e);
		}
	}
	
}
