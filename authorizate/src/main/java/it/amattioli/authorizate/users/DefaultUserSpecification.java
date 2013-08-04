package it.amattioli.authorizate.users;

import org.apache.commons.lang.StringUtils;

import it.amattioli.dominate.specifications.AbstractSpecification;

public abstract class DefaultUserSpecification extends AbstractSpecification<DefaultUser>{
	private String id;
	private String commonName;
	private String surname;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		String old = getId();
		this.id = id;
		firePropertyChange("id", old, id);
	}

	public String getCommonName() {
		return commonName;
	}
	
	public void setCommonName(String value) {
		String old = getCommonName();
		this.commonName = value;
		firePropertyChange("commonName", old, commonName);
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String value) {
		String old = getSurname();
		this.surname = value;
		firePropertyChange("surname", old, surname);
	}

	@Override
	public boolean isSatisfiedBy(DefaultUser object) {
		return (StringUtils.isBlank(id) || StringUtils.equals(object.getId(), id))
		    && (StringUtils.isBlank(commonName) || StringUtils.equals(object.getCommonName(), commonName))
		    && (StringUtils.isBlank(surname) || StringUtils.equals(object.getSurname(), surname));
	}
	
	public boolean wasSet() {
		return !StringUtils.isBlank(id) ||
		       !StringUtils.isBlank(commonName) ||
		       !StringUtils.isBlank(surname);
	}
	
}
