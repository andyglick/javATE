package it.amattioli.applicate.editors;

import java.util.List;

import it.amattioli.applicate.editors.ListEditorImpl;
import it.amattioli.applicate.properties.BeanStub;

public class BeanStubListEditor extends ListEditorImpl<BeanStub> {

	public BeanStubListEditor(List<BeanStub> editingList) {
		super(editingList, BeanStub.class);
	}

}
