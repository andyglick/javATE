package it.amattioli.guidate.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.amattioli.applicate.serviceFactory.ServiceFactory;
import it.amattioli.applicate.sessions.ApplicateSession;
import it.amattioli.authorizate.AuthorizationManager;
import it.amattioli.authorizate.users.User;
import it.amattioli.dominate.RepositoryFactory;

public class GuidateConfig {

    private static final String APPLICATE_SESSION_VARIABLE_KEY = "it.amattioli.guidate.applicateSessionVariable";
    private static final String APPLICATE_SESSION_VARIABLE_DFT = "applicateSession";
	private static final String SERVICE_FACTORY_CLASS_KEY = "it.amattioli.guidate.serviceFactoryClass";
	private static final String SERVICE_FACTORY_CLASS_DFT = "it.amattioli.applicate.serviceFactory.VoidServiceFactory";
	private static final String APPLICATE_SESSION_CLASS_KEY = "it.amattioli.guidate.applicateSessionClass";
	private static final String APPLICATE_SESSION_CLASS_DFT = "it.amattioli.applicate.sessions.ApplicateSession";
	private static final String REPOSITORY_FACTORY_CLASS_KEY = "it.amattioli.guidate.repositoryFactoryClass";
	private static final String REPOSITORY_FACTORY_CLASS_DFT = "it.amattioli.dominate.hibernate.HibernateRepositoryFactory";
	private static final String SESSION_IN_DESKTOP_KEY = "it.amattioli.guidate.sessionInDesktop";
	private static final String USER_CLASS_KEY = "it.amattioli.guidate.userClass";
	private static final String USER_CLASS_DFT = "it.amattioli.authorizate.users.DefaultUser";
	private static final String AUTH_MANAGER_CLASS_KEY = "it.amattioli.guidate.authManagerClass";
	private static final String AUTH_MANAGER_CLASS_DFT = "it.amattioli.authorizate.DefaultAuthorizationManager";

	private static final Logger logger = LoggerFactory.getLogger(GuidateConfig.class);
	
	public static final GuidateConfig instance = new GuidateConfig();
	
	private Class<? extends RepositoryFactory> repositoryFactoryClass;
	private Class<? extends ApplicateSession> applicateSessionClass;
	private Class<? extends ServiceFactory> serviceFactoryClass;
	private String applicateSessionVariable;
	private Boolean sessionInDesktop;
	private Class<? extends User> userClass;
	private Class<? extends AuthorizationManager> authManagerClass;

	private Properties configProps;
	private String propertiesFileName = "config.properties";
	
	private GuidateConfig() {
	}
	
	private Properties getConfigProperties() {
		if (configProps == null) {
			configProps = new Properties();
			try {
				InputStream propertiesStream = GuidateConfig.class.getResourceAsStream(getPropertiesFileName());
				if (propertiesStream == null) {
					throw new FileNotFoundException(getPropertiesFileName());
				}
				configProps.load(propertiesStream);
			} catch (IOException e) {
				// if cannot load properties use default values
				logger.warn("Cannot load properties. Using default values", e);
			}
		}
		return configProps;
	}

	public String getPropertiesFileName() {
		return propertiesFileName;
	}
	
	public void setPropertiesFileName(String propertiesFileName) {
		this.propertiesFileName = propertiesFileName;
		this.configProps = null;
		this.repositoryFactoryClass = null;
		this.applicateSessionClass = null;
		this.serviceFactoryClass = null;
		this.applicateSessionVariable = null;
		this.sessionInDesktop = null;
		this.userClass = null;
		this.authManagerClass = null;
	}

	public Class<? extends RepositoryFactory> getRepositoryFactoryClass() {
		if (repositoryFactoryClass == null) {
			String className = getConfigProperties().getProperty(REPOSITORY_FACTORY_CLASS_KEY,REPOSITORY_FACTORY_CLASS_DFT);
			try {
				repositoryFactoryClass = (Class<? extends RepositoryFactory>)Class.forName(className);
			} catch (ClassNotFoundException e) {
				throw new ConfigurationClassNotFound(className);
			}
		}
		return repositoryFactoryClass;
	}

	public Class<? extends ApplicateSession> getApplicateSessionClass() {
		if (applicateSessionClass == null) {
			String className = getConfigProperties().getProperty(APPLICATE_SESSION_CLASS_KEY,APPLICATE_SESSION_CLASS_DFT);
			try {
				applicateSessionClass = (Class<? extends ApplicateSession>)Class.forName(className);
			} catch (ClassNotFoundException e) {
				throw new ConfigurationClassNotFound(className);
			}
		}
		return applicateSessionClass;
	}

	public Class<? extends ServiceFactory> getServiceFactoryClass() {
		if (serviceFactoryClass == null) {
			String className = getConfigProperties().getProperty(SERVICE_FACTORY_CLASS_KEY,SERVICE_FACTORY_CLASS_DFT);
			try {
				serviceFactoryClass = (Class<? extends ServiceFactory>)Class.forName(className);
	        } catch (ClassNotFoundException e) {
	            throw new ConfigurationClassNotFound(className);
	        }
		}
        return serviceFactoryClass;
    }
	
	public String getApplicateSessionVariable() {
		if (applicateSessionVariable == null) {
			applicateSessionVariable = getConfigProperties().getProperty(APPLICATE_SESSION_VARIABLE_KEY,APPLICATE_SESSION_VARIABLE_DFT);
		}
		return applicateSessionVariable;
	}
	
	public boolean isSessionInDesktop() {
		if (sessionInDesktop == null) {
			String val = getConfigProperties().getProperty(SESSION_IN_DESKTOP_KEY);
			if (val != null) {
				sessionInDesktop = Boolean.valueOf(val);
			} else {
				sessionInDesktop = Boolean.TRUE;
			}
		}
		return sessionInDesktop;
	}
	
	public Class<? extends User> getUserClass() {
		if (userClass == null) {
			String className = getConfigProperties().getProperty(USER_CLASS_KEY,USER_CLASS_DFT);
			if (!StringUtils.isBlank(className)) {
				try {
					userClass = (Class<? extends User>)Class.forName(className);
		        } catch (ClassNotFoundException e) {
		            throw new ConfigurationClassNotFound(className);
		        }
			}
		}
        return userClass;
	}
	
	public Class<? extends AuthorizationManager> getAuthManagerClass() {
		if (authManagerClass == null) {
			String className = getConfigProperties().getProperty(AUTH_MANAGER_CLASS_KEY,AUTH_MANAGER_CLASS_DFT);
			try {
				authManagerClass = (Class<? extends AuthorizationManager>)Class.forName(className);
	        } catch (ClassNotFoundException e) {
	            throw new ConfigurationClassNotFound(className);
	        }
		}
        return authManagerClass;
	}
	
	public String getFormat(Class converter) {
		return configProps.getProperty(converter.getName()+".format","");
	}

}
