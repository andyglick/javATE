package it.amattioli.applicate.sessions;

import it.amattioli.applicate.commands.Command;
import it.amattioli.applicate.commands.CommandListener;

public class DefaultServiceEnhancer extends AbstractServiceEnhancer {
	
	@Override
	public void customizeService(Object newService) {
		if (newService instanceof Command) {
	        getSession().registerCommand((Command)newService);
	    }
	    if (newService instanceof CommandListener) {
	        getSession().registerCommandListener((CommandListener)newService);
	    }
	}

}
