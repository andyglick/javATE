package it.amattioli.workstate.exceptions;

import java.util.Collection;

public interface MultiException {

	public Collection<Throwable> getCauses();

	public void addCause(Throwable cause);

}
