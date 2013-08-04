package it.amattioli.applicate.browsing;

import it.amattioli.applicate.commands.CommandEvent;
import it.amattioli.applicate.commands.CommandListener;
import it.amattioli.applicate.selection.SelectionEvent;
import it.amattioli.applicate.selection.SelectionListener;
import it.amattioli.applicate.selection.SelectionSupport;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.RepositoryRegistry;
import it.amattioli.dominate.memory.CollectionRepository;
import it.amattioli.dominate.memory.EmptyRepository;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import it.amattioli.dominate.properties.PropertyChangeSupport;
import it.amattioli.dominate.properties.PropertyClass;
import it.amattioli.dominate.properties.PropertyClassRetriever;
import it.amattioli.dominate.properties.PropertyClassRetrieverImpl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.WrapDynaBean;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author a.mattioli
 *
 * @param <I>
 * @param <T>
 */
public class ObjectBrowserImpl<I extends Serializable, T extends Entity<I>>
    implements ObjectBrowser<I, T>, DynaBean, PropertyClassRetriever {
    private static final Logger logger = LoggerFactory.getLogger(ObjectBrowserImpl.class);
    private boolean released = false;
    private T hold;
    private DynaBean wrapper;
    private Repository<I, T> repository;
    private Collection<CommandListener> commandListeners = new ArrayList<CommandListener>();
    private PropertyChangeSupport pChange = new PropertyChangeSupport(this);
    private SelectionSupport selectionSupport = new SelectionSupport();
    private ContentChangeSupport contentChangeSupport = new ContentChangeSupport();
    private Map<String, ListBrowserImpl<?,?>> detailsBrowsers = LazyMap.decorate(new HashMap<String, ListBrowserImpl<?,?>>(),
	    		                                                            new Transformer() {

																				@Override
																				public ListBrowserImpl<?,?> transform(Object key) {
																					return createDetailsBrowser((String)key);
																				}

                                                                            });
    private BrowserFactory browserFactory = new BrowserFactory();
    private boolean reentrant = false;

    public ObjectBrowserImpl() {
    	
    }
    
    public ObjectBrowserImpl(T hold, Repository<I, T> repository) {
        setHold(hold);
        this.repository = repository;
    }

    public ObjectBrowserImpl(I holdId, Repository<I, T> repository) {
    	this.repository = repository;
    	setHoldId(holdId);
    }
    
    public ObjectBrowserImpl(Repository<I, T> repository) {
    	setHold(null);
        this.repository = repository;
    }
    
    public ObjectBrowserImpl(Class<T> entityClass) {
    	this(RepositoryRegistry.instance().getRepository(entityClass));
    }

    protected Repository<I, T> getRepository() {
        return repository;
    }

    /* (non-Javadoc)
	 * @see it.maticasrl.applicate.browsing.PlaceHolder#objectSelected(it.maticasrl.applicate.browsing.BrowserSelectionEvent)
	 */
    public void objectSelected(SelectionEvent event) {
    	if (!released) {
	        logger.debug("Object selected");
	        setHold(((ListBrowser<I, T>) event.getSource()).getSelectedObject());
	        logger.debug("Property change to be fired");
	        pChange.firePropertyChange(null, null, this.hold);
	        logger.debug("Property change fired");
	        selectionSupport.notifySelectionListeners(new SelectionEvent(this));
	        logger.debug("Selection fired");
    	}
    }

    /* (non-Javadoc)
	 * @see it.maticasrl.applicate.browsing.PlaceHolder#propertyChange(java.beans.PropertyChangeEvent)
	 */
    public void propertyChange(PropertyChangeEvent event) {
    	if (!released) {
	        if ("list".equals(event.getPropertyName())) {
	            logger.debug("List changed");
	            setHold(((ListBrowser<I, T>) event.getSource()).getSelectedObject());
	            logger.debug("Property change to be fired");
	            pChange.firePropertyChange(null, null, this.hold);
	            logger.debug("Property change fired");
	            fireContentChange();
	            logger.debug("Content change fired");
	            selectionSupport.notifySelectionListeners(new SelectionEvent(this));
	            logger.debug("Selection fired");
	        }
    	}
    }

    /* (non-Javadoc)
	 * @see it.maticasrl.applicate.browsing.PlaceHolder#getHold()
	 */
    public T getHold() {
        return hold;
    }

    private void setHold(T hold) {
    	this.hold = hold;
    	if (hold == null) {
    		this.wrapper = null;
    	} else {
    		this.wrapper = new WrapDynaBean(hold);
    	}
    	pChange.firePropertyChange(null, null, this.hold);
        fireContentChange();
    }
    
    public I getHoldId() {
    	if (hold == null) {
    		return null;
    	}
    	return hold.getId();
    }
    
    public void setHoldId(I holdId) {
    	if (holdId == null) {
    		setHold(null);
    	} else {
    		setHold(repository.get(holdId));
    	}
    }

    /* (non-Javadoc)
	 * @see it.maticasrl.applicate.browsing.PlaceHolder#addCommandListener(it.maticasrl.applicate.commands.CommandListener)
	 */
    public void addCommandListener(CommandListener listener) {
        commandListeners.add(listener);
    }

    /* (non-Javadoc)
	 * @see it.maticasrl.applicate.browsing.PlaceHolder#refresh()
	 */
    public void refresh() {
        if (this.hold != null) {
            logger.debug("Refreshing " + this.hold);
            I holdId = this.hold.getId();
            repository.refresh(this.hold);
            setHold(repository.get(holdId));
            detailsBrowsers.clear();
            additionalRefresh();
            pChange.firePropertyChange(null, null, this.hold);
            fireContentChange();
        }
    }

    protected void additionalRefresh() {
        // Do nothing!
    }

    /* (non-Javadoc)
	 * @see it.maticasrl.applicate.browsing.PlaceHolder#commandDone(it.maticasrl.applicate.commands.CommandEvent)
	 */
    public void commandDone(CommandEvent source) {
    	if (!reentrant && !released) {
    		reentrant = true;
    		try {
	    		logger.debug("Command done for " + this.hold);
	            refresh();
	            for (CommandListener curr : commandListeners) {
	                curr.commandDone(source);
	            }
    		} finally {
    			reentrant = false;
    		}
    	}
    }

    /* (non-Javadoc)
	 * @see it.maticasrl.applicate.browsing.PlaceHolder#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pChange.addPropertyChangeListener(listener);
    }

    /* (non-Javadoc)
	 * @see it.maticasrl.applicate.browsing.PlaceHolder#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pChange.removePropertyChangeListener(listener);
    }

    protected <J extends Serializable, E extends Entity<J>> void registerDetailBrowser(Class<E> entityClass, Class<? extends ListBrowser<J, E>> browserClass) {
        browserFactory.registerBrowser(entityClass, browserClass);
    }

    protected <J extends Serializable, E extends Entity<J>> Repository<J, E> getDetailsRepository(String propertyName,
            Class<E> c) {
        if (getHold() != null) {
            Collection<E> details;
            try {
                details = (Collection<E>) PropertyUtils.getProperty(getHold(), propertyName);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            } catch (InvocationTargetException e) {
                throw new IllegalArgumentException(e);
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException("Unknown property \"" + propertyName + "\" for class "
                        + getHold().getClass());
            }
            //return getRepository().getDetailRepository(details);
            return new CollectionRepository<J, E>(details);
        } else {
            return new EmptyRepository<J, E>();
        }
    }
    /*
    public <J extends Serializable, E extends Entity<J>> ListBrowser<J, E> getDetailsBrowser(String propertyName, Class<E> c) {
        ListBrowserImpl<J, E> browser = detailsBrowsers.get(propertyName);
        if (browser == null) {
            browser = createDetailsBrowser(propertyName, c);
        }
        return browser;
    }
    */
    private ListBrowserImpl createDetailsBrowser(String propertyName) {
    	if (hold == null) {
    		return new ListBrowserImpl(new EmptyRepository());
    	}
    	PropertyClassRetriever retriever = new PropertyClassRetrieverImpl(hold);
    	PropertyClass pClass = retriever.retrievePropertyClass(propertyName);
    	if (pClass.isMultiple()) {
    		return createDetailsBrowser(propertyName, (Class<Entity>)pClass.getElementClass());
    	} else {
    		return null;
    	}
    }

	private <J extends Serializable, E extends Entity<J>> ListBrowserImpl<J, E> createDetailsBrowser(String propertyName, Class<E> c) {
		ListBrowserImpl<J, E> browser = (ListBrowserImpl<J, E>)browserFactory.createBrowser(c, getDetailsRepository(propertyName, c));
		detailsBrowsers.put(propertyName, browser);
		addPropertyChangeListener(new DetailsUpdater<J, E>(propertyName, browser, c));
		return browser;
	}

    public Map<String, ListBrowserImpl<?,?>> getDetailsBrowsers() {
		return detailsBrowsers;
	}
    
    public ListBrowserImpl<?,?> getDetailsBrowser(String propertyName) {
    	return getDetailsBrowsers().get(propertyName);
    }

	private class DetailsUpdater<J extends Serializable, E extends Entity<J>> implements PropertyChangeListener {
        private ListBrowserImpl<J, E> detailsBrowser;
        private Class<E> entityClass;
        private String propertyName;

        public DetailsUpdater(String propertyName, ListBrowserImpl<J, E> detailsBrowser, Class<E> c) {
            this.propertyName = propertyName;
            this.detailsBrowser = detailsBrowser;
            this.entityClass = c;
        }

        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName() == null || "hold".equals(evt.getPropertyName())) {
                detailsBrowser.setRepository(((ObjectBrowserImpl<J, E>) evt.getSource()).getDetailsRepository(propertyName,
                        this.entityClass));
            }
        }

    }

    /* (non-Javadoc)
	 * @see it.maticasrl.applicate.browsing.PlaceHolder#release()
	 */
	@Override
    public void release() {
    	released = true;
    	pChange.disable();
    	contentChangeSupport.disable();
    }

    private void checkNullWrapper() {
		if (wrapper == null) {
			throw new IllegalStateException();
		}
	}

    public boolean contains(String name, String key) {
		if (wrapper == null) {
			return false;
		}
		return wrapper.contains(name, key);
	}

	public Object get(String name, int index) {
		if (wrapper == null) {
			return null;
		}
		if (existsCustomGetter(name)) {
			return customGet(name, index);
		} else {
			return wrapper.get(name, index);
		}
	}

	public Object get(String name, String key) {
		if (wrapper == null) {
			return null;
		}
		if (existsCustomGetter(name)) {
			return customGet(name, key);
		} else {
			return wrapper.get(name, key);
		}
	}

	public Object get(String name) {
		if (wrapper == null) {
			return null;
		}
		if (existsCustomGetter(name)) {
			return customGet(name);
		} else {
			return wrapper.get(name);
		}
	}

	public DynaClass getDynaClass() {
		if (wrapper == null) {
			DynaProperty[] props = new DynaProperty[1];
			props[0] = new DynaProperty("id", Object.class);
			return new BasicDynaClass("ObjectBrowserDynaClass", null, props);
		}
		return wrapper.getDynaClass();
	}

	public void remove(String name, String key) {
		throw new UnsupportedOperationException();
	}

	public void set(String name, int index, Object value) {
		throw new UnsupportedOperationException();
	}

	public void set(String name, Object value) {
		if ("id".equals(name)) {
			setHoldId((I)value);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	public void set(String name, String key, Object value) {
		throw new UnsupportedOperationException();
	}

	private boolean existsCustomGetter(String property) {
		try {
			getClass().getMethod("get" + StringUtils.capitalize(property));
			return true;
		} catch (NoSuchMethodException e) {
			return false;
		}
	}

	private Object customGet(String property) {
		try {
			return getClass().getMethod("get" + StringUtils.capitalize(property)).invoke(this);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	private Object customGet(String property, int i) {
		Object[] arr = (Object[])customGet(property);
		return arr[i];
	}

	private Object customGet(String property, String key) {
		Map<String, ?> map = (Map<String, ?>)customGet(property);
		return map.get(key);
	}

	@Override
	public PropertyClass retrievePropertyClass(String propertyName) {
		if (getHold() == null) {
			return null;
		}
		return new PropertyClassRetrieverImpl(getHold()).retrievePropertyClass(propertyName);
	}
	
	public T getSelectedObject() {
		return getHold();
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		selectionSupport.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		selectionSupport.removeSelectionListener(listener);
	}

	@Override
	public void addContentChangeListener(ContentChangeListener listener) {
		contentChangeSupport.addContentChangeListener(listener);
	}

	@Override
	public void removeContentChangeListener(ContentChangeListener listener) {
		contentChangeSupport.removeContentChangeListener(listener);
	}
	
	protected void fireContentChange() {
		contentChangeSupport.notifyContentChangeListeners(new ContentChangeEvent(this));
	}
	
	@Override
	public String toString() {
		return "ObjectBrowserImpl[hold=" + hold + "]";
	}

    /*
     * public void clear() { throw new UnsupportedOperationException(); }
     *
     * public boolean containsKey(Object key) { try { get((String)key); return
     * true; } catch(IllegalArgumentException e) { return false; } }
     *
     * public boolean containsValue(Object value) { throw new
     * UnsupportedOperationException(); }
     *
     * public Set entrySet() { DynaProperty[] props =
     * getDynaClass().getDynaProperties(); Set result = new
     * HashSet(props.length); for (int i=0; i<props.length; i++) { String name =
     * props[i].getName(); Map.Entry val = new MyEntry(name,get(name));
     * result.add(val); } return result; }
     *
     * public Object get(Object key) { return get((String)key); }
     *
     * public boolean isEmpty() { return false; }
     *
     * public Set keySet() { DynaProperty[] props =
     * getDynaClass().getDynaProperties(); Set result = new
     * HashSet(props.length); for (int i=0; i<props.length; i++) {
     * result.add(props[i].getName()); } return result; }
     *
     * public Object put(Object key, Object value) { Object old =
     * get((String)key); set((String)key,value); return old; }
     *
     * public void putAll(Map t) { Set entries = t.entrySet(); for (Iterator
     * iter = entries.iterator(); iter.hasNext();) { Map.Entry curr =
     * (Map.Entry)iter.next(); put(curr.getKey(),curr.getValue()); }
     *  }
     *
     * public Object remove(Object key) { throw new
     * UnsupportedOperationException(); }
     *
     * public int size() { return getDynaClass().getDynaProperties().length; }
     *
     * public Collection values() { DynaProperty[] props =
     * getDynaClass().getDynaProperties(); Collection result = new
     * ArrayList(props.length); for (int i=0; i<props.length; i++) {
     * result.add(get(props[i].getName())); } return result; }
     *
     * private class MyEntry implements Map.Entry { private Object key; private
     * Object value;
     *
     * public MyEntry(Object key, Object value) { this.key = key; this.value =
     * value; }
     *
     * public Object getKey() { return key; }
     *
     * public Object getValue() { return value; }
     *
     * public Object setValue(Object value) { Object old = getValue();
     * this.value = value; put(getKey(),value); return old; }
     *  }
     */

}
