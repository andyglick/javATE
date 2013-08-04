package it.amattioli.workstate.exceptions;

public interface KeyedException {

	public KeyedMessage getKeyedMessage();

	public void addParameter(String tag, String value);

}
