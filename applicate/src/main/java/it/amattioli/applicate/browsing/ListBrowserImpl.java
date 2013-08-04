package it.amattioli.applicate.browsing;

import it.amattioli.applicate.commands.CommandEvent;
import it.amattioli.applicate.commands.CommandEventSupport;
import it.amattioli.applicate.commands.CommandListener;
import it.amattioli.applicate.commands.CommandResult;
import it.amattioli.applicate.selection.SelectionEvent;
import it.amattioli.applicate.selection.SelectionListener;
import it.amattioli.applicate.selection.SelectionSupport;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.RepositoryRegistry;
import it.amattioli.dominate.Specification;
import it.amattioli.dominate.repositories.OrderList;
import it.amattioli.dominate.specifications.SpecificationChangeEvent;
import it.amattioli.dominate.specifications.SpecificationChangeListener;
import it.amattioli.dominate.specifications.beans.BeanSpecification;
import it.amattioli.dominate.properties.PropertyChangeEmitter;

import java.beans.PropertyChangeListener;
import it.amattioli.dominate.properties.PropertyChangeSupport;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Standard implementation of the {@link ListBrowser} interface.
 *
 * @author andrea
 *
 * @param <I> Identifier class for the browsing entities
 * @param <T> class of the browsing entities
 */
public class ListBrowserImpl<I extends Serializable, T extends Entity<I>> implements ListBrowser<I, T>, PropertyChangeEmitter {
    private static final Logger logger = LoggerFactory.getLogger(ListBrowserImpl.class);

    private Repository<I, T> repository;
    private Specification<T> specification;
    protected List<T> content = null;
    private boolean multipleSelection = false;
    private List<Integer> selectedIndexes = new ArrayList<Integer>();
    private OrderList orders = new OrderList();
    private PropertyChangeSupport pChange = new PropertyChangeSupport(this);
    private Class<? extends ObjectBrowser<I, T>> objectBrowserClass;
    private ObjectBrowser<I, T> objectBrowser;

    private SelectionSupport selectionSupport = new SelectionSupport();
    private CommandEventSupport cmdEventSupport = new CommandEventSupport();
    private ContentChangeSupport contentChangeSupport = new ContentChangeSupport();

	private SpecificationChangeListener specificationChangeListener = new SpecificationChangeListener() {

		@Override
		public void specificationChange(SpecificationChangeEvent event) {
			invalidateContent();
		}

    };
	
	/**
     * Factory method that creates a new repository given the class of the browsing entities.
     * 
     * @param entityClass the class of the browsing entities
     */
	public static <J extends Serializable, Q extends Entity<J>> ListBrowserImpl<J,Q> createFor(Class<Q> entityClass) {
		return new ListBrowserImpl<J,Q>(entityClass);
	}
	
	/**
     * Factory method that creates a new ListBrowser given the repository that contains the browsing entities.
     *
     * @param repository the repository that contains the browsing entities
     */
	public static <J extends Serializable, Q extends Entity<J>> ListBrowserImpl<J,Q> createFor(Repository<J, Q> repository) {
		return new ListBrowserImpl<J,Q>(repository);
	}

    /**
     * Create a new ListBrowser. Before using it a repository must be set using
     * {@link #setRepository(Repository)}
     */
    protected ListBrowserImpl() {

    }

    /**
     * Create a new ListBrowser given the repository that contains the browsing entities.
     *
     * @param repository the repository that contains the browsing entities
     */
    public ListBrowserImpl(Repository<I, T> repository) {
        this.repository = repository;
    }
    
    /**
     * Create a new repository given the class of the browsing entities.
     * 
     * @param entityClass the class of the browsing entities
     */
    public ListBrowserImpl(Class<T> entityClass) {
    	this(RepositoryRegistry.instance().getRepository(entityClass));
    	setSpecification(new BeanSpecification<T>(entityClass));
    }

    protected Repository<I, T> getRepository() {
        return this.repository;
    }

    protected void setRepository(Repository<I, T> repository) {
        this.repository = repository;
        invalidateContent();
    }

    @Override
	public boolean isMultipleSelection() {
		return multipleSelection;
	}

	@Override
	public void setMultipleSelection(boolean multipleSelection) {
		this.multipleSelection = multipleSelection;
	}

	protected void invalidateContent() {
        if (content != null) {
        	for (T curr: content) {
        		repository.refresh(curr);
        	}
            content = null;
            selectedIndexes.clear();
            firePropertyChange("list", null, null);
            fireContentChange();
            notifySelectionListeners();
        }
    }

    protected boolean isValidContent() {
        return content != null;
    }

    protected void validateContent() {
        if (!isValidContent()) {
            refreshContent();
        }
    }

    protected void refreshContent() {
        if (specification != null) {
        	content = new ArrayList<T>(repository.list(specification));
        } else {
        	content = new ArrayList<T>(repository.list());
        }
        for (Iterator<Integer> iter = selectedIndexes.iterator(); iter.hasNext();) {
			Integer idx = iter.next();
			if (!isValidSelectionIndex(idx)) {
				iter.remove();
			}
		}
    }

    @Override
    public List<T> getList() {
        validateContent();
        return content;
    }

    public boolean isEmptyList() {
        return getList().size() == 0;
    }

    @Override
	public Specification<T> getSpecification() {
		return this.specification;
	}

	@Override
	public void setSpecification(Specification<T> spec) {
		if (this.specification != null) {
			this.specification.removeSpecificationChangeListener(specificationChangeListener);
		}
		this.specification = spec;
		spec.addSpecificationChangeListener(specificationChangeListener);
		invalidateContent();
	}

	@Override
    public void setOrder(final String property, final boolean reverse) {
		String currentProperty = orders.getFirstProperty();
		boolean currentReverse = orders.isFirstReverse();
        if (!StringUtils.equals(property, currentProperty) || reverse != currentReverse) {
            T selected = getSelectedObject();
            orders.clear();
            orders.add(property, reverse);
            if (repository != null) {
	            repository.setOrder(property, reverse);
            }
            invalidateContent();
            if (selected != null) {
                select(selected);
            }
        }
    }
	
    public void addOrder(final String property, final boolean reverse) {
    	String currentProperty = orders.getLastProperty();
		boolean currentReverse = orders.isLastReverse();
        if (!StringUtils.equals(property, currentProperty) || reverse != currentReverse) {
            T selected = getSelectedObject();
            if (StringUtils.equals(property, currentProperty)) {
            	orders.removeLast();
            }
            orders.add(property, reverse);
            if (repository != null) {
            	if (StringUtils.equals(property, currentProperty)) {
                	repository.removeLastOrder();
                }
	            repository.addOrder(property, reverse);
            }
            invalidateContent();
            if (selected != null) {
                select(selected);
            }
        }
	}

    @Override
	public void addOrder(String property) {
    	if (StringUtils.equals(property, getOrderProperty())) {
            addOrder(property, !getReverseOrder());
        } else {
            addOrder(property, false);
        }
	}

	@Override
    public void setOrder(final String property) {
        if (StringUtils.equals(property, getOrderProperty())) {
            setOrder(property, !getReverseOrder());
        } else {
            setOrder(property, false);
        }
    }

    @Override
    public String getOrderProperty() {
        return orders.getLastProperty();
    }

    @Override
    public boolean getReverseOrder() {
        return orders.isLastReverse();
    }

    @Override
    public void select(int index) {
        select(Integer.valueOf(index));
    }

    public void select(final Integer index) {
    	if (!isValidSelectionIndex(index)) {
    		throw new ArrayIndexOutOfBoundsException(index);
    	}
    	if (index == null) {
			selectedIndexes.clear();
		} else {
			if (!multipleSelection) {
				selectedIndexes.clear();
			}
	    	if (selectedIndexes.contains(index)) {
	    		selectedIndexes.remove(index);
	    	} else {
	    		selectedIndexes.add(0, index);
	    	}
		}
    	notifySelectionListeners();
    }

	protected boolean isValidSelectionIndex(final Integer index) {
		return index == null || index < getList().size();
	}

    @Override
    public void select(final T object) {
        if (object == null || isEmptyList()) {
            select((Integer) null);
        } else {
            select(0);
            do {
                if (getSelectedObject().equals(object)) {
                    return;
                }
                next();
            } while (getHasNext());
        }
        select((Integer) null);
    }

    @Override
    public void deselect() {
        select((Integer) null);
    }

    protected T refreshObject(final int index) {
        T toBeRefreshed = getList().get(index);
        if (toBeRefreshed.getId() != null) {
	        toBeRefreshed = repository.get(toBeRefreshed.getId());
	        toBeRefreshed.getId(); // Costringe l'inizializzazione di un oggetto proxied
	        getList().set(index, toBeRefreshed);
        }
        return toBeRefreshed;
    }

    @Override
    public T getSelectedObject() {
        if (getSelectedIndex() == null) {
            return null;
        }
        T selected = refreshObject(getSelectedIndex().intValue());
        return selected;
    }

    @Override
    public Integer getSelectedIndex() {
    	if (selectedIndexes.isEmpty()) {
    		return null;
    	}
        return selectedIndexes.get(0);
    }

    @Override
    public void next() {
        if (getHasNext()) {
            select(getSelectedIndex().intValue() + 1);
        }
    }

    @Override
    public boolean getHasNext() {
        return (getSelectedIndex() != null) && (getSelectedIndex().intValue() < (getList().size() - 1));
    }

    @Override
    public void previous() {
        if (getHasPrevious()) {
            select(getSelectedIndex().intValue() - 1);
        }
    }

    @Override
    public boolean getHasPrevious() {
        return (getSelectedIndex() != null) && (getSelectedIndex().intValue() > 0);
    }

    @Override
    public void commandDone(final CommandEvent source) {
    	List<Integer> idxs = new ArrayList<Integer>(selectedIndexes.size());
		idxs.addAll(selectedIndexes);
        invalidateContent();
        for (Integer idx: idxs) {
	        if (isValidSelectionIndex(idx)) {
	        	select(idx);
	        }
        }
        fireCommandEvent(source);
    }
    
    @Override
	public List<Integer> getSelectedIndexes() {
    	return Collections.unmodifiableList(selectedIndexes);
    }

    @Override
	public List<T> getSelectedObjects() {
    	List<T> result = new ArrayList<T>();
    	for (Integer index : selectedIndexes) {
    		result.add(refreshObject(index));
    	}
    	return result;
    }

    protected void addCommandListener(final CommandListener listener) {
        cmdEventSupport.addListener(listener);
    }

    protected void addCommandListener(final CommandListener listener, final CommandResult... results) {
        cmdEventSupport.addListener(listener, results);
    }
    
    protected void fireCommandEvent(final CommandEvent source) {
    	cmdEventSupport.fireCommandEvent(source);
    }

    public void useObjectBrowserClass(Class<? extends ObjectBrowserImpl<I, T>> placeHolderClass) {
        this.objectBrowserClass = placeHolderClass;
    }

    private Constructor<? extends ObjectBrowserImpl<I, T>> findPlaceHolderConstructor() {
        for (Constructor<?> constructor : objectBrowserClass.getConstructors()) {
            Class<?>[] parms = constructor.getParameterTypes();
            if (parms.length == 2 && Entity.class.isAssignableFrom(parms[0])
                    && Repository.class.isAssignableFrom(parms[1])) {
                return (Constructor<? extends ObjectBrowserImpl<I, T>>)constructor;
            }
        }
        return null;
    }

    @Override
    public ObjectBrowser<I, T> newObjectBrowser() {
        ObjectBrowserImpl<I, T> result;
        if (objectBrowserClass != null) {
            Constructor<? extends ObjectBrowserImpl<I, T>> constructor;
            try {
                constructor = findPlaceHolderConstructor();
            } catch (SecurityException e) {
                throw new RuntimeException(e);
            }
            try {
                result = constructor.newInstance(new Object[] {getSelectedObject(), getRepository()});
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e.getCause());
            }
        } else {
        	result = new ObjectBrowserImpl<I, T>(getSelectedObject(), getRepository());
        }
        cmdEventSupport.addListener(result);
        return result;
    }

    @Override
    public ObjectBrowser<I, T> getSelectedObjectBrowser() {
        try {
            if (objectBrowser == null) {
                objectBrowser = newObjectBrowser();
                addSelectionListener(objectBrowser);
            }
        } catch (Exception e) {
            logger.error("Errore", e);
            throw new RuntimeException(e);
        }
        return objectBrowser;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pChange.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pChange.removePropertyChangeListener(listener);
    }
    
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		pChange.firePropertyChange(propertyName, oldValue, newValue);
	}

    @Override
    public void addSelectionListener(SelectionListener listener) {
    	selectionSupport.addSelectionListener(listener);
    }

    @Override
    public void removeSelectionListener(SelectionListener listener) {
    	selectionSupport.removeSelectionListener(listener);
    }

    private void notifySelectionListeners() {
    	selectionSupport.notifySelectionListeners(new SelectionEvent(this));
    }

	@Override
	public void release() {
		pChange.disable();
		contentChangeSupport.disable();
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

	public String toString() {
		return "ListBrowserImpl [repository=" + repository + "]";
	}

	@Override
	public Collection<T> getSelectables() {
		return getList();
	}

	@Override
	public void selectSatisfied() {
		if (getSelectables().size() == 1) {
			select(0);
		}
	}
}
