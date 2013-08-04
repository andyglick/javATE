package it.amattioli.guidate.browsing;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.event.ListDataListener;

import it.amattioli.applicate.browsing.ContentChangeEvent;
import it.amattioli.applicate.browsing.ContentChangeListener;
import it.amattioli.applicate.browsing.ContentChangeSupport;
import it.amattioli.applicate.browsing.ListBrowser;
import it.amattioli.applicate.browsing.ObjectBrowser;
import it.amattioli.applicate.browsing.ObjectBrowserImpl;
import it.amattioli.applicate.commands.CommandEvent;
import it.amattioli.applicate.selection.SelectionListener;
import it.amattioli.dominate.EntityImpl;
import it.amattioli.dominate.Specification;
import junit.framework.TestCase;

public class ListBrowserModelTest extends TestCase {

	static class MockListBrowser implements ListBrowser<Long, EntityImpl> {
		private List<EntityImpl> content = new ArrayList<EntityImpl>() {{
			add(new EntityImpl());
			add(new EntityImpl());
			add(new EntityImpl());
		}};
		private ContentChangeSupport contentChangeSupport = new ContentChangeSupport();
		
		@Override
		public void addPropertyChangeListener(PropertyChangeListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void deselect() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean getHasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean getHasPrevious() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public List<EntityImpl> getList() {
			return content;
		}

		@Override
		public String getOrderProperty() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean getReverseOrder() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Integer getSelectedIndex() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ObjectBrowser<Long, EntityImpl> getSelectedObjectBrowser() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Specification<EntityImpl> getSpecification() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ObjectBrowser<Long, EntityImpl> newObjectBrowser() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void next() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void previous() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removePropertyChangeListener(PropertyChangeListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void select(EntityImpl object) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void select(int index) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setOrder(String property, boolean reverse) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setOrder(String property) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addOrder(String property) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setSpecification(Specification<EntityImpl> spec) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void useObjectBrowserClass(Class<? extends ObjectBrowserImpl<Long, EntityImpl>> objectBrowserClass) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addSelectionListener(SelectionListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public EntityImpl getSelectedObject() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void removeSelectionListener(SelectionListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addContentChangeListener(ContentChangeListener listener) {
			contentChangeSupport.addContentChangeListener(listener);
		}

		@Override
		public void release() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeContentChangeListener(ContentChangeListener listener) {
			contentChangeSupport.removeContentChangeListener(listener);
		}
		
		protected void fireContentChange() {
			contentChangeSupport.notifyContentChangeListeners(new ContentChangeEvent(this));
		}

		@Override
		public void commandDone(CommandEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Collection<EntityImpl> getSelectables() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void selectSatisfied() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setMultipleSelection(boolean multipleSelection) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isMultipleSelection() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public List<EntityImpl> getSelectedObjects() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Integer> getSelectedIndexes() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	static class MockListDataListener implements ListDataListener {
		public boolean changed = false;
		
		@Override
		public void onChange(ListDataEvent event) {
			changed = true;
		}
	}

	public void testSize() {
		MockListBrowser lb = new MockListBrowser();
		ListBrowserModel model = new ListBrowserModel(lb);
		assertEquals(3, model.getSize());
	}
	
	public void testGetElementAt() {
		MockListBrowser lb = new MockListBrowser();
		ListBrowserModel model = new ListBrowserModel(lb);
		assertEquals(lb.getList().get(2), model.getElementAt(2));
	}
	
	public void testContentChange() {
		MockListBrowser lb = new MockListBrowser();
		ListBrowserModel model = new ListBrowserModel(lb);
		MockListDataListener listener = new MockListDataListener();
		model.addListDataListener(listener);
		lb.fireContentChange();
		assertTrue(listener.changed);
	}
	
}
