package it.amattioli.applicate.context;

import it.amattioli.applicate.commands.ApplicationException;
import it.amattioli.applicate.commands.Command;
import it.amattioli.applicate.commands.CommandEvent;
import it.amattioli.applicate.commands.CommandEventSupport;
import it.amattioli.applicate.commands.CommandListener;
import it.amattioli.applicate.commands.CommandResult;
import it.amattioli.applicate.commands.ConcurrencyException;
import it.amattioli.applicate.commands.Resettable;
import it.amattioli.dominate.sessions.ConnectionException;
import it.amattioli.dominate.sessions.SessionManager;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.collections.Closure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionalCommandContext extends LongRunningContext {
	private static final Logger logger = LoggerFactory.getLogger(TransactionalCommandContext.class);
	private Command decorated;
	private CommandEventSupport cmdEvtSupport = new CommandEventSupport();
	
	public static <T extends Command> T newTransaction(T command) {
		return newTransaction(command, new TransactionalCommandContext(command));
	}
	
	public static <T extends Command> T newTransaction(T command, SessionManager sMgr) {
		return newTransaction(command, new TransactionalCommandContext(command,sMgr));
	}
	
	private static <T extends Command> T newTransaction(T command, TransactionalCommandContext callback) {
		return LongRunningContext.newLongRunningService(command, callback);
	}

    public TransactionalCommandContext(Command decorated) {
    	super(decorated);
        this.decorated = decorated;
    }
    
    public TransactionalCommandContext(Command decorated, SessionManager sMgr) {
    	super(decorated, sMgr);
        this.decorated = decorated;
    }

    private void doCommand(Command invocatedCmd) throws ApplicationException {
    	Closure toBeDone = new Closure() {
			
			@Override
			public void execute(Object input) {
				decorated.doCommand();
			}

		};
		toBeDone = getSessionManager().transactionalClosure(toBeDone);
		CommandEvent evt = null;
		try {
			toBeDone.execute(null);
			evt = new CommandEvent(invocatedCmd, CommandResult.SUCCESSFUL);
		} catch(it.amattioli.dominate.sessions.ConcurrencyException e) {
			evt = new CommandEvent(invocatedCmd, CommandResult.UNSUCCESSFUL, e);
            throw new ConcurrencyException(e.getEntityName());
		} catch(ConnectionException e) {
			logger.debug("Database connection problems, transaction cannot be committed nor rolled back");
        	evt = new CommandEvent(invocatedCmd, CommandResult.UNSUCCESSFUL, e);
            throw e;
		} catch (RuntimeException e) {
            evt = new CommandEvent(invocatedCmd, CommandResult.UNSUCCESSFUL, e);
            throw e;
		} catch (Throwable e) {
            evt = new CommandEvent(invocatedCmd, CommandResult.UNSUCCESSFUL, e);
            throw new RuntimeException(e);
        } finally {
        	cmdEvtSupport.fireCommandEvent(evt);
        	if (invocatedCmd instanceof Resettable && ((Resettable)invocatedCmd).toBeReset()) {
        		reset();
        	} else {
        		getSessionManager().release();
        	}
        }
    }

    private void cancelCommand(Command invocatedCmd) {
    	try {
    		decorated.cancelCommand();
	    } finally {
			cmdEvtSupport.fireCommandEvent(new CommandEvent(invocatedCmd, CommandResult.CANCELLED));
			if (invocatedCmd instanceof Resettable && ((Resettable)invocatedCmd).toBeReset()) {
        		reset();
        	} else {
        		getSessionManager().release();
        	}
		}
    }

    private void addCommandListener(CommandListener listener) {
    	logger.debug("Registering transactional command listener " + listener);
    	cmdEvtSupport.addListener(listener);
    }

    private void addCommandListener(CommandListener listener, CommandResult... results) {
    	logger.debug("Registering transactional command listener " + listener);
    	cmdEvtSupport.addListener(listener, results);
    }
    
    private void reset() {
    	getSessionManager().reset();
		((Resettable)decorated).reset();
    }

	public Object perform(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		if ("doCommand".equals(method.getName())) {
			doCommand((Command) object);
			return null;
		} else if ("cancelCommand".equals(method.getName())) {
			cancelCommand((Command)object);
			return null;
		} else if ("addCommandListener".equals(method.getName())
					&& method.getParameterTypes().length == 2) {  //ADD_SPECIFIC_LSNR_PARMS.equals(method.getParameterTypes())) {
			addCommandListener((CommandListener) args[0], (CommandResult[]) args[1]);
			return null;
		} else if ("addCommandListener".equals(method.getName())
					&& method.getParameterTypes().length == 1) {  //ADD_GENERIC_LSNR_PARMS.equals(method.getParameterTypes())) {
			addCommandListener((CommandListener) args[0]);
			return null;
		} else if ("reset".equals(method.getName()) && object instanceof Resettable) {
			reset();
			return null;
		} else {
			return method.invoke(decorated, args);
		}
	}

}