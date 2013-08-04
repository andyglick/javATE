package it.amattioli.applicate.serviceFactory;

/**
 * Implementations of this interface know how to create service objects
 *  
 * @author andrea
 *
 */
public interface ServiceFactory {

	/**
	 * Check if this factory is able to create an service object with the given name.
	 * 
	 * @param serviceName the name of the service
	 * @return true if this factory is able to create an service object with the given name
	 */
    public boolean knowsService(String serviceName);
    
    /**
     * Create a service object with the given name.
     * If this factory is not able to create such a service it will return null.
     * 
     * @param serviceName the name of the service to be created
     * @return the service or null if this factory is not able to create such a service
     */
    public Object createService(String serviceName);

}
