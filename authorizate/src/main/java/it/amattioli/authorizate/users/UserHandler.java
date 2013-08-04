package it.amattioli.authorizate.users;


/**
 * This interface can be implemented by a class that needs an attribute of
 * class User.
 * 
 * @author a.mattioli
 *
 */
public interface UserHandler {
	
	public void setUser(User user);
	
	public User getUser();

}
