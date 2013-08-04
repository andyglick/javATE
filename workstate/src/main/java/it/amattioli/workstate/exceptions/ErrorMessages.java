package it.amattioli.workstate.exceptions;

/**
 * Utility class to easily throw exceptions.
 */
public class ErrorMessages extends MessageBundle {
	private static final String RESOURCES = "it/amattioli/workstate/exceptions/Messages";
	private static ErrorMessages instance = new ErrorMessages();

	private ErrorMessages() {
		super(RESOURCES);
	}

	public static ErrorMessages getInstance() {
		return instance;
	}

	public static IllegalArgumentException newIllegalArgumentException(
			ErrorMessage msg, String[] args) {
		return new IllegalArgumentException(getInstance().getErrorMessage(
				msg.name(), args));
	}

	public static IllegalArgumentException newIllegalArgumentException(
			ErrorMessage msg) {
		return newIllegalArgumentException(msg, new String[] {});
	}

	public static IllegalArgumentException newIllegalArgumentException(
			ErrorMessage msg, String arg) {
		return newIllegalArgumentException(msg, new String[] { arg });
	}

	public static IllegalArgumentException newIllegalArgumentException(
			ErrorMessage msg, String arg1, String arg2) {
		return newIllegalArgumentException(msg, new String[] { arg1, arg2 });
	}

	public static NullPointerException newNullPointerException(
			ErrorMessage msg, String[] args) {
		return new NullPointerException(getInstance().getErrorMessage(
				msg.name(), args));
	}

	public static NullPointerException newNullPointerException(ErrorMessage msg) {
		return newNullPointerException(msg, new String[] {});
	}

	public static NullPointerException newNullPointerException(
			ErrorMessage msg, String arg) {
		return newNullPointerException(msg, new String[] { arg });
	}

	public static NullPointerException newNullPointerException(
			ErrorMessage msg, String arg1, String arg2) {
		return newNullPointerException(msg, new String[] { arg1, arg2 });
	}

	public static IllegalStateException newIllegalStateException(
			ErrorMessage msg, String[] args) {
		return new IllegalStateException(getInstance().getErrorMessage(
				msg.name(), args));
	}

	public static IllegalStateException newIllegalStateException(
			ErrorMessage msg) {
		return newIllegalStateException(msg, new String[] {});
	}

	public static IllegalStateException newIllegalStateException(
			ErrorMessage msg, String arg) {
		return newIllegalStateException(msg, new String[] { arg });
	}

	public static IllegalStateException newIllegalStateException(
			ErrorMessage msg, String arg1, String arg2) {
		return newIllegalStateException(msg, new String[] { arg1, arg2 });
	}

	public static UnsupportedOperationException newUnsupportedOperationException(
			ErrorMessage msg, String[] args) {
		return new UnsupportedOperationException(getInstance().getErrorMessage(
				msg.name(), args));
	}

	public static UnsupportedOperationException newUnsupportedOperationException(
			ErrorMessage msg) {
		return newUnsupportedOperationException(msg, new String[] {});
	}

	public static UnsupportedOperationException newUnsupportedOperationException(
			ErrorMessage msg, String arg) {
		return newUnsupportedOperationException(msg, new String[] { arg });
	}

	public static UnsupportedOperationException newUnsupportedOperationException(
			ErrorMessage msg, String arg1, String arg2) {
		return newUnsupportedOperationException(msg,
				new String[] { arg1, arg2 });
	}

	public static ClassCastException newClassCastException(ErrorMessage msg,
			String[] args) {
		return new ClassCastException(getInstance().getErrorMessage(msg.name(),
				args));
	}

	public static ClassCastException newClassCastException(ErrorMessage msg) {
		return newClassCastException(msg, new String[] {});
	}

	public static ClassCastException newClassCastException(ErrorMessage msg,
			String arg) {
		return newClassCastException(msg, new String[] { arg });
	}

	public static ClassCastException newClassCastException(ErrorMessage msg,
			String arg1, String arg2) {
		return newClassCastException(msg, new String[] { arg1, arg2 });
	}

	public static ClassCastException newClassCastException(ErrorMessage msg,
			String arg1, String arg2, String arg3) {
		return newClassCastException(msg, new String[] { arg1, arg2, arg3 });
	}

	public static WorkflowException newWorkflowException(String tag,
			ErrorMessage msg, String[] args) {
		return new WorkflowException(tag, getInstance().getErrorMessage(
				msg.name(), args));
	}

	public static WorkflowException newWorkflowException(String tag,
			ErrorMessage msg) {
		return newWorkflowException(tag, msg, new String[] {});
	}

	public static WorkflowException newWorkflowException(String tag,
			ErrorMessage msg, String arg1, String arg2) {
		return newWorkflowException(tag, msg, new String[] { arg1, arg2 });
	}

}
