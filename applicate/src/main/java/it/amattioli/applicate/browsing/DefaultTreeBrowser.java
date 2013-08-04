package it.amattioli.applicate.browsing;

import it.amattioli.applicate.commands.CommandEvent;
import it.amattioli.applicate.selection.SelectionEvent;
import it.amattioli.applicate.selection.SelectionListener;
import it.amattioli.applicate.selection.SelectionSupport;
import it.amattioli.dominate.properties.PropertyChangeSupport;
import it.amattioli.dominate.properties.PropertyChangeEmitter;
import it.amattioli.dominate.specifications.SpecificationChangeEvent;
import it.amattioli.dominate.specifications.SpecificationChangeListener;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.RepositoryRegistry;
import it.amattioli.dominate.Specification;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

public class DefaultTreeBrowser<I extends Serializable, T extends Entity<I>> implements TreeBrowser<I, T>, PropertyChangeEmitter {
	private Repository<I, T> repository;
	private I rootId;
	private T root;
	private String childrenPropertyName;
	private String parentPropertyName;
	private TreePath selectedPath;
	private Specification<T> specification;
	
	private SelectionSupport selectionSupport = new SelectionSupport();
	private PropertyChangeSupport pChange = new PropertyChangeSupport(this);
	private ContentChangeSupport contentChangeSupport = new ContentChangeSupport();
	private SpecificationChangeListener specificationListener = new SpecificationChangeListener() {
		
		@Override
		public void specificationChange(SpecificationChangeEvent event) {
			deselect();
		}
	};
	
	protected DefaultTreeBrowser() {
		
	}
	
	public DefaultTreeBrowser(Repository<I, T> repository, I rootId, String childrenPropertyName, String parentPropertyName) {
		this.repository = repository;
		this.rootId = rootId;
		this.childrenPropertyName = childrenPropertyName;
		this.parentPropertyName = parentPropertyName;
	}
	
	public DefaultTreeBrowser(Class<T> entityClass, I rootId, String childrenPropertyName, String parentPropertyName) {
		this(RepositoryRegistry.instance().getRepository(entityClass), rootId, childrenPropertyName, parentPropertyName);
	}
	
	public I getRootId() {
		return rootId;
	}

	public void setRootId(I rootId) {
		this.rootId = rootId;
		this.root = null;
		firePropertyChange("root", null, null);
		fireContentChange();
	}

	@Override
	public T getRoot() {
		if (root == null && rootId != null) {
			root = repository.get(rootId);
		}
		return root;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getChildrenOf(T target) {
		if (target == null) {
			return Collections.emptyList();
		}
		try {
			return (List<T>) PropertyUtils.getProperty(repository.get(target.getId()), childrenPropertyName);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
	
	public T getParentOf(T target) {
		if (target == null) {
			return null;
		}
		try {
			return (T) PropertyUtils.getProperty(repository.get(target.getId()), parentPropertyName);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public TreePath getPathOf(T target) {
		List<Integer> result = new ArrayList<Integer>();
		T curr = target;
		while (!curr.equals(getRoot())) {
			T parent = getParentOf(curr);
			result.add(0, getChildrenOf(parent).indexOf(curr));
			curr = parent;
		}
		return new TreePath(result);
	}

	@Override
	public T getTargetOf(TreePath path) {
		T result = getRoot();
		for (int i: path.asIntArray()) {
			result = getChildrenOf(result).get(i);
		}
		return result;
	}

	@Override
	public void select(TreePath path) {
		this.selectedPath = path;
		notifySelectionListeners();
	}

	@Override
	public void select(T selected) {
		select(getPathOf(selected));
	}
	
	public void deselect() {
		select((TreePath)null);
	}

	@Override
	public T getSelectedObject() {
		if (selectedPath == null) {
			return null;
		}
		return getTargetOf(selectedPath);
	}
	
	@Override
	public TreePath getSelectedPath() {
		return selectedPath;
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
	public void commandDone(CommandEvent evt) {
		repository.refresh(root);
		this.root = repository.get(rootId);
		firePropertyChange("root", null, null);
		fireContentChange();
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
	
	private boolean selectedSatisfySpecification() {
		if (getSelectedObject() == null) {
			return false;
		}
		return specification == null || specification.isSatisfiedBy(getSelectedObject());
	}
	
	public void next() {
		selectionSupport.disable();
		T start = getSelectedObject();
		if (start == null) {
			start = getRoot();
		}
		T prec;
		do {
			prec = start;
			nextOf(start);
			start = getSelectedObject();
		} while(prec != start && !selectedSatisfySpecification());
		selectionSupport.enable();
		notifySelectionListeners();
	}
	
	private void nextOf(T start) {
		List<T> children = getChildrenOf(start);
		if (children.isEmpty()) {
			nextFellowOf(start);
		} else {
			select(children.get(0));
		}
	}

	private void nextFellowOf(T start) {
		List<T> children;
		T parent = getParentOf(start);
		if (parent != null) {
			children = getChildrenOf(parent);
			int startIdx = children.indexOf(start);
			if (startIdx == children.size() - 1) {
				nextFellowOf(parent);
			} else {
				select(children.get(startIdx + 1));
			}
		}
	}
	
	public void previous() {
		selectionSupport.disable();
		T start = getSelectedObject();
		if (start == null) {
			start = getRoot();
		}
		T next;
		do {
			next = start;
			previousOf(start);
			start = getSelectedObject();
		} while(next != start && !selectedSatisfySpecification());
		selectionSupport.enable();
		notifySelectionListeners();
	}
	
	private void selectChildOf(T start) {
		List<T> children = getChildrenOf(start);
		if (children.isEmpty()) {
			select(start);
		} else {
			selectChildOf(children.get(children.size()-1));
		}
	}
	
	private void previousOf(T start) {
		List<T> children;
		T parent = getParentOf(start);
		if (parent != null) {
			children = getChildrenOf(parent);
			int startIdx = children.indexOf(start);
			if (startIdx == 0) {
				select(parent);
			} else {
				selectChildOf(children.get(startIdx - 1));
			}
		}
	}

	@Override
	public Specification<T> getSpecification() {
		return specification;
	}

	@Override
	public void setSpecification(Specification<T> spec) {
		this.specification = spec;
		this.specification.addSpecificationChangeListener(specificationListener);
		deselect();
	}
	
	private void collectSelectableChildren(T parent, Collection<T> coll) {
		for (T curr: getChildrenOf(parent)) {
			if (getSpecification().isSatisfiedBy(curr)) {
				coll.add(curr);
			}
			collectSelectableChildren(curr, coll);
		}
	}

	@Override
	public Collection<T> getSelectables() {
		Collection<T> result = new ArrayList<T>();
		collectSelectableChildren(getRoot(), result);
		return result;
	}

	@Override
	public void selectSatisfied() {
		if (getSelectables().size() == 1) {
			deselect();
			next();
		}
	}
}
