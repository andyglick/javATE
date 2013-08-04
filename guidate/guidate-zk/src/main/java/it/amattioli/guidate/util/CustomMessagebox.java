package it.amattioli.guidate.util;

import it.amattioli.applicate.commands.ApplicationException;
//import it.amattioli.common.exceptions.KeyedMessage;
//import it.amattioli.common.exceptions.MessageBundle;

import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

public class CustomMessagebox {
	
	public static void show(ApplicationException e) throws InterruptedException {
		Desktop desktop = Executions.getCurrent().getDesktop();
//		MessageBundle bundle = (MessageBundle)desktop.getAttribute(DesktopAttributes.MESSAGE_BUNDLE);
//		String msg = "";
//		if (bundle == null) {
//			msg = e.getMessage();
//		} else {
//			msg = bundle.getErrorMessage(e.getKeyedMessage());
//		}
		Messagebox.show(e.getMessage(), null, Messagebox.OK, Messagebox.ERROR);
	}
	
//	public static void show(String msgKey) throws InterruptedException {
//	    Desktop desktop = Executions.getCurrent().getDesktop();
//        MessageBundle bundle = (MessageBundle)desktop.getAttribute(DesktopAttributes.MESSAGE_BUNDLE);
//        Messagebox.show(bundle.getErrorMessage(new KeyedMessage(msgKey)),
//                        null,
//                        Messagebox.OK,
//                        Messagebox.ERROR);
//	}
	
}
