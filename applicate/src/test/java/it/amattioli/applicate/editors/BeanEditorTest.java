package it.amattioli.applicate.editors;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import it.amattioli.applicate.editors.BeanEditor;
import it.amattioli.applicate.editors.BeanEditorImpl;
import it.amattioli.applicate.properties.BeanStub;
import it.amattioli.applicate.properties.MockPropertyChangeListener;
import it.amattioli.dominate.properties.PropertyClass;
import it.amattioli.dominate.validation.Constraint;
import it.amattioli.dominate.validation.ValidationResult;
import static it.amattioli.dominate.validation.ValidationResult.ResultType.*;
import junit.framework.TestCase;

public class BeanEditorTest extends TestCase {

	public void testEditingBean() {
		BeanEditor<BeanStub> editor = new BeanEditorImpl<BeanStub>();
		BeanStub editingBean = new BeanStub();
		editor.setEditingBean(editingBean);
		assertEquals(editingBean, editor.getEditingBean());
	}

	public void testPropertyClass() {
		BeanEditorImpl<BeanStub> editor = new BeanEditorImpl<BeanStub>();
		BeanStub editingBean = new BeanStub();
		editor.setEditingBean(editingBean);
		PropertyClass pClass = editor.retrievePropertyClass("simpleProperty");
		assertEquals(String.class, pClass.getElementClass());
	}

	public void testGet() throws Exception {
		BeanEditor<BeanStub> editor = new BeanEditorImpl<BeanStub>();
		BeanStub editingBean = new BeanStub();
		editor.setEditingBean(editingBean);
		String value = (String)PropertyUtils.getProperty(editor, "simpleProperty");
		assertEquals("simplePropertyValue", value);
	}

	public void testSet() throws Exception {
		BeanEditor<BeanStub> editor = new BeanEditorImpl<BeanStub>();
		BeanStub editingBean = new BeanStub();
		editor.setEditingBean(editingBean);
		PropertyUtils.setProperty(editor, "simpleProperty", "newValue");
		String value = (String)PropertyUtils.getProperty(editor, "simpleProperty");
		assertEquals("newValue", value);
	}
	
	public void testSimplePropertyChange() throws Exception {
		BeanEditorImpl<BeanStub> editor = new BeanEditorImpl<BeanStub>();
		BeanStub editingBean = new BeanStub();
		editor.setEditingBean(editingBean);
		MockPropertyChangeListener listener = new MockPropertyChangeListener("simpleProperty");
		editor.addPropertyChangeListener(listener);
		PropertyUtils.setProperty(editor, "simpleProperty", "newValue");
		assertEquals(1, listener.getNumPropertyChangeNotified());
	}
	
	public void testDerivedPropertyChange() throws Exception {
		BeanEditorImpl<BeanStub> editor = new BeanEditorImpl<BeanStub>();
		BeanStub editingBean = new BeanStub();
		editor.setEditingBean(editingBean);
		MockPropertyChangeListener listener = new MockPropertyChangeListener("derivedProperty");
		editor.addPropertyChangeListener(listener);
		PropertyUtils.setProperty(editor, "simpleProperty", "newValue");
		assertEquals(1, listener.getNumPropertyChangeNotified());
	}

	public void testGetIndexed() throws Exception {
		BeanEditor<BeanStub> editor = new BeanEditorImpl<BeanStub>();
		BeanStub editingBean = new BeanStub();
		editor.setEditingBean(editingBean);
		String value = (String)PropertyUtils.getProperty(editor, "indexedProperty[2]");
		assertEquals("value2", value);
	}

	public void testSetIndexed() throws Exception {
		BeanEditor<BeanStub> editor = new BeanEditorImpl<BeanStub>();
		BeanStub editingBean = new BeanStub();
		editor.setEditingBean(editingBean);
		PropertyUtils.setProperty(editor, "indexedProperty[1]", "newValue");
		String value = (String)PropertyUtils.getProperty(editor, "indexedProperty[1]");
		assertEquals("newValue", value);
	}
	
	public void testCascadingIndexedPropertyChange() throws Exception {
		BeanEditorImpl<BeanStub> editor = new BeanEditorImpl<BeanStub>();
		BeanStub editingBean = new BeanStub();
		editor.setEditingBean(editingBean);
		MockPropertyChangeListener listener = new MockPropertyChangeListener("indexedProperty");
		editor.addPropertyChangeListener(listener);
		PropertyUtils.setProperty(editor, "indexedProperty[1]", "newValue");
		assertEquals(1, listener.getNumPropertyChangeNotified());
	}
	
	public void testListEditor() throws Exception {
		BeanEditor<BeanStub> editor = new BeanEditorImpl<BeanStub>();
		BeanStub editingBean = new BeanStub();
		editor.setEditingBean(editingBean);
		ListEditor<BeanStub> listEditor = (ListEditor<BeanStub>)PropertyUtils.getProperty(editor, "assocIndexedPropertyEditor");
		listEditor.addRow();
		BeanEditor<BeanStub> rowEditor = listEditor.getElementEditor(0);
		PropertyUtils.setProperty(rowEditor, "simpleProperty", "Hello!");
		assertEquals("Hello!", editingBean.getAssocIndexedProperty(0).getSimpleProperty());
	}
	
	public void testValidation() {
		BeanEditorImpl<BeanStub> editor = new BeanEditorImpl<BeanStub>();
		BeanStub editingBean = new BeanStub();
		editor.setEditingBean(editingBean);
		ValidationResult result = editor.validateProperty("annotatedProperty", null);
		assertEquals(INVALID, result.getType());
	}
	
	public void testPropertyConstraints() {
		BeanEditorImpl<BeanStub> editor = new BeanEditorImpl<BeanStub>();
		BeanStub editingBean = new BeanStub();
		editor.setEditingBean(editingBean);
		Constraint c = editor.getPropertyConstraint("annotatedProperty", "org.hibernate.validator.NotNull");
		assertNotNull(c);
		assertEquals("org.hibernate.validator.NotNull", c.getName());
	}
	
	public void testPropertyValues() {
		BeanEditorImpl<BeanStub> editor = new BeanEditorImpl<BeanStub>();
		BeanStub editingBean = new BeanStub();
		editor.setEditingBean(editingBean);
		List<String> values = (List<String>)editor.getPropertyValues("valuedProperty");
		assertEquals(3,values.size());
		assertTrue(values.contains("value1"));
		assertTrue(values.contains("value2"));
		assertTrue(values.contains("value3"));
	}
	
	public void testPropertyAvailability() {
		BeanEditorImpl<BeanStub> editor = new BeanEditorImpl<BeanStub>();
		BeanStub editingBean = new BeanStub();
		editor.setEditingBean(editingBean);
		assertTrue(editor.isPropertyAvailable("simpleProperty"));
	}
	
	public void testPropertyWritability() {
		BeanEditorImpl<BeanStub> editor = new BeanEditorImpl<BeanStub>();
		BeanStub editingBean = new BeanStub();
		editor.setEditingBean(editingBean);
		assertTrue(editor.isPropertyWritable("simpleProperty"));
	}
}
