package it.amattioli.workstate.actions;

/**
 * A NullStateAction is used to execute no actions entering and exiting
 * a state.
 */
public class NullStateAction extends AbstractStateAction {
	private static final NullStateAction instance = new NullStateAction();

	public static NullStateAction getInstance() {
		return instance;
	}

	private NullStateAction() {
	}

	public void doAction(AttributeHandler state) {
	}

}
