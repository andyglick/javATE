package it.amattioli.authorizate.users;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.hibernate.validators.NotBlank;

public class DefaultUser implements Entity<String>, User {
	private String id;
	private Long version;
	private String commonName;
	private String surname;
	private byte[] password;
	private String mailAddr;
	private List<DefaultRole> roles = new ArrayList<DefaultRole>();

	@NotBlank
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
	public String getName() {
		return id;
	}

	@NotBlank
	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	@NotBlank
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getMailAddr() {
		return mailAddr;
	}

	public void setMailAddr(String mailAddr) {
		this.mailAddr = mailAddr;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}
	
	private byte[] passwordDigest(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		byte[] digest = md.digest(password.getBytes());
		String pwd = "{SHA}"+new String(Base64.encodeBase64(digest));
		return pwd.getBytes();
	}
	
	public boolean checkPassword(String password) {
		if (password == null || password.equals("")) {
			return this.password == null || this.password.equals("");
		}
		byte[] pwdDigest = passwordDigest(password);
		return new String(pwdDigest).equals(new String(this.password));
	}

	
	public void setPassword(String newPassword) {
		setPassword(passwordDigest(newPassword));
	}
	
	public void addRole(DefaultRole role) {
		roles.add(role);
	}
	
	public void removeRole(DefaultRole role) {
		roles.remove(role);
	}
	
	public List<DefaultRole> getRoles() {
		return roles;
	}
	
	public void setRoles(List<DefaultRole> roles) {
		this.roles = roles;
	}
	
	@Override
	public boolean hasRole(String role) {
		for (DefaultRole curr: roles) {
			if (curr.getId().equals(role) || curr.hasRole(role)) {
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof DefaultUser)) {
			return false;
		}
		DefaultUser other = (DefaultUser) obj;
		if (id == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!id.equals(other.getId())) {
			return false;
		}
		return true;
	}
}
