package it.amattioli.authorizate.users;

import it.amattioli.applicate.commands.AbstractCommand;
import it.amattioli.applicate.commands.ApplicationException;
import it.amattioli.dominate.RepositoryRegistry;

public class ChangePwdCommand extends AbstractCommand {
	private DefaultUser editingBean;
	private PasswordEditor pwdEditor;

	public PasswordEditor getPwdEditor() {
		return pwdEditor;
	}

	public void setPwdEditor(PasswordEditor pwdEditor) {
		this.pwdEditor = pwdEditor;
	}
	
	public void setEditingBeanId(String id) {
		editingBean = RepositoryRegistry.instance().getRepository(DefaultUser.class).get(id);
	}

	@Override
	public void doCommand() throws ApplicationException {
		editingBean.setPassword(pwdEditor.getPassword());
		RepositoryRegistry.instance().getRepository(DefaultUser.class).put(editingBean);
		super.doCommand();
	}

}
