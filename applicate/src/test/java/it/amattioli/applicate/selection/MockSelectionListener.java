package it.amattioli.applicate.selection;

public class MockSelectionListener implements SelectionListener {
	private int notificationsNumber = 0;
	
	public int getNotificationsNumber() {
		return notificationsNumber;
	}
	
	@Override
	public void objectSelected(SelectionEvent event) {
		notificationsNumber++;
	}
	
	public boolean isSelectionNotified() {
		return notificationsNumber > 0;
	}
	
}
