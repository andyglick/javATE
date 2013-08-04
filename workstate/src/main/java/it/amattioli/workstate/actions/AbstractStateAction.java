package it.amattioli.workstate.actions;

public abstract class AbstractStateAction implements StateAction {

	/**
	 * Generally speaking two actions are considered equals if they have
	 * the same class. Implementations can partially redefine this behavior
	 * adding other conditions but leaving this condition unaltered.
	 * For example, if the behavior of an action depends on the value of
	 * a parameter passed to the constructor you can redefine the equals
	 * method so it returns true if two actions have the same class and
	 * the same value of the parameter.
	 * 
	 */
	@Override
	public boolean equals(Object o) {
		return this.getClass().equals(o.getClass());
	}
	
	private static final int PRIME = 37;
	
	@Override
	public int hashCode() {
		return PRIME + getClass().hashCode();
	}

	public void undoAction(AttributeHandler state) {
	}

}
