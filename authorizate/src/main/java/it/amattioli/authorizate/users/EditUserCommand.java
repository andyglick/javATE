package it.amattioli.authorizate.users;

import it.amattioli.applicate.commands.AbstractCommand;
import it.amattioli.applicate.commands.ApplicationException;
import it.amattioli.applicate.editors.BeanEditor;
import it.amattioli.dominate.RepositoryRegistry;

public class EditUserCommand extends AbstractCommand {
	private BeanEditor<DefaultUser> userEditor;

	public BeanEditor<DefaultUser> getUserEditor() {
		return userEditor;
	}

	public void setUserEditor(BeanEditor<DefaultUser> userEditor) {
		this.userEditor = userEditor;
	}

	public void setEditingBeanId(String id) {
		if (userEditor != null) {
			userEditor.setEditingBean(RepositoryRegistry.instance().getRepository(DefaultUser.class).get(id));
		}
	}

	@Override
	public void doCommand() throws ApplicationException {
		RepositoryRegistry.instance().getRepository(DefaultUser.class).put(userEditor.getEditingBean());
		super.doCommand();
	}
	
}
