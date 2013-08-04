package it.amattioli.guidate.editing;

import it.amattioli.applicate.editors.DefaultEditingListManager;
import it.amattioli.applicate.editors.EditingListManager;
import it.amattioli.applicate.editors.ListEditorImpl;
import it.amattioli.dominate.EntityImpl;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.au.AuService;
import org.zkoss.zk.scripting.Namespace;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.ComponentNotFoundException;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.IdSpace;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.ext.ScopeListener;
import org.zkoss.zk.ui.metainfo.ComponentDefinition;

import junit.framework.TestCase;

public class DeleteRowComposerTest extends TestCase {

	public void testOnCreate() {
		ListEditorImpl<?> editor = new ListEditorImpl<EntityImpl>(new ArrayList<EntityImpl>(), EntityImpl.class);
		DeleteRowMockComponent cmp = new DeleteRowMockComponent();
		cmp.setAttribute("backBean", editor);
		DeleteRowComposer composer = new DeleteRowComposer();
		Event evt = new Event("onCreate", cmp);
		composer.onCreate(evt);
		assertFalse(cmp.isVisible());
	}
	
	public void testAfterSelection() {
		ArrayList<EntityImpl> editingList = new ArrayList<EntityImpl>() {{
			EntityImpl e1 = new EntityImpl();
			e1.setId(1L);
			add(e1);
		}};
		ListEditorImpl<?> editor = new ListEditorImpl<EntityImpl>(editingList, EntityImpl.class);
		DeleteRowMockComponent cmp = new DeleteRowMockComponent();
		cmp.setAttribute("backBean", editor);
		DeleteRowComposer composer = new DeleteRowComposer();
		Event evt = new Event("onCreate", cmp);
		composer.onCreate(evt);
		editor.select(0);
		assertTrue(cmp.isVisible());
	}
	
	public void testOnClick() {
		ArrayList<EntityImpl> editingList = new ArrayList<EntityImpl>() {{
			EntityImpl e1 = new EntityImpl();
			e1.setId(1L);
			add(e1);
		}};
		ListEditorImpl<?> editor = new ListEditorImpl<EntityImpl>(editingList, EntityImpl.class);
		editor.setEmptyRow(false);
		DeleteRowMockComponent cmp = new DeleteRowMockComponent();
		cmp.setAttribute("backBean", editor);
		DeleteRowComposer composer = new DeleteRowComposer();
		Event onCreate = new Event("onCreate", cmp);
		composer.onCreate(onCreate);
		editor.select(0);
		Event onClick = new Event("onClick", cmp);
		composer.onClick(onClick);
		assertTrue(editingList.isEmpty());
	}
	
	public void testCannotDelete() {
		ArrayList<EntityImpl> editingList = new ArrayList<EntityImpl>() {{
			EntityImpl e1 = new EntityImpl();
			e1.setId(1L);
			add(e1);
		}};
		EditingListManager<EntityImpl> lm = new DefaultEditingListManager<EntityImpl>(editingList, EntityImpl.class) {

			@Override
			public boolean canDeleteRow(int idx) {
				return false;
			}

		};
		ListEditorImpl<?> editor = new ListEditorImpl<EntityImpl>(lm);
		editor.setEmptyRow(false);
		DeleteRowMockComponent cmp = new DeleteRowMockComponent();
		cmp.setAttribute("backBean", editor);
		DeleteRowComposer composer = new DeleteRowComposer();
		Event evt = new Event("onCreate", cmp);
		composer.onCreate(evt);
		editor.select(0);
		assertFalse(cmp.isVisible());
	}
	
	public static class DeleteRowMockComponent implements Component {
		private boolean visible = true;
		private Map<String, Object> attributes = new HashMap<String, Object>();
		
		@Override
		public Object clone() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean addEventListener(String evtnm, EventListener listener) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean addForward(String originalEvent, Component target, String targetEvent) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean addForward(String originalEvent, String targetPath, String targetEvent) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean addForward(String originalEvent, Component target, String targetEvent, Object eventData) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean addForward(String originalEvent, String targetPath, String targetEvent, Object eventData) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean appendChild(Component child) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void applyProperties() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean containsVariable(String name, boolean local) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void detach() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Object getAttribute(String name) {
			return attributes.get(name);
		}

		@Override
		public Object getAttribute(String name, int scope) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map getAttributes() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map getAttributes(int scope) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List getChildren() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ComponentDefinition getDefinition() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Desktop getDesktop() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Component getFellow(String id) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Component getFellowIfAny(String id) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection getFellows() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Component getFirstChild() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getId() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Component getLastChild() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Iterator getListenerIterator(String evtnm) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getMold() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Namespace getNamespace() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Component getNextSibling() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Page getPage() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Component getParent() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Component getPreviousSibling() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Component getRoot() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IdSpace getSpaceOwner() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getUuid() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getVariable(String name, boolean local) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean insertBefore(Component newChild, Component refChild) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void invalidate() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isInvalidated() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isListenerAvailable(String evtnm, boolean asap) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isVisible() {
			return visible;
		}

		@Override
		public Object removeAttribute(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object removeAttribute(String name, int scope) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean removeChild(Component child) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean removeEventListener(String evtnm, EventListener listener) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean removeForward(String originalEvent, Component target, String targetEvent) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean removeForward(String originalEvent, String targetPath, String targetEvent) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object setAttribute(String name, Object value) {
			return attributes.put(name, value);
		}

		@Override
		public Object setAttribute(String name, Object value, int scope) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setId(String id) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setMold(String mold) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setPage(Page page) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setPageBefore(Page page, Component refRoot) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setParent(Component parent) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setVariable(String name, Object val, boolean local) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean setVisible(boolean visible) {
			this.visible = visible;
			return visible;
		}

		@Override
		public void unsetVariable(String name, boolean local) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean addScopeListener(ScopeListener arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object getAttribute(String arg0, boolean arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasAttribute(String arg0, boolean arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object removeAttribute(String arg0, boolean arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean removeScopeListener(ScopeListener arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object setAttribute(String arg0, Object arg1, boolean arg2) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getAttributeOrFellow(String arg0, boolean arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AuService getAuService() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Component getFellow(String arg0, boolean arg1)
				throws ComponentNotFoundException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Component getFellowIfAny(String arg0, boolean arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getStubonly() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getWidgetAttribute(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set getWidgetAttributeNames() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getWidgetClass() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getWidgetListener(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set getWidgetListenerNames() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getWidgetOverride(String arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set getWidgetOverrideNames() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasAttribute(String arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean hasAttribute(String arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean hasAttributeOrFellow(String arg0, boolean arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean hasFellow(String arg0, boolean arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean hasFellow(String arg0) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setAuService(AuService arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setStubonly(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String setWidgetAttribute(String arg0, String arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setWidgetClass(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String setWidgetListener(String arg0, String arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String setWidgetOverride(String arg0, String arg1) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
}
