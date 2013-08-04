package it.amattioli.applicate.browsing;

import it.amattioli.applicate.context.SameContext;
import it.amattioli.applicate.selection.FilteredSelector;
import it.amattioli.applicate.selection.SelectionEvent;
import it.amattioli.applicate.selection.SelectionListener;
import it.amattioli.applicate.selection.SelectionSupport;
import it.amattioli.applicate.selection.Selector;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.RepositoryRegistry;
import it.amattioli.dominate.Specification;
import it.amattioli.dominate.specifications.ComparisonType;
import it.amattioli.dominate.specifications.ConjunctionSpecification;
import it.amattioli.dominate.specifications.DisjunctionSpecification;
import it.amattioli.dominate.specifications.StringSpecification;
import it.amattioli.dominate.properties.Properties;
import it.amattioli.dominate.properties.PropertyChangeEmitter;
import it.amattioli.dominate.validation.ValidationResult;
import it.amattioli.dominate.validation.Validator;
import static it.amattioli.dominate.validation.Validators.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

public class EntitySelector<I extends Serializable, T extends Entity<I>> implements Selector<T>, PropertyChangeEmitter {
	private static final String MATCH_PROPERTY = "match";
	private static final String SELECTED_OBJECT_PROPERTY = "selectedObject";
	private static final String DESCRIPTION_PROPERTY = "searchValue";

	public static enum Match {NONE, EXACT, MULTIPLE};
	
	private Repository<I,T> entityRepository;
	private String[] searchPropertyNames;
	private T selectedObject;
	private String description;
	private FilteredSelector<I,T> entityBrowser;
	private PropertyChangeSupport pChange = new PropertyChangeSupport(this);
	private SelectionListener browserSelectionListener;
	private Match match;
	private PropertyChangeListener selectorListener;
	private Object boundBean;
	private String boundPropertyName;
	private SelectionSupport selectionSupport = new SelectionSupport();
	private Specification<T> additionalSpecification;
	private PropertyChangeListener boundBeanListener;
	private ComparisonType comparisonType = ComparisonType.STARTS;

	public EntitySelector() {
		
	}
	
	public EntitySelector(Repository<I,T> entityRepository, final FilteredSelector<I,T> entityBrowser, String... searchPropertyNames) {
		this.entityRepository = entityRepository;
		setEntityBrowser(entityBrowser);
		if (searchPropertyNames.length == 0) {
			this.searchPropertyNames = new String[] {"description"};
		} else {
			this.searchPropertyNames = searchPropertyNames;
		}
	}
	
	public EntitySelector(Class<T> entityClass, final FilteredSelector<I,T> entityBrowser, String... searchPropertyNames) {
		this(RepositoryRegistry.instance().getRepository(entityClass), entityBrowser, searchPropertyNames);
	}
	
	public EntitySelector(Class<T> entityClass, String... searchPropertyNames) {
		this(entityClass, new ListBrowserImpl<I, T>(entityClass), searchPropertyNames);
	}
	
	public EntitySelector(Class<T> entityClass, String searchPropertyName, final FilteredSelector<I,T> placeBrowser) {
		this(entityClass, placeBrowser, searchPropertyName);
	}
	
	public List<String> getSearchPropertyNames() {
		return Arrays.asList(searchPropertyNames);
	}
	
	public ComparisonType getComparisonType() {
		return comparisonType;
	}

	public void setComparisonType(ComparisonType comparisonType) {
		this.comparisonType = comparisonType;
	}
	
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        pChange.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        pChange.removePropertyChangeListener(listener);
    }

    public void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue) {
        pChange.firePropertyChange(propertyName, oldValue, newValue);
    }
	
	public Specification<T> getAdditionalSpecification() {
		return additionalSpecification;
	}

	public void setAdditionalSpecification(Specification<T> additionalSpecification) {
		this.additionalSpecification = additionalSpecification;
		if (entityBrowser != null && additionalSpecification != null) {
			entityBrowser.setSpecification(additionalSpecification);
		}
	}

	public FilteredSelector<I,T> getEntityBrowser() {
		return entityBrowser;
	}

	@SameContext
	public void setEntityBrowser(final FilteredSelector<I,T> entityBrowser) {
		//this.placeBrowser = LongRunningContext.withCurrentSessionManager(placeBrowser);
		this.entityBrowser = entityBrowser;
		//StringSpecification<T> spec = StringSpecification.newInstance(searchPropertyName);
		//entityBrowser.setSpecification(spec);
		browserSelectionListener = new SelectionListener() {

			public void objectSelected(SelectionEvent event) {
				String old = description;
				T selectedObject = entityBrowser.getSelectedObject();
				if (selectedObject != null) {
					description = getSearchPropertyValue(selectedObject);
					setMatch(Match.EXACT);
				} else {
					description = "";
				}
				firePropertyChange(DESCRIPTION_PROPERTY, old, description);
				setSelectedObject(selectedObject);
			}
			
		};
		entityBrowser.addSelectionListener(browserSelectionListener);
		if (additionalSpecification != null) {
			entityBrowser.setSpecification(additionalSpecification);
		}
	}
	
	private String getSearchPropertyValue(T object) {
		return (String)Properties.get(object, searchPropertyNames[0]);
	}
	
	private void setSelectedObject(T selectedObject) {
		T old = this.selectedObject;
		this.selectedObject = selectedObject;
		firePropertyChange(SELECTED_OBJECT_PROPERTY, old, selectedObject);
		fireSelectionEvent();
	}

	public T getSelectedObject() {
		return selectedObject;
	}
	
	public void setSelectedObjectId(I id) {
		T old = this.selectedObject;
		I oldId = old == null ? null : old.getId();
		if (!ObjectUtils.equals(oldId, id)) {
			if (id == null) {
				this.selectedObject = null;
				this.description = "";
			} else {
				T selected = entityRepository.get(id);
				this.selectedObject = selected;
				this.description = getSearchPropertyValue(selected);
			}
			setMatch(Match.EXACT);
			firePropertyChange(SELECTED_OBJECT_PROPERTY, old, selectedObject);
			firePropertyChange(DESCRIPTION_PROPERTY, old, description);
			fireSelectionEvent();
		}
	}
	
	public I getSelectedObjectId() {
		if (selectedObject == null) {
			return null;
		}
		return selectedObject.getId();
	}

	public String getSearchValue() {
		return description;
	}
	
	private Specification<T> createSpecification(String description, ComparisonType comparisonType) {
		DisjunctionSpecification<T> spec = new DisjunctionSpecification<T>();
		for (String currPropertyName: searchPropertyNames) {
			StringSpecification<T> currSpec = StringSpecification.newInstance(currPropertyName);
			currSpec.setComparisonType(comparisonType);
			currSpec.setValue(description);
			spec.addSpecification(currSpec);
		}
		ConjunctionSpecification<T> completeSpec = new ConjunctionSpecification<T>();
		completeSpec.addSpecification(spec);
		if (additionalSpecification != null) {
			completeSpec.addSpecification(additionalSpecification);
		}
		return completeSpec;
	}

	public void setSearchValue(final String newDescription) {
		String description = StringUtils.upperCase(newDescription);
		String oldDescription = getSearchValue();
		if (!StringUtils.equals(oldDescription, description)) {
			Match oldMatch = getMatch();
			if (StringUtils.isBlank(description)) {
				this.description = "";
				setMatch(Match.EXACT);
				firePropertyChange(DESCRIPTION_PROPERTY, oldDescription, description);
				firePropertyChange(MATCH_PROPERTY, oldMatch, getMatch());
				entityBrowser.select((T)null);
			} else {
				entityBrowser.setSpecification(createSpecification(description, ComparisonType.EXACT));
				if (entityBrowser.getSelectables().size() == 0) {
					entityBrowser.setSpecification(createSpecification(description, comparisonType));
				}
				this.description = description;
				switch (entityBrowser.getSelectables().size()) {
					case 0: 
						setMatch(Match.NONE);
					    break;
					
					case 1:
						setMatch(Match.EXACT);
						break;
						
					default:
						setMatch(Match.MULTIPLE);
					        
				}
				firePropertyChange(DESCRIPTION_PROPERTY, oldDescription, description);
				firePropertyChange(MATCH_PROPERTY, oldMatch, getMatch());
				entityBrowser.selectSatisfied();
			}
		}
	}
	
	private void setMatch(Match match) {
		this.match = match;
	}
	
	public Match getMatch() {
		return match;
	}
	
	public void bindTo(final Object bean, final String propertyName) {
		boundBean = bean;
		boundPropertyName = propertyName;
		selectorListener = new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (SELECTED_OBJECT_PROPERTY.equals(evt.getPropertyName())) {
					Properties.set(bean, propertyName, evt.getNewValue());
				}
			}
			
		};
		addPropertyChangeListener(selectorListener);
		if (boundBean instanceof PropertyChangeEmitter) {
			boundBeanListener = new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if (evt.getPropertyName().equals(boundPropertyName)) {
						T newValue = (T)evt.getNewValue();
						I id = newValue == null ? null : newValue.getId();
						setSelectedObjectId(id);
					}
				}
			};
			((PropertyChangeEmitter)boundBean).addPropertyChangeListener(boundBeanListener);
		}
		T initial = (T)Properties.get(bean, propertyName);
		if (initial != null) {
			setSelectedObjectId(initial.getId());
		} else {
			setSelectedObjectId(null);
		}
	}
	
	public ValidationResult validate() {
		/*
		if (getMatch().equals(Match.NONE)) {
			String msg = ResourceBundle.getBundle("ValidatorMessages").getString("entitySelector.MatchNone");
			return new ValidationResult(ResultType.INVALID, msg);
		}
		*/
		if (boundBean != null) {
			Validator v = validatorFor(boundBean);
			return v.validateProperty(boundPropertyName, getSelectedObject());
		} else {
			return null;
		}
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		selectionSupport.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		selectionSupport.addSelectionListener(listener);
	}
	
	protected void fireSelectionEvent() {
		selectionSupport.notifySelectionListeners(new SelectionEvent(this));
	}
}
