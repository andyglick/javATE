package it.amattioli.applicate.browsing;

public class MockContentChangeListener implements ContentChangeListener {
	private boolean notified = false;
	
	@Override
	public void contentChanged(ContentChangeEvent event) {
		notified = true;
	}

	public boolean isNotified() {
		return notified;
	}

}
