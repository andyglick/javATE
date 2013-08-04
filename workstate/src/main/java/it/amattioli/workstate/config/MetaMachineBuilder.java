package it.amattioli.workstate.config;

import it.amattioli.workstate.core.*;
import static it.amattioli.workstate.exceptions.ErrorMessage.*;
import static it.amattioli.workstate.exceptions.ErrorMessages.*;

public class MetaMachineBuilder extends MetaCompositeStateBuilder {
	private Configuration reader;
	private EventRepository eventRepository;
	private String ownerClass;

	public MetaMachineBuilder(String tag, String id) {
		super(tag, id);
	}

	public void setReader(Configuration reader) {
		if (builtState != null) {
			throw newIllegalStateException(OBJECT_ALREADY_BUILT, "Reader", "MetaMachine");
		}
		this.reader = reader;
	}

	public void setEventRepository(EventRepository eventRepository) {
		if (builtState != null) {
			throw newIllegalStateException(OBJECT_ALREADY_BUILT, "EventRepository", "MetaMachine");
		}
		this.eventRepository = eventRepository;
	}

	public void setOwnerClass(String ownerClass) {
		if (builtState != null) {
			throw newIllegalStateException(OBJECT_ALREADY_BUILT, "EventRepository", "MetaMachine");
		}
		this.ownerClass = ownerClass;
	}

	protected MetaState createMetaState() {
		MetaMachine result = new MetaMachine(getTag(), reader, getId(), eventRepository, ownerClass);
		for (MetaAttribute currAttribute: getTempAttributeRepository()) {
			result.addAttribute(currAttribute);
		}
		for (MetaState currSubstate: getTempSubstateRepository()) {
			result.addMetaState(currSubstate);
		}
		return result;
	}

}
