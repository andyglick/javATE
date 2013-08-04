package it.amattioli.applicate.sessions;

import java.util.ArrayList;
import java.util.Collection;

public class CompositeServiceEnhancer implements ServiceEnhancer {
	private Collection<ServiceEnhancer> enhancers = new ArrayList<ServiceEnhancer>();
	private ApplicateSession session;
	
	@Override
	public void setSession(ApplicateSession session) {
		this.session = session;
		setSessionToEnhancers();
	}

	private void setSessionToEnhancers() {
		for (ServiceEnhancer enhancer: enhancers) {
			enhancer.setSession(session);
		}
	}
	
	public void setEnhancers(Collection<ServiceEnhancer> enhancers) {
		this.enhancers = enhancers;
		setSessionToEnhancers();
	}
	
	public void addEnhancer(ServiceEnhancer enhancer) {
		enhancer.setSession(session);
		enhancers.add(enhancer);
	}

	@Override
	public void customizeService(Object newService) {
		for (ServiceEnhancer enhancer: enhancers) {
			enhancer.customizeService(newService);
		}
	}

}
