package it.amattioli.guidate.editing;

import it.amattioli.applicate.editors.ListEditor;
import it.amattioli.applicate.selection.SelectionEvent;
import it.amattioli.applicate.selection.SelectionListener;
import it.amattioli.guidate.collections.PrototypeRowRenderer;
import it.amattioli.guidate.containers.BackBeans;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.GenericComposer;
import org.zkoss.zul.Detail;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.impl.InputElement;

public class EditorGridComposer extends GenericComposer {
	private static final String SELECTED_IDX = "it.maticasrl.accounting.ui.CustomEditorGridComposer.selectedIdx";
    private ListEditor<?> editor;
	private SelectionListener selectionListener;
    
    public void onCreate(Event evt) {
    	Grid grid = (Grid)evt.getTarget();
    	this.editor = (ListEditor<?>)BackBeans.findBackBean(grid);
    	grid.setRowRenderer(new PrototypeRowRenderer(grid));
    	grid.setModel(new ListEditorModel(editor));
    	setSelectionListener(grid);
    }
    
    private void setSelectionListener(final Grid grid) {
		ListEditor<?> editor = (ListEditor)BackBeans.findBackBean(grid);
		selectionListener = new SelectionListener() {
			
			@Override
			public void objectSelected(SelectionEvent event) {
				ListEditor<?> editor = (ListEditor<?>)event.getSource();
				Integer selectedIndex = editor.getSelectedIndex();
				if (selectedIndex != null && !selectedIndex.equals(grid.getAttribute(SELECTED_IDX))) {
					Row selectedRow = (Row)grid.getRows().getChildren().get(selectedIndex);
					setFocusOnFirstInputChild(selectedRow);
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
					if (!(curr instanceof Detail)) {
						result = setFocusOnFirstInputChild((Component)curr);
						if (result) {
							break;
						}
					}
				}
				return result;
			}

		};
		editor.addSelectionListener(selectionListener);
	}

    public void onSelect(SelectEvent evt) {
    	Grid grid = (Grid)evt.getTarget();
    	Rows rows = grid.getRows();
        for (Object curr:evt.getSelectedItems()) {
        	int idx = rows.getChildren().lastIndexOf(curr);
        	grid.setAttribute(SELECTED_IDX, idx);
            editor.select(idx);
        }
    }

}
