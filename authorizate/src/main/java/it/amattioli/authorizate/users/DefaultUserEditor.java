package it.amattioli.authorizate.users;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.List;

import it.amattioli.applicate.browsing.ListBrowser;
import it.amattioli.applicate.browsing.ListBrowserImpl;
import it.amattioli.applicate.commands.AbstractCommand;
import it.amattioli.applicate.commands.ApplicationException;
import it.amattioli.applicate.commands.Command;
import it.amattioli.applicate.context.SameContext;
import it.amattioli.applicate.editors.BeanEditor;
import it.amattioli.dominate.memory.CollectionRepository;
import it.amattioli.dominate.properties.PropertyChangeEmitter;

public class DefaultUserEditor implements BeanEditor<DefaultUser>, PropertyChangeEmitter {
	private PropertyChangeSupport pChange = new PropertyChangeSupport(this);
	private DefaultUser editingBean;
	private ListBrowser<String, DefaultRole> allRoles;
	private ListBrowser<String, DefaultRole> selectedRoles;
	
	private Command commandAddRole = new AbstractCommand() {

		@Override
		public void doCommand() throws ApplicationException {
			DefaultRole selected = getAllRoles().getSelectedObject();
			if (selected != null) {
				getEditingBean().addRole(selected);
				((DefaultRoleSpecification)getAllRoles().getSpecification()).addToExclusionList((DefaultRole)selected);
			}
			super.doCommand();
		}
		
	};
	
	private Command commandRemoveRole = new AbstractCommand() {

		@Override
		public void doCommand() throws ApplicationException {
			DefaultRole selected = getSelectedRoles().getSelectedObject();
			if (selected != null) {
				getEditingBean().removeRole(selected);
				((DefaultRoleSpecification)getAllRoles().getSpecification()).removeFromExclusionList((DefaultRole)selected);
			}
			super.doCommand();
		}
		
	};
	
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		pChange.firePropertyChange(propertyName, oldValue, newValue);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
	    pChange.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
	    pChange.removePropertyChangeListener(listener);
	}
	
	@Override
	public DefaultUser getEditingBean() {
		return editingBean;
	}

	@Override
	public void setEditingBean(DefaultUser editingBean) {
		this.editingBean = editingBean;
		firePropertyChange(null, null, null);
	}
	
	public String getId() {
		return editingBean.getId();
	}
	
	public void setId(String id) {
		String old = getId();
		editingBean.setId(id);
		firePropertyChange("id", old, id);
	}

	public String getCommonName() {
		return editingBean.getCommonName();
	}
	
	public void setCommonName(String commonName) {
		String old = getCommonName();
		editingBean.setCommonName(commonName);
		firePropertyChange("commonName", old, commonName);
	}
	
	public String getSurname() {
		return editingBean.getSurname();
	}
	
	public void setSurname(String surname) {
		String old = getSurname();
		editingBean.setSurname(surname);
		firePropertyChange("surname", old, surname);
	}

	public String getMailAddr() {
		return editingBean.getMailAddr();
	}

	public void setMailAddr(String mailAddr) {
		String old = getMailAddr();
		editingBean.setMailAddr(mailAddr);
		firePropertyChange("mailAddr", old, mailAddr);
	}

	@SameContext
	public ListBrowser<String, DefaultRole> getAllRoles() {
		return allRoles;
	}

	@SameContext
	public void setAllRoles(ListBrowser<String, DefaultRole> allRoles) {
		this.allRoles = allRoles;
	}
	
	public ListBrowser<String, DefaultRole> getSelectedRoles() {
		if (selectedRoles == null) {
			Collection<DefaultRole> roles = (List<DefaultRole>)getEditingBean().getRoles();
			selectedRoles = new ListBrowserImpl<String, DefaultRole>(new CollectionRepository<String, DefaultRole>(roles));
			commandAddRole.addCommandListener(selectedRoles);
			commandRemoveRole.addCommandListener(selectedRoles);
			for (DefaultRole curr: roles) {
				((DefaultRoleSpecification)getAllRoles().getSpecification()).addToExclusionList((DefaultRole)curr);
			}
		}
		return selectedRoles;
	}
	
	public void addRole() {
		commandAddRole.doCommand();
	}
	
	public void removeRole() {
		commandRemoveRole.doCommand();
	}

}
