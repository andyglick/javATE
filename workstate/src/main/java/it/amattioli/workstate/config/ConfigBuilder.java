package it.amattioli.workstate.config;

import it.amattioli.workstate.actions.BeanShellStateAction;
import it.amattioli.workstate.actions.StateAction;
import it.amattioli.workstate.core.*;
import it.amattioli.workstate.conversion.ConversionService;
import static it.amattioli.workstate.exceptions.ErrorMessage.*;
import static it.amattioli.workstate.exceptions.ErrorMessages.*;

import java.util.*;

/**
 * <p>
 * Instances of this class allows finite state machines configuration.
 * </p>
 * 
 * <p>
 * Using this class id possible to configure a finite state machine in a very
 * simple way:
 * <ul>
 *   <li>instantiate an object of this class</li>
 *   <li>call its building methods in the right order</li>
 *   <li>use the {@link #getResult()} method to obtain the configuration</li>
 * </ul>
 * This is an implementation of the builder pattern for {@link MetaMachine}
 * objects.
 * </p>
 * 
 * <p>
 * Once you have a builder instance you need to define the possible states
 * for the machine. This can be done using {@link #newState(String, String, String)}
 * and {@link #endState()} methods.
 * </p>
 * 
 * <p>
 * Obviously, when you call {@link #newState(String, String, String)} you will
 * start a state definition that will end with a corresponding {@link #endState()}
 * call. To define a sub-state you will need a couple of {@link #newState(String, String, String)}
 * and {@link #endState()} call inside the previous one.
 * </p>
 * 
 * <p>
 * Let us write a simple example with a sequential state with two simple sub-states:
 * <code><pre>
 *   newState("sequential","mySequentialState","1");
 *   newState("simple","mySimpleStateOne","2");
 *   enddState();
 *   newState("simple","mySimpleStateTwo","3");
 *   endState();
 *   endState();
 * </pre></code>
 * </p>
 * 
 * <p>
 * When you call {@link #newState(String, String, String)} for the first time
 * you will need to specify the "machine" type to say that the root state
 * will be a {@link MetaMachine}.
 * </p>
 * 
 * <p>
 * Now you can configure the events. This can be done using the {@link #newEvent(String, String)}
 * and {@link #endEvent()} methods. Contrary to the state definition this
 * calls cannot be nested because sub-events are nonsense.
 * </p>
 * 
 * <p>
 * At the end you need to define transitions, both external and internal,
 * using the {@link #newExternalTransition(String, String, String)} and
 * {@link #newInternalTransition(String, String)} closed by the
 * {@link #endTransition()} method.
 * </p>
 * 
 */
public class ConfigBuilder {
	public static enum StateType {
		SIMPLE,
		SEQUENTIAL,
		CONCURRENT,
		INITIAL,
		FINAL,
		JUNCTION,
		MACHINE,
		REFERENCE
	}
	private Configuration reader;
	private String id;
	private boolean stateBuildComplete = false;
	private MetaStateBuilder stateBuilder = null;
	private Stack<MetaCompositeStateBuilder> stateStack = new Stack<MetaCompositeStateBuilder>();
	private Map<String, MetaState> stateMap = new HashMap<String, MetaState>();
	private MetaAttributeBuilder attrBuilder = null;
	private EventRepository eventRepository;
	private MetaEventBuilder eventBuilder;
	private Map<String, MetaEvent> eventMap = new HashMap<String, MetaEvent>();
	private AttributeOwnerBuilder attrOwnerBuilder;
	private TransitionBuilder transitionBuilder;
	private String ownerClass;

	/**
	 * <p>
	 * Build a new {@link ConfigBuilder}.
	 * </p>
	 * <p>
	 * The built {@link MetaMachine} will have a reference to the passed configuration.
	 * This configuration will be used eventually for deserialization.
	 * It is possible to pass null to this constructor but it will not be possible 
	 * to serialize/deserialize correctly the {@link MetaMachine}
	 * and all the {@link Machine} built with it. 
	 * </p>
	 * 
	 * @param reader The {@link Configuration} that is reading the configuration.
	 * @param id The identifier of the building {@link MetaMachine}
	 */
	public ConfigBuilder(Configuration reader, String id) {
		this.reader = reader;
		this.id = id;
		eventRepository = new EventRepository(new ConversionService());
	}

	public void setOwnerClass(String ownerClass) {
		this.ownerClass = ownerClass;
	}

	/**
	 * <p>
	 * Start the configuration of a new state.
	 * </p>
	 * <p>
	 * The state type can be one of the following:
	 * <dl>
	 * <dt>simple</dt>
	 * <dd>to define a simple state</dd>
	 * <dt>sequential</dt>
	 * <dd>to define a sequential state</dd>
	 * <dt>concurrent</dt>
	 * <dd>to define a concurrent state</dd>
	 * <dt>initial</dt>
	 * <dd>to define the initial state of a sequential state</dd>
	 * <dt>junction</dt>
	 * <dd>to define a Junction-Point</dd>
	 * <dt>machine</dt>
	 * <dd>to define the root-state of a finite state machine</dd>
	 * </dl>
	 * </p>
	 * <p>
	 * A state configuration must always be closed with a call to the
	 * {@link #endState()} method.
	 * </p>
	 * <p>
	 * You also need to pass and id to this method. This unique id will be
	 * used during the configuration to reference the objects you are building.
	 * For example, if you define a state with id="1", the same string will be
	 * used during a transition definition to or from this state.
	 * </p>
	 * 
	 * @param type the state type
	 * @param tag the state tag. Ignored if you are defining a pseudo-state
	 * @param id the unique id of the state
	 * @throws IllegalStateException 
	 */
	public void newState(StateType type, String tag, String id) {
		if (stateBuildComplete) {
			throw newIllegalStateException(ONE_ROOT_STATE_ONLY);
		}
		MetaStateBuilder newBuilder = null;
		switch (type) {
			case SIMPLE:
				newBuilder = new MetaSimpleStateBuilder(tag, id);
				break;
			case SEQUENTIAL:
				newBuilder = new MetaSequentialStateBuilder(tag, id);
				break;
			case CONCURRENT:
				newBuilder = new MetaConcurrentStateBuilder(tag, id);
				break;
			case INITIAL:
				newBuilder = new MetaInitialStateBuilder(id);
				break;
			case FINAL:
				newBuilder = new MetaFinalStateBuilder(id);
				break;
			case REFERENCE:
				newBuilder = new MetaReferenceStateBuilder(tag, id);
				break;
			case JUNCTION:
				newBuilder = new MetaJunctionPointBuilder(id);
				break;
			case MACHINE:
				//if (this.id.equals(id)) {
					if (stateBuilder != null) {
						throw newIllegalStateException(SUB_MACHINE_NOT_ALLOWED);
					}
					newBuilder = new MetaMachineBuilder(tag, id);
				//}
		}
		if (stateBuilder != null) {
			if (stateBuilder instanceof MetaCompositeStateBuilder) {
				stateStack.push((MetaCompositeStateBuilder) stateBuilder);
			} else {
				throw newIllegalStateException(SUBSTATE_NOT_ALLOWED);
			}
		}
		stateBuilder = newBuilder;
		if (stateBuilder instanceof MetaRealStateBuilder) {
			attrOwnerBuilder = (AttributeOwnerBuilder) stateBuilder;
		} else {
			attrOwnerBuilder = null;
		}
	}

	/**
	 * End a state configuration.
	 * 
	 * @throws IllegalStateException if there is no corresponding
	 *         {@link #newState(String, String, String)} method call
	 */
	public void endState() {
		if (stateBuildComplete) {
			throw newIllegalStateException(UNMATCHED_END_STATE);
		}
		if (!stateStack.empty()) {
			MetaCompositeStateBuilder poppedBuilder = stateStack.pop();
			MetaState buildState = stateBuilder.getBuiltMetaState();
			stateMap.put(stateBuilder.getId(), buildState);
			poppedBuilder.addSubstate(buildState);
			stateBuilder = poppedBuilder;
			if (stateBuilder instanceof MetaRealStateBuilder) {
				attrOwnerBuilder = (AttributeOwnerBuilder) stateBuilder;
			} else {
				attrOwnerBuilder = null;
			}
		} else {
			if (stateBuilder == null) {
				throw newIllegalStateException(UNMATCHED_END_STATE);
			}
			stateBuildComplete = true;
		}
	}

	/**
	 * <p>
	 * Set the entry-action of the building state.
	 * </p>
	 * <p>
	 * This method must always be called between {@link #newState(String, String, String)}
	 * and {@link #endState()}.
	 * </p>
	 * <p>
	 * The string containing the action definition can be:
	 * <ul>
	 * <li>
	 * If it starts and ends with curly braces it will be interpreted as a
	 * BeanShell script so an istance of {@link BeanShellStateAction} will
	 * be created. This action will interpret the script at runtime. The script
	 * syntax will not be verified at configuration time.
	 * </li>
	 * <li>
	 * Otherwise it will be interpreted as the constructor of a class the
	 * implements the {@link StateAction} interface. Practically speaking
	 * will be executed the java code obtained concatenating "new" with the
	 * passed string. For example, if the passed string is: 
	 * "it.amattioli.example.MyAction(\"param\")", the expression
	 * <code>new it.amattioli.example.MyAction("param")</code> will be evaluated
	 * and the result will be used as entry-action.
	 * </li>
	 * </ul>
	 * </p>
	 * 
	 * @param entryAction a string containing the entry-action definition
	 *            
	 * @throws IllegalStateException if this method is called outside a state
	 *         definition
	 */
	public void setEntryAction(String entryAction) {
		if (stateBuilder == null) {
			throw newIllegalStateException(STATEACTION_OUT_OF_STATE, "EntryAction");
		}
		stateBuilder.setEntryAction(entryAction);
	}

	public void setExitAction(String exitAction) {
		if (stateBuilder == null) {
			throw newIllegalStateException(STATEACTION_OUT_OF_STATE, "ExitAction");
		}
		stateBuilder.setExitAction(exitAction);
	}

	public void newAttribute(String tag, String attrClass, String initial) {
		if (attrBuilder != null) {
			throw newIllegalStateException(NESTED_ATTRIBUTE);
		}
		if (attrOwnerBuilder != null) {
			attrBuilder = new MetaAttributeBuilder(tag, attrClass, initial);
		} else {
			throw newIllegalStateException(ILLEGAL_ATTR_OWNER);
		}
	}

	public void setAttributeValidator(String validator) {
	}

	public void endAttribute() {
		if (attrBuilder == null) {
			throw newIllegalStateException(UNMATCHED_END_ATTR);
		}
		attrOwnerBuilder.addAttribute(attrBuilder.getBuiltAttribute());
		attrBuilder = null;
	}

	public void newEvent(String tag, String id) {
		if (eventBuilder != null) {
			throw newIllegalStateException(NESTED_EVENT);
		}
		eventBuilder = new MetaEventBuilder(tag, id);
		attrOwnerBuilder = eventBuilder;
	}

	public void endEvent() {
		if (eventBuilder == null) {
			throw newIllegalStateException(UNMATCHED_END_EVENT);
		}
		eventRepository.addMetaEvent(eventBuilder.getBuiltEvent());
		eventMap.put(eventBuilder.getId(), eventBuilder.getBuiltEvent());
		eventBuilder = null;
		attrOwnerBuilder = null;
	}

	public void newInternalTransition(String event, String state) {
		if (transitionBuilder != null) {
			throw newIllegalStateException(NESTED_TRANSITION);
		}
		MetaState metaState = stateMap.get(state);
		MetaEvent metaEvent = eventMap.get(event);
		transitionBuilder = new InternalTransitionBuilder(metaEvent, metaState);
	}

	public void newExternalTransition(String event, String startState, String endState) {
		if (transitionBuilder != null) {
			throw newIllegalStateException(NESTED_TRANSITION);
		}
		MetaState startMetaState = stateMap.get(startState);
		MetaState endMetaState = stateMap.get(endState);
		MetaEvent metaEvent = eventMap.get(event);
		transitionBuilder = new ExternalTransitionBuilder(metaEvent,
				startMetaState, endMetaState);
	}

	public void setAction(String action) {
		if (transitionBuilder == null) {
			throw newIllegalStateException(ACTION_OUT_OF_TRANSITION);
		}
		transitionBuilder.setAction(action);
	}

	public void setGuard(String guard) {
		if (transitionBuilder == null) {
			throw newIllegalStateException(GUARD_OUT_OF_TRANSITION);
		}
		transitionBuilder.setGuard(guard);
	}

	public void endTransition() {
		if (transitionBuilder == null) {
			throw newIllegalStateException(UNMATCHED_END_TRANSITION);
		}
		transitionBuilder.getBuiltTransition();
		transitionBuilder = null;
	}

	public void setReference(String ref) {
		if (stateBuilder == null) {
			throw newIllegalStateException(STATEACTION_OUT_OF_STATE, "Reference");
		}
		((MetaReferenceStateBuilder) stateBuilder).setRef(ref);
	}

	public MetaMachine getResult() {
		if (!stateBuildComplete) {
			// TODO: ECCEZIONE !!!!!!!
		}
		if (!(stateBuilder instanceof MetaMachineBuilder)) {
			// TODO: ECCEZIONE !!!!!!!!!
		}
		MetaMachineBuilder machineBuilder = (MetaMachineBuilder) stateBuilder;
		machineBuilder.setReader(reader);
		machineBuilder.setEventRepository(eventRepository);
		machineBuilder.setOwnerClass(ownerClass);
		return (MetaMachine) machineBuilder.getBuiltMetaState();
	}

}
