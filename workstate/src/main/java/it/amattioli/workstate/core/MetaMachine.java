package it.amattioli.workstate.core;

import it.amattioli.workstate.config.*;
import it.amattioli.workstate.exceptions.*;
import it.amattioli.workstate.conversion.ConversionService;
import java.util.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class MetaMachine extends MetaSequentialState implements java.io.Serializable {
	public static final String OWNER_TAG = "owner";
	private EventRepository eventRepository;
	private Configuration config;
	private String id;

	protected MetaMachine(String tag, String ownerClass) {
		super(tag, null, null);
		if (ownerClass == null || ownerClass.trim().equals("")) {
			ownerClass = "Object";
		}
		MetaAttribute metaOwner = new MetaAttribute(OWNER_TAG, ownerClass.trim(), null);
		addAttribute(metaOwner);
	}

	public MetaMachine(String tag, Configuration config, String id, EventRepository eventRepository, String ownerClass) {
		this(tag, ownerClass);
		this.config = config;
		this.id = id;
		this.eventRepository = eventRepository;
	}

	public State newState(CompositeState parent) {
		return new Machine(this, parent);
	}

	public Machine newMachineInstance(Object owner, CompositeState parent, StateMemento memento) throws WorkflowException {
		Machine result = (Machine) newState(parent);
		result.setOwner(owner);
		if (memento == null) {
			result.enter();
		} else {
			result.restore(memento);
		}
		return result;
	}

	public Machine newMachineInstance(Object owner, CompositeState parent) throws WorkflowException {
		Machine result = (Machine) newState(parent);
		result.setOwner(owner);
		result.enter();
		return result;
	}

	public Machine newMachineInstance(CompositeState parent) throws WorkflowException {
		return newMachineInstance(null, parent);
	}

	public Event buildEvent(String name, Map<String,Object> stringParameters) throws WorkflowException {
		return eventRepository.buildEvent(name, stringParameters);
	}

	public Event buildEvent(String name, Map<String,Object> stringParameters, ConversionService conversionService) throws WorkflowException {
		return eventRepository.buildEvent(name, stringParameters, conversionService);
	}

	/**
	 * Controlla se questa macchina &egrave; in grado di interpretare l'evento
	 * il cui tag è passato come parametro.
	 * 
	 * @param name
	 *            il tag dell'evento da verificare
	 * @return true se questa macchina &egrave; in grado di interpretare
	 *         l'evento, altrimenti false
	 */
	public boolean knowsEvent(String name) {
		return eventRepository.containsEvent(name);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		config = (Configuration) in.readObject();
		id = (String) in.readObject();
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeObject(config);
		out.writeObject(id);
	}

	private Object readResolve() {
		return Registry.instance().getMetaMachine(config);
	}

	public Configuration getConfig() {
		return config;
	}
	
	public MetaState findMetaState(String tag) {
		MetaStateFinder finder = new MetaStateFinder(tag);
		finder.visit(this);
		return finder.getResult();
	}

}
