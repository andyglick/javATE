package it.amattioli.applicate.sessions;

import it.amattioli.applicate.commands.Command;
import it.amattioli.applicate.commands.CommandExecutor;
import it.amattioli.applicate.commands.CommandListener;
import it.amattioli.applicate.commands.executors.SimpleExecutor;
import it.amattioli.applicate.serviceFactory.ServiceFactory;

import static it.amattioli.applicate.commands.CommandResult.*;

public class ApplicateSession {
	private CommandEventTopic cmdTopic = new CommandEventTopic();
	private ServiceFactory serviceFactory;
	private CommandExecutor cmdExecutor = new SimpleExecutor();
	private ServiceEnhancer enhancer;
	
	public ApplicateSession() {
		setEnhancer(new DefaultServiceEnhancer());
	}
	
	public Object createService(String serviceName) {
	    Object newService = serviceFactory.createService(serviceName);
	    customizeService(newService);
        return newService;
	}
	
	protected void customizeService(Object newService) {
		enhancer.customizeService(newService);
	}
	
	public void setServiceFactory(ServiceFactory serviceFactory) {
	    this.serviceFactory = serviceFactory;
	}
	
	public void setEnhancer(ServiceEnhancer enhancer) {
		this.enhancer = enhancer;
		enhancer.setSession(this);
	}
	
	protected void registerCommand(Command cmd) {
		cmd.addCommandListener(cmdTopic, SUCCESSFUL, UNSUCCESSFUL);
	}
	
	public void registerCommandListener(CommandListener listener) {
		cmdTopic.addCommandListener(listener);
	}
	
	public void setCommandExecutor(CommandExecutor cmdExecutor) {
		this.cmdExecutor = cmdExecutor;
	}
	
	public void execute(Command cmd) {
		cmdExecutor.execute(cmd);
	}
	
	public void release() {
		
	}
}
