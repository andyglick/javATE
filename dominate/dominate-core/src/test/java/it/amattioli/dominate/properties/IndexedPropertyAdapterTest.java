package it.amattioli.dominate.properties;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class IndexedPropertyAdapterTest extends TestCase {

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
	
	public void testGet() {
		MyBeanContainer obj = new MyBeanContainer();
		List<MyBean> list = new IndexedPropertyAdapter<MyBean>(obj, "bean");
		MyBean b = list.get(0);
		assertEquals("B1",b.getDescription());
	}
	
	public void testSize() {
		MyBeanContainer obj = new MyBeanContainer();
		List<MyBean> list = new IndexedPropertyAdapter<MyBean>(obj, "bean");
		int size = list.size();
		assertEquals(2,size);
	}
	
	public void testAdd() {
		MyBeanContainer obj = new MyBeanContainer();
		List<MyBean> list = new IndexedPropertyAdapter<MyBean>(obj, "bean");
		MyBean b = new MyBean();
		b.setDescription("NEW_B");
		list.add(b);
		assertEquals("NEW_B", list.get(2).getDescription());
	}
	
	public void testRemove() {
		MyBeanContainer obj = new MyBeanContainer();
		List<MyBean> list = new IndexedPropertyAdapter<MyBean>(obj, "bean");
		list.remove(0);
		assertEquals(1, list.size());
		assertEquals("B2", list.get(0).getDescription());
	}
}
