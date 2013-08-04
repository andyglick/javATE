package it.amattioli.guidate.init;

import it.amattioli.applicate.serviceFactory.ServiceFactory;
import it.amattioli.applicate.sessions.ApplicateSession;
import it.amattioli.applicate.sessions.Application;
import it.amattioli.authorizate.sessions.DefaultAuthorizationSession;
import it.amattioli.authorizate.users.User;
import it.amattioli.dominate.RepositoryRegistry;
import it.amattioli.guidate.config.GuidateConfig;

public class GuidateApplication implements Application {
	
	@Override
	public void init() {
		RepositoryRegistry.instance().setRepositoryFactoryClass(GuidateConfig.instance.getRepositoryFactoryClass());
		Class<? extends User> userClass = GuidateConfig.instance.getUserClass();
		if (userClass != null) {
			DefaultAuthorizationSession.setUserClass(userClass);
		}
	}

	@Override
	public ApplicateSession createSession() {
		try {
			ApplicateSession session = GuidateConfig.instance.getApplicateSessionClass().newInstance();
	    	ServiceFactory serviceFactory = GuidateConfig.instance.getServiceFactoryClass().newInstance();
	    	session.setServiceFactory(serviceFactory);
			return session;
		} catch(InstantiationException ie) {
			throw new RuntimeException(ie);
		} catch(IllegalAccessException ae) {
			throw new RuntimeException(ae);
		}
	}

}
