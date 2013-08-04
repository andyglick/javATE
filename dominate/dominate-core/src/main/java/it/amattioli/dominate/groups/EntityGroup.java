package it.amattioli.dominate.groups;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.Specification;

/**
 * Represents a group of entities that satisfies a specification.
 * 
 * @author andrea
 *
 * @param <I>
 * @param <T>
 */
public class EntityGroup<I extends Serializable,T extends Entity<I>> {
	private Repository<I,T> rep;
	private Specification<T> spec;
	private List<T> content;
	private String description;

	/**
	 * Construct a group given a repository and a specification.
	 * The group will contain all the entities of the repository that
	 * satisfy the specification.
	 * 
	 * @param rep the repository from which the entities will be loaded
	 * @param spec the specification that must be satisfied by the members of this group
	 * @param description the description of this group
	 */
	public EntityGroup(Repository<I,T> rep, Specification<T> spec, String description) {
		this.rep = rep;
		this.spec = spec;
		this.description = description;
	}

	/**
	 * Construct a group given a specification. The group will initially be empty
	 * and must be filled using {@link #add(Entity)}.
	 * 
	 * @param spec the specification that must be satisfied by the members of this group
	 * @param description the description of this group
	 */
	public EntityGroup(Specification<T> spec, String description) {
		this(null, spec, description);
	}
	
	/**
	 * Retrieves a member of the group.
	 * 
	 * @param index the index of the member in the list returned by {@link #list()}
	 * @return the member of the group with the given index
	 */
	public T getMember(int index) {
		return list().get(index);
	}

	/**
	 * Check if this group contains an entity.
	 * 
	 * @param e the entity to be checked
	 * @return true if this group contains the given entity
	 */
	public boolean contains(T e) {
		return list().contains(e);
	}

	/**
	 * Return the number of members contained in this group
	 * 
	 * @return the number of members contained in this group
	 */
	public int size() {
		return list().size();
	}

	/**
	 * Retrieves all the members of this group.
	 * 
	 * @return all the members of this group.
	 */
	public List<T> list() {
		if (content == null) {
			content = rep.list(spec);
		}
		return content;
	}

	/**
	 * Add an entity to this group. The entity will actually be added only if it satisfies
	 * the group specification.
	 * 
	 * @param toBeAdded the entity to be added to this group
	 * @return true if the given entity was added to this group
	 */
	public boolean add(T toBeAdded) {
		if (content == null) {
			content = new ArrayList<T>();
		}
		if (spec.isSatisfiedBy(toBeAdded)) {
			content.add(toBeAdded);
			return true;
		}
		return false;
	}

	/**
	 * Retrieves the description of this group
	 * 
	 * @return the description of this group
	 */
	public String getDescription() {
		return description;
	}
}
