package it.amattioli.guidate.editing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import it.amattioli.applicate.editors.ListEditor;
import it.amattioli.applicate.editors.NullListEditor;

import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.event.ListDataEvent;

public class ListEditorModel extends AbstractListModel {
	private static final long serialVersionUID = 1L;
	private ListEditor<?> editor;
	private PropertyChangeListener propertyChangeListener;
	
	public <T> ListEditorModel(ListEditor<T> editor) {
		this.editor = editor == null ? new NullListEditor<T>() : editor;
		propertyChangeListener = new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ("editingList".equals(evt.getPropertyName())) {
					fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
				}
			}
        	
        };
		this.editor.addPropertyChangeListener(propertyChangeListener);
	}
	
	@Override
	public Object getElementAt(int idx) {
		return editor.getElementEditor(idx);
	}

	@Override
	public int getSize() {
		return editor.getSize();
	}

}
