package it.amattioli.applicate.editors;

import it.amattioli.applicate.editors.BeanEditor;
import it.amattioli.applicate.editors.IndexedPropertyEditingListManager;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class IndexedPropertyEditingListManagerTest extends TestCase {

	public static class MyBean {
		private String description;

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}
	
	public static class MyBeanContainer {
		private	List<MyBean> beans = new ArrayList<MyBean>();
		{
			MyBean b1 = new MyBean();
			b1.setDescription("B1");
			beans.add(b1);
			MyBean b2 = new MyBean();
			b2.setDescription("B2");
			beans.add(b2);
		}
		
		public MyBean getBean(int idx) {
			return beans.get(idx);
		}
		
		public void addBean(MyBean newBean) {
			beans.add(newBean);
		}
		
		public void removeBean(MyBean toBeRemoved) {
			beans.remove(toBeRemoved);
		}
	}
	
	public void testEditingList() {
		MyBeanContainer c = new MyBeanContainer();
		IndexedPropertyEditingListManager<MyBean> mgr = new IndexedPropertyEditingListManager<MyBean>(c, "bean");
		List<MyBean> editingList = mgr.getEditingList();
		assertTrue(c.beans.containsAll(editingList) && editingList.containsAll(c.beans));
	}
	
	public void testAddRow() {
		MyBeanContainer c = new MyBeanContainer();
		IndexedPropertyEditingListManager<MyBean> mgr = new IndexedPropertyEditingListManager<MyBean>(c, "bean");
		BeanEditor<MyBean> beanEditor = mgr.addRow();
		assertTrue(beanEditor.getEditingBean() instanceof MyBean);
		assertEquals(3, mgr.getEditingList().size());
	}
	
	public void testCanDeleteRow() {
		MyBeanContainer c = new MyBeanContainer();
		IndexedPropertyEditingListManager<MyBean> mgr = new IndexedPropertyEditingListManager<MyBean>(c, "bean");
		assertTrue(mgr.canDeleteRow(1));
	}
	
	public void testCannotDeleteRowIfNegativeIndex() {
		MyBeanContainer c = new MyBeanContainer();
		IndexedPropertyEditingListManager<MyBean> mgr = new IndexedPropertyEditingListManager<MyBean>(c, "bean");
		assertFalse(mgr.canDeleteRow(-1));
	}
	
	public void testCannotDeleteRowIfIndexOutOfBound() {
		MyBeanContainer c = new MyBeanContainer();
		IndexedPropertyEditingListManager<MyBean> mgr = new IndexedPropertyEditingListManager<MyBean>(c, "bean");
		assertFalse(mgr.canDeleteRow(2));
	}
	
	public void testDeleteRow() {
		MyBeanContainer c = new MyBeanContainer();
		IndexedPropertyEditingListManager<MyBean> mgr = new IndexedPropertyEditingListManager<MyBean>(c, "bean");
		MyBean toBeDeleted = c.beans.get(1);
		MyBean deleted = mgr.deleteRow(1);
		assertFalse(mgr.getEditingList().contains(deleted));
		assertEquals(toBeDeleted, deleted);
	}
}
