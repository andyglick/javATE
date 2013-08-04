package it.amattioli.applicate.commands;

import it.amattioli.dominate.properties.PropertyChangeEmitter;
import it.amattioli.dominate.properties.ValuesLister;
import it.amattioli.dominate.properties.ValuesListerImpl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import it.amattioli.dominate.properties.PropertyChangeSupport;
import java.util.Collection;

/**
 * This class is a base implementation of the {@link Command} interface.
 * It implements the two addCommandListener methods and fires a {@link CommandEvent}
 * when {@link #doCommand()} and {@link #cancelCommand()} are called with
 * {@link CommandResult#SUCCESSFUL} and {@link CommandResult#CANCELLED} respectively.
 * 
 * This class implements {@link PropertyChangeEmitter} too, so extenders can fire 
 * {@link PropertyChangeEvent} when a command property changes.
 * 
 * @author andrea
 *
 */
public abstract class AbstractCommand implements Command, PropertyChangeEmitter, ValuesLister {

	private CommandEventSupport cmdEvtSupport = new CommandEventSupport();
	private PropertyChangeSupport pChange = new PropertyChangeSupport(this);
	private ValuesLister valuesLister = new ValuesListerImpl(this);

	public AbstractCommand() {
		super();
	}

	public void doCommand() throws ApplicationException {
		cmdEvtSupport.fireCommandEvent(new CommandEvent(this, CommandResult.SUCCESSFUL));
	}

	public void cancelCommand() {
		cmdEvtSupport.fireCommandEvent(new CommandEvent(this, CommandResult.CANCELLED));
	}

	public void addCommandListener(CommandListener listener) {
	    cmdEvtSupport.addListener(listener);
	}

	public void addCommandListener(CommandListener listener, CommandResult... results) {
		cmdEvtSupport.addListener(listener, results);
	}
	
	public void setCommandListeners(Collection<CommandListener> listeners) {
		for (CommandListener curr: listeners) {
			addCommandListener(curr);
		}
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		pChange.firePropertyChange(propertyName, oldValue, newValue);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
	    pChange.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
	    pChange.removePropertyChangeListener(listener);
	}

	@Override
	public Collection<?> getPropertyValues(String propertyName) {
		return valuesLister.getPropertyValues(propertyName);
	}

}