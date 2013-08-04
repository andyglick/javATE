package it.amattioli.dominate.validation;

public interface Constraint {

	public abstract String getName();

	public abstract Object getParameter(String name);

}