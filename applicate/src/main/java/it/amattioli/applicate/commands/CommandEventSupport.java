package it.amattioli.applicate.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandEventSupport {
	private static final String DUMMY = "dummy";
	private static final Logger logger = LoggerFactory.getLogger(CommandEventSupport.class);
	private WeakHashMap<CommandListener, String> listeners;
	private Map<CommandResult, WeakHashMap<CommandListener, String>> specificListeners;

	private WeakHashMap<CommandListener, String> getGenericListeners() {
        if (listeners == null) {
            listeners = new WeakHashMap<CommandListener, String>();
        }
        return listeners;
    }

	private WeakHashMap<CommandListener, String> getSpecificListeners(CommandResult result) {
		if (specificListeners == null) {
			specificListeners = new HashMap<CommandResult, WeakHashMap<CommandListener, String>>();
		}
		WeakHashMap<CommandListener, String> wantedListeners = specificListeners.get(result);
		if (wantedListeners == null) {
			wantedListeners = new WeakHashMap<CommandListener, String>();
			specificListeners.put(result, wantedListeners);
		}
		return wantedListeners;
	}

    public void addListener(CommandListener listener) {
    	logger.debug("Registering command listener {}", listener);
        getGenericListeners().put(listener, DUMMY);
    }

    public void removeListener(CommandListener listener) {
    	logger.debug("Deregistering command listener {}", listener);
    	getGenericListeners().remove(listener);
		for (WeakHashMap<CommandListener, String> curr : specificListeners.values()) {
			curr.remove(listener);
		}
    }

	public void addListener(CommandListener listener, CommandResult... results) {
    	for (CommandResult curr : results) {
    		getSpecificListeners(curr).put(listener, DUMMY);
    	}
    }

    private void fireCommandEvent(CommandEvent evt, CommandListener listener) {
    	logger.debug("Notifying {} to {}", evt, listener);
		try {
			listener.commandDone(evt);
		} catch (RuntimeException e) {
			if (evt.getExc() != null) {
				logger.error("The following exception prevented the command from being executed: ", evt.getExc());
			}
			throw e;
		}
    }

    public void fireCommandEvent(CommandEvent evt) {
    	for (CommandListener curr : getGenericListeners().keySet()) {
    		fireCommandEvent(evt, curr);
        }
    	if (evt.getResult() != null) {
    		for (CommandListener curr : getSpecificListeners(evt.getResult()).keySet()) {
    			fireCommandEvent(evt, curr);
            }
    	}
    	logger.debug("Command Listeners notified");
    }

}
