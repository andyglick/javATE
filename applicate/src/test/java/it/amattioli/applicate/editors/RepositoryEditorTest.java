package it.amattioli.applicate.editors;

import org.apache.commons.beanutils.PropertyUtils;

import it.amattioli.dominate.Repository;
import it.amattioli.dominate.memory.MemoryRepository;
import it.amattioli.applicate.editors.BeanEditor;
import it.amattioli.applicate.editors.RepositoryEditor;
import it.amattioli.applicate.properties.BeanStub;
import junit.framework.TestCase;

public class RepositoryEditorTest extends TestCase {

	public Repository<Long, BeanStub> createRepository() {
		Repository<Long, BeanStub> rep = new MemoryRepository<Long, BeanStub>();
		BeanStub b0 = new BeanStub();
		b0.setId(0L);
		b0.setSimpleProperty("value0");
		rep.put(b0);
		BeanStub b1 = new BeanStub();
		b1.setId(1L);
		b1.setSimpleProperty("value1");
		rep.put(b1);
		return rep;
	}
	
	public void testGet() throws Exception {
		RepositoryEditor<Long,BeanStub> editor = new RepositoryEditor(createRepository(), BeanStub.class);
		BeanEditor<BeanStub> beanEditor = editor.getElementEditor(1);
		assertEquals("value1", PropertyUtils.getProperty(beanEditor, "simpleProperty"));
	}
	
	public void testSet() throws Exception {
		RepositoryEditor<Long,BeanStub> editor = new RepositoryEditor(createRepository(), BeanStub.class);
		BeanEditor<BeanStub> beanEditor = editor.getElementEditor(0);
		PropertyUtils.setProperty(beanEditor, "simpleProperty", "newValue");
		assertEquals("newValue", PropertyUtils.getProperty(beanEditor, "simpleProperty"));
	}

	public void testAddRow() throws Exception {
		RepositoryEditor<Long,BeanStub> editor = new RepositoryEditor(createRepository(), BeanStub.class);
		editor.addRow();
		BeanEditor<BeanStub> beanEditor = editor.getElementEditor(2);
		assertEquals("simplePropertyValue", PropertyUtils.getProperty(beanEditor, "simpleProperty"));
	}
	
	public void testAddRowToEmptyList() throws Exception {
		ListEditor<BeanStub> listEditor = new RepositoryEditor<Long,BeanStub>(BeanStub.class);
		listEditor.addRow();
		BeanEditor<BeanStub> editor = listEditor.getElementEditor(0);
		assertEquals("simplePropertyValue", PropertyUtils.getProperty(editor, "simpleProperty"));
	}
	
	public void testAddSecondRowToEmptyList() throws Exception {
		ListEditor<BeanStub> listEditor = new RepositoryEditor<Long,BeanStub>(BeanStub.class);
		listEditor.addRow();
		listEditor.addRow();
		BeanEditor<BeanStub> editor1 = listEditor.getElementEditor(0);
		PropertyUtils.setProperty(editor1, "simpleProperty", "newValue");
		BeanEditor<BeanStub> editor2 = listEditor.getElementEditor(1);
		assertEquals("newValue", PropertyUtils.getProperty(editor1, "simpleProperty"));
		assertEquals("simplePropertyValue", PropertyUtils.getProperty(editor2, "simpleProperty"));
	}
	
	public void testDeleteRow() throws Exception {
		RepositoryEditor<Long,BeanStub> editor = new RepositoryEditor(createRepository(), BeanStub.class);
		editor.select(1);
		BeanStub toBeDeleted = editor.getSelectedObject();
		BeanStub deleted = editor.deleteRow();
		assertEquals(toBeDeleted, deleted);
	}
	
	public void testDoCommand() throws Exception {
		Repository<Long, BeanStub> rep = createRepository();
		RepositoryEditor<Long,BeanStub> editor = new RepositoryEditor(rep, BeanStub.class);
		BeanEditor<BeanStub> beanEditor0 = editor.getElementEditor(0);
		PropertyUtils.setProperty(beanEditor0, "simpleProperty", "modifiedPropertyValue");
		editor.addRow();
		BeanEditor<BeanStub> beanEditor2 = editor.getElementEditor(2);
		Long addedBeanId = (Long)PropertyUtils.getProperty(beanEditor2, "id");
		PropertyUtils.setProperty(beanEditor2, "simpleProperty", "newPropertyValue");
		editor.select(1);
		editor.deleteRow();
		editor.doCommand();
		BeanStub b0 = rep.get(0L);
		assertEquals("modifiedPropertyValue",b0.getSimpleProperty());
		assertNull(rep.get(1L));
		BeanStub b2 = rep.get(addedBeanId);
		assertEquals("newPropertyValue",b2.getSimpleProperty());
	}
}
