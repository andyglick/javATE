package it.amattioli.authorizate.users;

import java.util.ArrayList;
import java.util.List;

import it.amattioli.dominate.Described;

public class DefaultRole implements Role, Described {
	private String id;
	private Long version;
	private String description;
	private List<DefaultRole> children = new ArrayList<DefaultRole>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public List<DefaultRole> getChildren() {
		return children;
	}
	
	public void setChildren(List<DefaultRole> children) {
		this.children = children;
	}
	
	public void addChild(DefaultRole role) {
		children.add(role);
	}
	
	public void removeChild(DefaultRole role) {
		children.remove(role);
	}
	
	@Override
	public boolean hasRole(Role role) {
		return hasRole(role.getId());
	}
	
	public boolean hasRole(String roleId) {
		for (DefaultRole curr: children) {
			if (curr.getId().equals(roleId) || curr.hasRole(roleId)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DefaultRole))
			return false;
		DefaultRole other = (DefaultRole) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
