package it.amattioli.guidate.editing;

import it.amattioli.applicate.editors.ListEditor;
import it.amattioli.applicate.selection.SelectionEvent;
import it.amattioli.applicate.selection.SelectionListener;
import it.amattioli.guidate.collections.PrototypeListItemRenderer;
import it.amattioli.guidate.containers.BackBeans;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.GenericComposer;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.impl.InputElement;

public class EditorListboxComposer extends GenericComposer {
	private static final String SELECTED_IDX = "it.maticasrl.accounting.ui.CustomEditorGridComposer.selectedIdx";
    private ListEditor<?> editor;
    private SelectionListener selectionListener;
    
    public void onCreate(Event evt) {
    	Listbox listbox = (Listbox)evt.getTarget();
    	this.editor = (ListEditor<?>)BackBeans.findBackBean(listbox);
        listbox.setItemRenderer(new PrototypeListItemRenderer(listbox));
        listbox.setModel(new ListEditorModel(editor));
        setSelectionListener(listbox);
    }
    
    private void setSelectionListener(final Listbox listbox) {
		ListEditor<?> editor = (ListEditor)BackBeans.findBackBean(listbox);
		selectionListener = new SelectionListener() {
			
			@Override
			public void objectSelected(SelectionEvent event) {
				ListEditor<?> editor = (ListEditor<?>)event.getSource();
				Integer selectedIndex = editor.getSelectedIndex();
				if (selectedIndex != null && !selectedIndex.equals(listbox.getAttribute(SELECTED_IDX))) {
					Listitem selectedItem = (Listitem)listbox.getItemAtIndex(selectedIndex);
					setFocusOnFirstInputChild(selectedItem);
				}
			}
			
			private boolean setFocusOnFirstInputChild(Component cmp) {
				boolean result = false;
				for (Object curr:cmp.getChildren()) {
					if (curr instanceof InputElement) {
						((InputElement)curr).focus();
						result = true;
						break;
					}
					result = setFocusOnFirstInputChild((Component)curr);
					if (result) {
						break;
					}
				}
				return result;
			}

		};
		editor.addSelectionListener(selectionListener);
	}
    
    public void onSelect(SelectEvent evt) {
    	Listbox listbox = (Listbox)evt.getTarget();
        for (Object curr:evt.getSelectedItems()) {
            int selectedIndex = ((Listitem)curr).getIndex();
            listbox.setAttribute(SELECTED_IDX, selectedIndex);
			editor.select(selectedIndex);
        }
    }

}
