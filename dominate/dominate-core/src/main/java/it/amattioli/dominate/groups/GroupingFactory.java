package it.amattioli.dominate.groups;

import it.amattioli.dominate.Described;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.properties.Properties;
import it.amattioli.dominate.specifications.dflt.DefaultObjectSpecification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * An {@link EntityGroupFactory} that groups a collection of entities by the value of a property.
 * 
 * @author andrea
 *
 * @param <I>
 * @param <T>
 */
public class GroupingFactory<I extends Serializable, T extends Entity<I>> implements EntityGroupFactory<I, T> {
	private String groupingProperty;
	private List<T> entities;
	
	/**
	 * Create a factory given the name of a property and a collection of entities.
	 * 
	 * @param groupingProperty
	 * @param entities
	 */
	public GroupingFactory(String groupingProperty, List<T> entities) {
		this.groupingProperty = groupingProperty;
		this.entities = entities;
	}
	
	public List<EntityGroup<I,T>> createGroups() {
		List<EntityGroup<I,T>> result = new ArrayList<EntityGroup<I,T>>();
		for (T entity: entities) {
			if (!addToGroup(result, entity)) {
				result.add(buildGroupFor(entity));
			}
		}
		return result;
	}
	
	private boolean addToGroup(List<EntityGroup<I,T>> groups, T entity) {
		for (EntityGroup<I,T> group: groups) {
			if (group.add(entity)) {
				return true;
			}
		}
		return false;
	}
	
	private EntityGroup<I,T> buildGroupFor(T entity) {
		DefaultObjectSpecification<T> spec = new DefaultObjectSpecification<T>(groupingProperty);
		Object value = Properties.get(entity, groupingProperty);
		spec.setValue(value);
		String description;
		if (value instanceof Described) {
			description = ((Described)value).getDescription();
		} else {
			description = value.toString();
		}
		EntityGroup<I, T> result = createGroup(spec, description);
		result.add(entity);
		return result;
	}

	protected EntityGroup<I, T> createGroup(DefaultObjectSpecification<T> spec, String description) {
		return new EntityGroup<I, T>(spec, description);
	}
	
}
