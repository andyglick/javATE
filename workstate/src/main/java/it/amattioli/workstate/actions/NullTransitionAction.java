package it.amattioli.workstate.actions;

/**
 * A NullTransitionAction is used to execute no actions duringa transition.
 */
public class NullTransitionAction extends AbstractTransitionAction {
	private static final NullTransitionAction instance = new NullTransitionAction();

	public static NullTransitionAction getInstance() {
		return instance;
	}

	private NullTransitionAction() {
	}

	public void doAction(AttributeReader event, AttributeHandler state) {
	}

}
