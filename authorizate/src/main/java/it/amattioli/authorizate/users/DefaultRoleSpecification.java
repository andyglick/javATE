package it.amattioli.authorizate.users;

import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.lang.StringUtils;

import it.amattioli.dominate.specifications.AbstractSpecification;

public abstract class DefaultRoleSpecification extends AbstractSpecification<DefaultRole> {
	private String id;
	private String description;
	private Collection<DefaultRole> exclusionList = new HashSet<DefaultRole>();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		String old = getId();
		this.id = id;
		firePropertyChange("id", old, id);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		String old = getDescription();
		this.description = description;
		firePropertyChange("description", old, description);
	}

	public void addToExclusionList(DefaultRole entity) {
		exclusionList.add(entity);
		firePropertyChange("exclusionList", null, null);
	}
	
	public void removeFromExclusionList(DefaultRole entity) {
		exclusionList.remove(entity);
		firePropertyChange("exclusionList", null, null);
	}
	
	protected Collection<DefaultRole> getExclusionList() {
		return exclusionList;
	}
	
	@Override
	public boolean isSatisfiedBy(DefaultRole object) {
		return (StringUtils.isBlank(id) || StringUtils.equals(object.getId(), id))
		    && (StringUtils.isBlank(description) || StringUtils.equals(object.getDescription(), description))
		    && !exclusionList.contains(object);
	}
	
	public boolean wasSet() {
		return !StringUtils.isBlank(id) ||
		       !StringUtils.isBlank(description) ||
		       !exclusionList.isEmpty();
	}

}
