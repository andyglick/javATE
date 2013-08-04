package it.amattioli.guidate.wizard;

import it.amattioli.workstate.core.State;
import it.amattioli.workstate.info.Receiver;
import it.amattioli.workstate.info.Visitor;

public class WorkflowPageRetriever implements Visitor {
	private String url;
	
	@Override
	public void visit(Receiver receiver) {
		try {
			url = (String)((State)receiver).getAttribute("url");
			if (url != null) {
				return;
			}
		} catch(IllegalArgumentException e) {
			//Means this state has no url attribute
		}
		receiver.receive(this);
	}
	
	public String getPageUrl() {
		return url;
	}

}
