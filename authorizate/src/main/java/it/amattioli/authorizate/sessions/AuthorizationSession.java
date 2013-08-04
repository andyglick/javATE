package it.amattioli.authorizate.sessions;

import it.amattioli.applicate.sessions.ApplicateSession;
import it.amattioli.authorizate.AuthorizationManager;
import it.amattioli.authorizate.users.User;
import it.amattioli.authorizate.users.UserHandler;

public abstract class AuthorizationSession extends ApplicateSession {

	public boolean checkRule(String operation, Object subject) {
		return getAuthorizationManager().checkRule(getUser(), operation, subject);
	}

	@Override
	protected void customizeService(Object newService) {
		if (newService instanceof UserHandler) {
			((UserHandler)newService).setUser(getUser());
		}
		super.customizeService(newService);
	}

	public abstract User getUser();

	protected abstract AuthorizationManager getAuthorizationManager();

}
