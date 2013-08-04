package it.amattioli.workstate.actions;

public abstract class AbstractGuard implements Guard {
	public static final Integer USER_PRIORITY = Integer.valueOf(1000);

	public boolean equals(Object o) {
		return this.getClass().equals(o.getClass());
	}

	private static final int PRIME = 37;
	
	@Override
	public int hashCode() {
		return PRIME + getClass().hashCode();
	}
	
	public Integer getPriority() {
		return USER_PRIORITY;
	}

	public String toString() {
		return this.getClass().getName();
	}

}
