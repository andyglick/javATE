package it.amattioli.guidate.wizard;

public interface WizardState {
	
	public void setBackBean(Object backBean);

	public boolean hasPrevious();
	
	public boolean hasNext();
	
	public boolean hasFinish();
	
	public void previous();
	
	public void next();
	
	public void finish();
	
	public String getCurrentPage();
	
}
