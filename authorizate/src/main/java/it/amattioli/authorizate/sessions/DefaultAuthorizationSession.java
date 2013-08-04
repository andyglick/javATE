package it.amattioli.authorizate.sessions;

import it.amattioli.authorizate.AuthorizationManager;
import it.amattioli.authorizate.users.User;
import it.amattioli.authorizate.users.UserRepository;
import it.amattioli.dominate.RepositoryRegistry;

public class DefaultAuthorizationSession extends AuthorizationSession {
	private static Class<? extends User> userClass;
	
	public static Class<? extends User> getUserClass() {
		return userClass;
	}

	public static void setUserClass(Class<? extends User> userClass) {
		DefaultAuthorizationSession.userClass = userClass;
	}

	private User user;
	private AuthorizationManager manager;
	
	@Override
	public AuthorizationManager getAuthorizationManager() {
		return manager;
	}
	
	public void setAuthorizationManager(AuthorizationManager manager) {
		this.manager = manager;
	}

	@Override
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setUser(String userName) {
		this.user = ((UserRepository)RepositoryRegistry.instance().getRepository(userClass)).getByName(userName);
	}

}
