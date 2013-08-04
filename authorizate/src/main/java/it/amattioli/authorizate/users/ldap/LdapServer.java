package it.amattioli.authorizate.users.ldap;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LdapServer {
	private static final String ROLE_BASE_KEY = "roleBase";
	private static final String USER_BASE_KEY = "userBase";
	private static String url = "ldap://localhost:10389";
	private static String username = "uid=admin,ou=system";
	private static String password = "secret";
	private static String userBase = "ou=Users,dc=example,dc=com";
	private static String roleBase = "ou=Groups,dc=example,dc=com";
	private static ThreadLocal<DirContext> currCtx = new ThreadLocal<DirContext>();
	private static Hashtable env;
	
	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		LdapServer.url = url;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		LdapServer.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		LdapServer.password = password;
	}

	public static String getUserBase() {
		if (getContextEnvironment().containsKey(USER_BASE_KEY)) {
			return (String)getContextEnvironment().get(USER_BASE_KEY);
		}
		return userBase;
	}

	public static void setUserBase(String userBase) {
		LdapServer.userBase = userBase;
	}

	public static String getRoleBase() {
		if (getContextEnvironment().containsKey(ROLE_BASE_KEY)) {
			return (String)getContextEnvironment().get(ROLE_BASE_KEY);
		}
		return roleBase;
	}

	public static void setRoleBase(String roleBase) {
		LdapServer.roleBase = roleBase;
	}

	public static synchronized Hashtable getContextEnvironment() {
		if (env == null) {
			InputStream propertiesStream = LdapServer.class.getResourceAsStream("config.properties");
			if (propertiesStream != null) {
				Properties props = new Properties();
				try {
					props.load(propertiesStream);
					env = props;
				} catch (IOException e) {
					createEnvTable();
				}
			} else {
				createEnvTable();
			}
		}
		return env;
	}

	private static void createEnvTable() {
		env = new Hashtable();
		String sp = "com.sun.jndi.ldap.LdapCtxFactory";
		env.put(Context.INITIAL_CONTEXT_FACTORY, sp);
		env.put(Context.PROVIDER_URL,getUrl());
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put("com.sun.jndi.ldap.connect.pool", "true");
		env.put(Context.SECURITY_PRINCIPAL, getUsername());
		env.put(Context.SECURITY_CREDENTIALS, getPassword());
	}
	
	public static DirContext getServerContext() {
		try {
		  DirContext dctx = currCtx.get();
		  if (dctx == null) {
			  Hashtable env = getContextEnvironment();
			  dctx = new InitialDirContext(env);
			  currCtx.set(dctx);
		  }
		  return dctx;
		} catch(NamingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void releaseContext() {
		DirContext dctx = currCtx.get();
		if (dctx != null) {
			currCtx.set(null);
			try {
				dctx.close();
			} catch (NamingException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
