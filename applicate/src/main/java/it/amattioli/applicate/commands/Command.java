package it.amattioli.applicate.commands;

import it.amattioli.dominate.properties.PropertyChangeEmitter;

/**
 * A command is a way to encapsulate an operation in an object. You can create a command
 * object, set the operation parameters passing them to the constructor or using properties
 * accessor methods, and then execute the operation calling {@link #doCommand()}.
 * 
 * Every time a command is executed a CommandEvent should be fired. Other objects can
 * listen to these events adding a {@link CommandListener} to this command using
 * {@link #addCommandListener(CommandListener)} or {@link #addCommandListener(CommandListener, CommandResult...)}. 
 *
 * @author andrea
 *
 */
public interface Command extends PropertyChangeEmitter {

	/**
	 * Execute this command.
	 * 
	 * @throws ApplicationException
	 */
    public void doCommand() throws ApplicationException;

    public void cancelCommand();

    /**
     * Add a listener for this command events. Every time this command fires a
     * {@link CommandEvent} the {@link CommandListener#commandDone(CommandEvent)}
     * method will be called on all the added listeners.
     * 
     * @param listener the listener to be added
     */
    public void addCommandListener(CommandListener listener);

    /**
     * Add a listener for this command events. Every time this command fires a
     * {@link CommandEvent} the {@link CommandListener#commandDone(CommandEvent)}
     * method will be called on all the added listeners with result equals to one
     * of the specified results.
     * 
     * @param listener the listener to be added
     */
    public void addCommandListener(CommandListener listener, CommandResult... results);

}
