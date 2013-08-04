package it.amattioli.guidate.editing;

import it.amattioli.applicate.commands.tree.TreeEditor;
import it.amattioli.guidate.containers.BackBeans;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericComposer;

public class MoveTreeNodeDownComposer extends GenericComposer {

	public void onClick(Event evt) {
		TreeEditor<?,?> editor = (TreeEditor<?,?>)BackBeans.findBackBean(evt.getTarget());
		editor.moveDown();
	}
	
}
