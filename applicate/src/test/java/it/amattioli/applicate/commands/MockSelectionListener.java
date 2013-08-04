package it.amattioli.applicate.commands;

import it.amattioli.applicate.selection.SelectionEvent;
import it.amattioli.applicate.selection.SelectionListener;

public class MockSelectionListener implements SelectionListener {
	private boolean objectSelectedReceived = false;
	private int numObjectSelectedReceived = 0;
	
	@Override
	public void objectSelected(SelectionEvent event) {
		objectSelectedReceived = true;
		numObjectSelectedReceived++;
	}

	public boolean isObjectSelectedReceived() {
		return objectSelectedReceived;
	}

	public int getNumObjectSelectedReceived() {
		return numObjectSelectedReceived;
	}

}
