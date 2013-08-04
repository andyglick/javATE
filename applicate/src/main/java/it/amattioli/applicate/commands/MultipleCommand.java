package it.amattioli.applicate.commands;

//import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

//import org.apache.commons.beanutils.PropertyUtils;

/**
 * Un command ch epuò eseguire una sequenza di altri command.
 * I command da eseguire vengono aggiunti tramite il metodo addCommand().
 * Quando viene richiamato il metodo doCommand() i command verranno eseguiti
 * nell'ordine in cui sono stati aggiunti.
 *
 * @author a.mattioli
 *
 */
public abstract class MultipleCommand implements Command {
	private ArrayList<Command> commands;
	private CommandEventSupport cmdEvtSupport = new CommandEventSupport();

	public MultipleCommand() {
		commands = new ArrayList<Command>(2);
	}

	/**
	 * Crea un command multiplo con capacità iniziale pari al numero indicato dal
	 * parametro. Se vengono aggiunti più command di quanto indicato nella capacità
	 * iniziale l'elenco verrà comunque ingrandito.
	 *
	 * @param capacity
	 */
	public MultipleCommand(int capacity) {
		commands = new ArrayList<Command>(capacity);
	}

	public void addCommand(Command c) {
		commands.add(c);
	}
	/*
	protected void setOnAll(String property, Object value) {
		if (commands.isEmpty()) {
			throw new IllegalStateException();
		}
		for (Iterator iter = commands.iterator(); iter.hasNext();) {
			Command curr = (Command)iter.next();
			try {
				PropertyUtils.setProperty(curr,property,value);
			} catch (IllegalAccessException e) {
				throw new IllegalArgumentException(e);
			} catch (InvocationTargetException e) {
				throw new IllegalArgumentException(e.getCause());
			} catch (NoSuchMethodException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	protected Object getFromFirst(String property) {
		if (commands.isEmpty()) {
			return null;
		} else {
			try {
				return PropertyUtils.getProperty(commands.get(0),property);
			} catch (IllegalAccessException e) {
				throw new IllegalArgumentException(e);
			} catch (InvocationTargetException e) {
				throw new IllegalArgumentException(e.getCause());
			} catch (NoSuchMethodException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}
	*/
	public void doCommand() throws ApplicationException {
		for (Command curr : commands) {
			curr.doCommand();
		}
		cmdEvtSupport.fireCommandEvent(new CommandEvent(this, CommandResult.SUCCESSFUL));
	}

	public void cancelCommand() {
		for (Command curr : commands) {
			curr.cancelCommand();
		}
	}

    public void addCommandListener(CommandListener listener) {
        cmdEvtSupport.addListener(listener);
    }

	public void addCommandListener(CommandListener listener, CommandResult... results) {
		cmdEvtSupport.addListener(listener, results);
	}

}
