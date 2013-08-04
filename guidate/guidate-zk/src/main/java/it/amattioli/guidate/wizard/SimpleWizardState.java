package it.amattioli.guidate.wizard;

public class SimpleWizardState implements WizardState {
	private String[] pages;
	private int current = 0;
	private Object backBean;
	
	public SimpleWizardState(String... pages) {
		this.pages = pages;
	}
	
	public void setBackBean(Object backBean) {
		this.backBean = backBean;
	}
	
	@Override
	public String getCurrentPage() {
		return pages[current];
	}

	@Override
	public boolean hasNext() {
		return current < pages.length - 1;
	}

	@Override
	public boolean hasPrevious() {
		return current > 0;
	}
	
	@Override
	public boolean hasFinish() {
		return !hasNext();
	}

	@Override
	public void next() {
		if (!hasNext()) {
			throw new IllegalStateException();
		}
		current++;
	}

	@Override
	public void previous() {
		if (!hasPrevious()) {
			throw new IllegalStateException();
		}
		current--;
	}
	
	@Override
	public void finish() {
		
	}

}
