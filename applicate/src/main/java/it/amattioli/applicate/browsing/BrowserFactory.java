package it.amattioli.applicate.browsing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class BrowserFactory {
    private static final Logger logger = LoggerFactory.getLogger(BrowserFactory.class);

    private Map<Class<? extends Entity<?>>, Class<? extends ListBrowser<?, ?>>> browsersClasses = new HashMap<Class<? extends Entity<?>>, Class<? extends ListBrowser<?, ?>>>();

    public <J extends Serializable, E extends Entity<J>> void registerBrowser(Class<E> entityClass, Class<? extends ListBrowser<J, E>> browserClass) {
        browsersClasses.put(entityClass, browserClass);
    }

    public <J extends Serializable, E extends Entity<J>> ListBrowser<J, E> createBrowser(Class<E> c, Repository<J, E> rep) {
        Class<ListBrowser<J, E>> browserClass = (Class<ListBrowser<J, E>>)browsersClasses.get(c);
        if (browserClass == null) {
            return new ListBrowserImpl<J, E>(rep);
        } else {
            try {
                //return (Browser<J, E>)browserClass.newInstance();
                Constructor<ListBrowser<J, E>> constr = browserClass.getConstructor(Repository.class);
                return constr.newInstance(rep);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
