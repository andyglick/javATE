package it.amattioli.guidate.containers;

import it.amattioli.applicate.commands.ApplicationException;
import it.amattioli.applicate.commands.Command;
import it.amattioli.guidate.util.CustomMessagebox;
import it.amattioli.guidate.util.DesktopAttributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import static org.zkoss.zk.ui.event.Events.*;
import org.zkoss.zk.ui.event.KeyEvent;

import static it.amattioli.guidate.validators.ValidatingComposer.ON_VALIDATE_ELEMENT;

public class CommandComposer extends BackBeanComposer {
	private static final Logger logger = LoggerFactory.getLogger(CommandComposer.class);

    @Override
    public void doAfterCompose(Component comp) throws Exception {
    	super.doAfterCompose(comp);
    }
    
    public void onOK(Event evt) throws Exception {
    	if (evt instanceof KeyEvent) {
	    	Component reference = ((KeyEvent)evt).getReference();
	    	sendEvent(ON_BLUR, reference, null);
    	}
    	Component comp = evt.getTarget();
    	Command command = (Command)BackBeans.findBackBean(comp);
    	sendEvent(ON_VALIDATE_ELEMENT, comp, null);
    	try {
    		DesktopAttributes.getSession(comp).execute(command);
            afterCommand();
        } catch(ApplicationException e) {
        	logger.error("Error", e);
            CustomMessagebox.show(e);
            postEvent(ON_CLOSE, self, null);
        }
    }

	protected void afterCommand() {
		postEvent(ON_CLOSE, self, null);
	}
    
    public void onCancel(Event evt) {
    	Component comp = evt.getTarget();
    	Command command = (Command)BackBeans.findBackBean(comp);
        command.cancelCommand();
        afterCommand();
    }
    
    public void onApplyCommand(Event evt) throws Exception {
    	Component comp = evt.getTarget();
    	Command command = (Command)BackBeans.findBackBean(comp);
    	sendEvent(ON_VALIDATE_ELEMENT, comp, null);
    	try {
    		command.doCommand();
        } catch(ApplicationException e) {
        	logger.error("Error", e);
            CustomMessagebox.show(e);
            postEvent(ON_CLOSE, self, null);
        }
    }

}
