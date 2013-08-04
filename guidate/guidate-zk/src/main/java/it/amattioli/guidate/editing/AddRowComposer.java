package it.amattioli.guidate.editing;

import it.amattioli.applicate.editors.ListEditor;
import it.amattioli.guidate.containers.BackBeans;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericComposer;

public class AddRowComposer extends GenericComposer {

	public void onClick(Event evt) {
		ListEditor<?> editor = (ListEditor<?>)BackBeans.findBackBean(evt.getTarget());
		editor.addRow();
		Events.postEvent("onPostClick",evt.getTarget(),null);
	}
	
	public void onPostClick(Event evt) {
		ListEditor<?> editor = (ListEditor<?>)BackBeans.findBackBean(evt.getTarget());
		editor.select(editor.getSize() - 1);
	}
}
