package it.amattioli.guidate.editing;

import it.amattioli.applicate.editors.ListEditor;
import it.amattioli.applicate.selection.SelectionEvent;
import it.amattioli.applicate.selection.SelectionListener;
import it.amattioli.applicate.selection.Selector;
import it.amattioli.guidate.containers.BackBeanNotFoundException;
import it.amattioli.guidate.containers.BackBeans;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericComposer;

public class DeleteRowComposer extends GenericComposer {

	public ListEditor<?> getEditor(Component cmp) {
		return (ListEditor<?>)BackBeans.findBackBean(cmp);
	}
	
	public void onCreate(Event evt) {
		Component cmp = evt.getTarget();
		ListEditor<?> editor = getEditor(cmp);
		if (editor == null) {
			throw new BackBeanNotFoundException();
		}
		DeleteRowListener listener = new DeleteRowListener(cmp);
		editor.addSelectionListener(listener);
		cmp.setVisible(isActive(cmp));
		cmp.setAttribute(DeleteRowComposer.class.getName()+".listener", listener);
	}

	private boolean isActive(Component cmp) {
		ListEditor<?> editor = getEditor(cmp);
		return editor.getSelectedObject() != null && editor.canDeleteRow();
	}

	public void onClick(Event evt) {
		ListEditor<?> editor = (ListEditor<?>)BackBeans.findBackBean(evt.getTarget());
		editor.deleteRow();
	}
	
	private class DeleteRowListener implements SelectionListener {
		private Component component;
		
		public DeleteRowListener(Component component) {
			this.component = component;
		}
		
		@Override
		public void objectSelected(SelectionEvent event) {
			Object subject = ((Selector<?>)event.getSource()).getSelectedObject();
			component.setVisible(isActive(component));
		}

	}
}
