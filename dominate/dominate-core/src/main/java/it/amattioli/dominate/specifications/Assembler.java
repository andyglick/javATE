package it.amattioli.dominate.specifications;

public interface Assembler {
	
	public void startNegation();
	
	public void endNegation();

	public void startConjunction();
	
	public void endConjunction();
	
	public void startDisjunction();
	
	public void endDisjunction();
	
	public void noResults();

}
