package it.amattioli.applicate.commands;

import it.amattioli.applicate.commands.CommandEvent;
import it.amattioli.applicate.commands.CommandListener;

public class MockCommandListener implements CommandListener {
	private boolean commandDoneReceived = false;
	private int numCommandDoneReceived = 0;
	
	@Override
	public void commandDone(CommandEvent e) {
		commandDoneReceived = true;
		numCommandDoneReceived++;
	}

	public boolean isCommandDoneReceived() {
		return commandDoneReceived;
	}

	public int getNumCommandDoneReceived() {
		return numCommandDoneReceived;
	}

}
