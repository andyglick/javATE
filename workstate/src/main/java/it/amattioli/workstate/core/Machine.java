package it.amattioli.workstate.core;

import java.util.*;
import java.io.*;

import it.amattioli.workstate.exceptions.*;
import it.amattioli.workstate.config.Registry;
import it.amattioli.workstate.conversion.ConversionService;

public class Machine extends SequentialState implements Cloneable {
	public static final String CONVERSION_SERVICE_TAG = "ConversionService";
	private MetaMachine metaState;
	private Queue<Object> eventsQueue = new Queue<Object>();
	private Queue<Map<String, Object>> eventParamsQueue = new Queue<Map<String, Object>>();
	private ConversionService conversionService = null;
	private StateMemento stateMemento;
	private Object owner;

	public Machine(MetaMachine metastate, CompositeState parent) {
		super(metastate, parent);
		this.metaState = metastate;
	}

	public Machine(MetaMachine metastate) {
		this(metastate, null);
	}

	/**
	 * Set the state machine owner. When a state machine is used inside the
	 * business layer, the machine will be often an attribute of an entity. This
	 * method allows to set a back reference to this entity so it is available
	 * to actions and guards as a state machine attribute whose tag is
	 * {@link MetaMachine#OWNER_TAG}.
	 * 
	 * @param owner the owner of this state machine
	 * 
	 */
	public void setOwner(Object owner) throws WorkflowException {
		// setAttribute(MetaMachine.OWNER_TAG,owner);
		this.owner = owner;
	}

	public Object getAttribute(String tag) {
		if (CONVERSION_SERVICE_TAG.equals(tag)) {
			return conversionService;
		}
		if (MetaMachine.OWNER_TAG.equals(tag)) {
			if (owner instanceof ReferenceState) {
				return ((ReferenceState) owner).getAttribute(tag);
			} else {
				return owner;
			}
		}
		if (metaState.isAllowedAttribute(tag)) {
			return super.getAttribute(tag);
		} else if (owner instanceof ReferenceState) {
			return ((ReferenceState) owner).getAttribute(tag);
		} else {
			throw ErrorMessages.newIllegalArgumentException(ErrorMessage.NON_EXISTING_ATTR_TAG, tag, this.toString());
		}
	}

	protected Map<String, Object> addAllAttributes(Map<String, Object> attributes) {
		super.addAllAttributes(attributes);
		if (owner instanceof ReferenceState) {
			return ((ReferenceState) owner).addAllAttributes(attributes);
		} else {
			return attributes;
		}
	}

	public void setAttribute(String tag, Object value) throws WorkflowException {
		if (metaState.isAllowedAttribute(tag)) {
			super.setAttribute(tag, value);
		} else if (owner instanceof ReferenceState) {
			((ReferenceState) owner).setAttribute(tag, value);
		} else {
			throw ErrorMessages.newIllegalArgumentException(ErrorMessage.NON_EXISTING_ATTR_TAG, tag, this.toString());
		}
	}

	public boolean hasAllowedAttribute(String tag) {
		boolean result = super.hasAllowedAttribute(tag);
		if (!result && owner instanceof ReferenceState) {
			result = ((ReferenceState) owner).hasAllowedAttribute(tag);
		}
		return result;
	}

	public Machine getRootMachine() {
		if (owner instanceof ReferenceState) {
			return ((ReferenceState) owner).getRootMachine();
		} else {
			return this;
		}
	}

	public Event buildEvent(String name, Map<String, Object> stringParameters) throws WorkflowException {
		Event event = metaState.buildEvent(name, stringParameters, conversionService);
		if (event == null) {
			return super.buildEvent(name, stringParameters);
		} else {
			return event;
		}
	}

	public boolean admitEvent(String eventName, Map<String, Object> stringParameters) throws WorkflowException {
		Event event = buildEvent(eventName, stringParameters);
		return admitEvent(event);
	}

	public void postEvent(String name, Map<String, Object> stringParameters) throws WorkflowException {
		eventsQueue.offer(name);
		eventParamsQueue.offer(stringParameters);
	}
	
	@SuppressWarnings("unchecked")
	public void postEvent(String name) throws WorkflowException {
		postEvent(name, Collections.EMPTY_MAP);
	}

	public void postEvent(Event event) {
		eventsQueue.offer(event);
		eventParamsQueue.offer(null);
	}

	public void processEvents() throws WorkflowException {
		Event event = null;
		do {
			Object e = eventsQueue.poll();
			if (e instanceof String) {
				String eventName = (String) e;
				Map<String, Object> stringParameters = eventParamsQueue.poll();
				event = buildEvent(eventName, stringParameters);
			} else {
				event = (Event) e;
				eventParamsQueue.poll();
			}
			if (event != null) {
				receiveEvent(event);
			}
		} while (event != null);
	}

	public boolean isInState(MetaState matchingMetaState) {
		MetaStateChecker checker = new MetaStateChecker(matchingMetaState);
		checker.visit(this);
		return checker.getResult();
	}
	
	public void useConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		metaState = (MetaMachine) in.readObject();
		conversionService = (ConversionService) in.readObject();
		stateMemento = (StateMemento) in.readObject();
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeObject(metaState);
		out.writeObject(conversionService);
		out.writeObject(getMemento());
	}

	private Object readResolve() throws WorkflowException {
		Machine result = (Machine) metaState.newState(null);
		result.restore(stateMemento);
		result.useConversionService(conversionService);
		return result;
	}

	public void terminate() throws WorkflowException {
		super.terminate();
		if (owner instanceof ReferenceState) {
			((ReferenceState) owner).terminate();
		}
	}
	
	@Override
	public Machine clone() {
		try {
			return Registry.instance().newMachine(metaState.getConfig().getId(), owner, null, getMemento());
		} catch(WorkflowException e) {
			throw new RuntimeException(e);
		}
	}
}
