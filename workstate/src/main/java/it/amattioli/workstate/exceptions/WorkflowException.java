package it.amattioli.workstate.exceptions;

import java.util.*;

public class WorkflowException extends Exception implements KeyedException, MultiException {
	private String tag;
	private KeyedMessage keyedMessage;
	private Collection<Throwable> causes = Collections.emptyList();
	private static final String DEFAULT_MESSAGE = "Errore!";

	public WorkflowException(String tag, String message) {
		super(message);
		this.tag = tag;
	}

	public WorkflowException(String tag, Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
		this.tag = tag;
		addCause(cause);
	}

	public WorkflowException(String tag, String message, Throwable cause) {
		super(message, cause);
		this.tag = tag;
		addCause(cause);
	}

	public WorkflowException(String tag, KeyedMessage keyedMessage) {
		super(DEFAULT_MESSAGE);
		this.tag = tag;
		this.keyedMessage = keyedMessage;
	}

	public WorkflowException(String tag, KeyedMessage keyedMessage, Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
		this.tag = tag;
		this.keyedMessage = keyedMessage;
		addCause(cause);
	}

	public String getTag() {
		return this.tag;
	}

	public KeyedMessage getKeyedMessage() {
		if (this.keyedMessage != null) {
			return this.keyedMessage;
		} else if (this.getCause() != null
				&& this.getCause() instanceof KeyedException) {
			return ((KeyedException) this.getCause()).getKeyedMessage();
		} else {
			return null;
		}
	}

	public void addParameter(String tag, String value) {
		if (this.keyedMessage != null) {
			this.keyedMessage.addParameter(tag, value);
		} else if (this.getCause() != null
				&& this.getCause() instanceof KeyedException) {
			((KeyedException) this.getCause()).addParameter(tag, value);
		} else {
			throw new NullPointerException(); // ?????????????
		}
	}

	public boolean equals(Object o) {
		boolean result = false;
		if (o instanceof WorkflowException) {
			WorkflowException e = (WorkflowException) o;
			if (this.tag == null) {
				result = (e.tag == null);
			} else {
				result = this.tag.equals(e.tag);
			}
			if (this.getMessage() == null) {
				result = result && (e.getMessage() == null);
			} else {
				result = result && this.getMessage().equals(e.getMessage());
			}
			if (this.keyedMessage == null) {
				result = result && (e.keyedMessage == null);
			} else {
				result = result && this.keyedMessage.equals(e.keyedMessage);
			}
		}
		return result;
	}

	public void addCause(Throwable cause) {
		if (causes.size() == 0) {
			causes = new ArrayList<Throwable>();
		}
		causes.add(cause);
	}

	public Collection<Throwable> getCauses() {
		return Collections.unmodifiableCollection(causes);
	}
}
