package it.amattioli.applicate.editors;

import it.amattioli.applicate.commands.MockSelectionListener;
import it.amattioli.applicate.editors.BeanEditor;
import it.amattioli.applicate.editors.ListEditor;
import it.amattioli.applicate.editors.ListEditorImpl;
import it.amattioli.applicate.properties.BeanStub;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import junit.framework.TestCase;

public class ListEditorTest extends TestCase {

	private List<BeanStub> createList() {
		ArrayList<BeanStub> result = new ArrayList<BeanStub>();
		BeanStub b0 = new BeanStub();
		b0.setSimpleProperty("value0");
		result.add(b0);
		BeanStub b1 = new BeanStub();
		b1.setSimpleProperty("value1");
		result.add(b1);
		return result;
	}

	public void testGet() throws Exception {
		ListEditor<BeanStub> listEditor = new BeanStubListEditor(createList());
		BeanEditor<BeanStub> editor = listEditor.getElementEditor(1);
		assertEquals("value1", PropertyUtils.getProperty(editor, "simpleProperty"));
	}

	public void testSet() throws Exception {
		ListEditor<BeanStub> listEditor = new BeanStubListEditor(createList());
		BeanEditor<BeanStub> editor = listEditor.getElementEditor(0);
		PropertyUtils.setProperty(editor, "simpleProperty", "newValue");
		assertEquals("newValue", PropertyUtils.getProperty(editor, "simpleProperty"));
	}

	public void testSize() throws Exception {
		ListEditor<BeanStub> listEditor = new BeanStubListEditor(createList());
		assertEquals(2, listEditor.getSize());
	}
	
	public void testAddRow() throws Exception {
		ListEditor<BeanStub> listEditor = new ListEditorImpl<BeanStub>(createList(), BeanStub.class);
		listEditor.addRow();
		BeanEditor<BeanStub> editor = listEditor.getElementEditor(2);
		assertEquals("simplePropertyValue", PropertyUtils.getProperty(editor, "simpleProperty"));
	}
	
	public void testAddRowToEmptyList() throws Exception {
		ListEditor<BeanStub> listEditor = new ListEditorImpl<BeanStub>(new ArrayList<BeanStub>(), BeanStub.class);
		listEditor.addRow();
		BeanEditor<BeanStub> editor = listEditor.getElementEditor(0);
		assertEquals("simplePropertyValue", PropertyUtils.getProperty(editor, "simpleProperty"));
	}
	
	public void testAddSecondRowToEmptyList() throws Exception {
		ListEditor<BeanStub> listEditor = new ListEditorImpl<BeanStub>(new ArrayList<BeanStub>(), BeanStub.class);
		listEditor.addRow();
		listEditor.addRow();
		BeanEditor<BeanStub> editor1 = listEditor.getElementEditor(0);
		PropertyUtils.setProperty(editor1, "simpleProperty", "newValue");
		BeanEditor<BeanStub> editor2 = listEditor.getElementEditor(1);
		assertEquals("simplePropertyValue", PropertyUtils.getProperty(editor2, "simpleProperty"));
	}
	
	public void testDeleteRow() throws Exception {
		ListEditor<BeanStub> listEditor = new ListEditorImpl<BeanStub>(createList(), BeanStub.class);
		listEditor.select(0);
		BeanStub toBeDeleted = listEditor.getSelectedObject();
		BeanStub deleted = listEditor.deleteRow();
		assertEquals(toBeDeleted, deleted);
	}
	
	public void testSelectionAfterDeletingLastRow() throws Exception {
		ListEditor<BeanStub> listEditor = new ListEditorImpl<BeanStub>(createList(), BeanStub.class);
		listEditor.select(1);
		listEditor.deleteRow();
		assertEquals(new Integer(0), listEditor.getSelectedIndex());
	}
	
	public void testNoSelectionAfterDeletingLastRow() throws Exception {
		ListEditor<BeanStub> listEditor = new ListEditorImpl<BeanStub>(createList(), BeanStub.class);
		listEditor.select(1);
		listEditor.deleteRow();
		listEditor.select(0);
		listEditor.deleteRow();
		assertNull(listEditor.getSelectedObject());
	}
	
	public void testSelectedIndex() {
		ListEditor<BeanStub> listEditor = new BeanStubListEditor(createList());
		listEditor.select(1);
		assertEquals(new Integer(1), listEditor.getSelectedIndex());
	}
	
	public void testNoSelectedIndex() {
		ListEditor<BeanStub> listEditor = new BeanStubListEditor(createList());
		assertNull(listEditor.getSelectedIndex());
	}
	
	public void testSelectedObject() {
		ListEditor<BeanStub> listEditor = new BeanStubListEditor(createList());
		listEditor.select(1);
		assertEquals("value1", listEditor.getSelectedObject().getSimpleProperty());
	}
	
	public void testNoSelectedObject() {
		ListEditor<BeanStub> listEditor = new BeanStubListEditor(createList());
		assertNull(listEditor.getSelectedObject());
	}
	
	public void testSelectionListener() {
		ListEditor<BeanStub> listEditor = new BeanStubListEditor(createList());
		MockSelectionListener listener = new MockSelectionListener();
		listEditor.addSelectionListener(listener);
		listEditor.select(1);
		assertTrue(listener.isObjectSelectedReceived());
		assertEquals(1, listener.getNumObjectSelectedReceived());
	}

}
