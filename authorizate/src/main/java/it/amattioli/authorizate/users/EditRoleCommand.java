package it.amattioli.authorizate.users;

import it.amattioli.applicate.commands.AbstractCommand;
import it.amattioli.applicate.commands.ApplicationException;
import it.amattioli.applicate.editors.BeanEditor;
import it.amattioli.dominate.RepositoryRegistry;

public class EditRoleCommand extends AbstractCommand {
	private BeanEditor<DefaultRole> roleEditor;

	public BeanEditor<DefaultRole> getRoleEditor() {
		return roleEditor;
	}

	public void setRoleEditor(BeanEditor<DefaultRole> roleEditor) {
		this.roleEditor = roleEditor;
	}

	public void setEditingBeanId(String id) {
		if (roleEditor != null) {
			roleEditor.setEditingBean(RepositoryRegistry.instance().getRepository(DefaultRole.class).get(id));
		} else {
			roleEditor.setEditingBean(new DefaultRole());
		}
	}

	@Override
	public void doCommand() throws ApplicationException {
		RepositoryRegistry.instance().getRepository(DefaultRole.class).put(roleEditor.getEditingBean());
		super.doCommand();
	}
	
}
