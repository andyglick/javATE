package it.amattioli.authorizate.users;

import it.amattioli.applicate.commands.AbstractCommand;
import it.amattioli.applicate.commands.ApplicationException;
import it.amattioli.applicate.editors.BeanEditor;
import it.amattioli.dominate.RepositoryRegistry;

public class CreateUserCommand extends AbstractCommand {
	private BeanEditor<DefaultUser> userEditor;
	private PasswordSupplier pwdSupplier;

	public BeanEditor<DefaultUser> getUserEditor() {
		return userEditor;
	}

	public void setUserEditor(BeanEditor<DefaultUser> userEditor) {
		this.userEditor = userEditor;
		userEditor.setEditingBean(new DefaultUser());
	}

	public PasswordSupplier getPwdSupplier() {
		return pwdSupplier;
	}

	public void setPwdSupplier(PasswordSupplier pwdSupplier) {
		this.pwdSupplier = pwdSupplier;
	}

	@Override
	public void doCommand() throws ApplicationException {
		userEditor.getEditingBean().setPassword(pwdSupplier.getPassword());
		RepositoryRegistry.instance().getRepository(DefaultUser.class).put(userEditor.getEditingBean());
		super.doCommand();
	}
	
}
