package it.amattioli.workstate.config;

import it.amattioli.workstate.core.CompositeState;
import it.amattioli.workstate.core.Machine;
import it.amattioli.workstate.core.MetaMachine;
import it.amattioli.workstate.core.StateMemento;
import it.amattioli.workstate.exceptions.WorkflowException;

import java.util.HashMap;
import java.util.Map;

public class Registry {
	private static final Registry instance = new Registry();
	
	public static Registry instance() {
		return instance;
	}
	
	private Map<String, Configuration> configs = new HashMap<String, Configuration>();
	
	private Registry() {
		
	}
	
	public void register(Configuration config) {
		configs.put(config.getId(), config);
	}
	
	public MetaMachine getMetaMachine(String id) {
		return configs.get(id).read();
	}
	
	public MetaMachine getMetaMachine(Configuration config) {
		if (!configs.containsKey(config.getId())) {
			register(config);
		}
		return configs.get(config.getId()).read();
	}
	
	public Machine newMachine(Configuration config, Object owner, CompositeState parent) throws WorkflowException {
		MetaMachine metaMachine = getMetaMachine(config);
		Machine result = metaMachine.newMachineInstance(owner, parent);
		return result;
	}
	
	public Machine newMachine(String id, Object owner, CompositeState parent) throws WorkflowException {
		MetaMachine metaMachine = getMetaMachine(id);
		Machine result = metaMachine.newMachineInstance(owner, parent);
		return result;
	}
	
	public Machine newMachine(Configuration config, Object owner) throws WorkflowException {
		return newMachine(config, owner, null);
	}

	public Machine newMachine(String id, Object owner) throws WorkflowException {
		return newMachine(id, owner, null);
	}

	public Machine newMachine(String id) throws WorkflowException {
		return newMachine(id, null);
	}

	public Machine newMachine(String id, Object owner, CompositeState parent, StateMemento memento) throws WorkflowException {
		MetaMachine metaMachine = getMetaMachine(id);
		Machine result = metaMachine.newMachineInstance(owner, parent, memento);
		return result;
	}
}
