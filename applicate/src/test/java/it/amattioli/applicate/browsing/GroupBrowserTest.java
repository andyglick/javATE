package it.amattioli.applicate.browsing;

import it.amattioli.applicate.commands.CommandEvent;
import it.amattioli.applicate.commands.CommandResult;
import it.amattioli.applicate.commands.NullCommand;
import it.amattioli.applicate.selection.SelectionEvent;
import it.amattioli.applicate.selection.SelectionListener;
import it.amattioli.dominate.groups.GroupingFactory;

import java.util.ArrayList;
import java.util.Collections;

import junit.framework.TestCase;

public class GroupBrowserTest extends TestCase {

	public void testGroups() {
		ArrayList<MyEntity> l = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setEnumProperty(MyEnum.VALUE1);
		l.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setEnumProperty(MyEnum.VALUE2);
		l.add(e2);
		GroupingFactory<Long,MyEntity> f = new GroupingFactory<Long,MyEntity>("enumProperty",l);
		
		DefaultGroupBrowser<Long, MyEntity> brw = new DefaultGroupBrowser<Long, MyEntity>(f);
		assertEquals(2, brw.getGroups().size());
		assertTrue(brw.getGroups().get(0).contains(e1));
		assertTrue(brw.getGroups().get(1).contains(e2));
	}
	
	public void testContentChange() {
		GroupingFactory<Long,MyEntity> f = new GroupingFactory<Long,MyEntity>("enumProperty", Collections.EMPTY_LIST);
		DefaultGroupBrowser<Long, MyEntity> brw = new DefaultGroupBrowser<Long, MyEntity>(f);
		MockContentChangeListener listener = new MockContentChangeListener();
		brw.addContentChangeListener(listener);
		brw.commandDone(new CommandEvent(new NullCommand(), CommandResult.SUCCESSFUL));
		assertTrue(listener.fired);
	}
	
	public static class MockContentChangeListener implements ContentChangeListener {
		private boolean fired = false;
		
		@Override
		public void contentChanged(ContentChangeEvent event) {
			fired = true;
		}

	}
	
	public void testIndexSelection() {
		ArrayList<MyEntity> l = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setEnumProperty(MyEnum.VALUE1);
		l.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setEnumProperty(MyEnum.VALUE2);
		l.add(e2);
		GroupingFactory<Long,MyEntity> f = new GroupingFactory<Long,MyEntity>("enumProperty",l);
		
		DefaultGroupBrowser<Long, MyEntity> brw = new DefaultGroupBrowser<Long, MyEntity>(f);
		brw.select(0, 0);
		assertEquals(e1, brw.getSelectedObject());
		assertEquals(new Integer(0), brw.getSelection().getGroupIndex());
		assertEquals(new Integer(0), brw.getSelection().getMemberIndex());
		assertEquals(1, brw.getSelectedObjects().size());
		assertEquals(e1, brw.getSelectedObjects().get(0));
	}
	
	public void testWrongGroupIndexSelection() {
		ArrayList<MyEntity> l = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setEnumProperty(MyEnum.VALUE1);
		l.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setEnumProperty(MyEnum.VALUE2);
		l.add(e2);
		GroupingFactory<Long,MyEntity> f = new GroupingFactory<Long,MyEntity>("enumProperty",l);
		
		DefaultGroupBrowser<Long, MyEntity> brw = new DefaultGroupBrowser<Long, MyEntity>(f);
		try {
			brw.select(3, 4);
			fail("Should throw ArrayIndexOutOfBoundException");
		} catch(ArrayIndexOutOfBoundsException e) {
			
		}	
	}
	
	public void testWrongMemberIndexSelection() {
		ArrayList<MyEntity> l = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setEnumProperty(MyEnum.VALUE1);
		l.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setEnumProperty(MyEnum.VALUE2);
		l.add(e2);
		GroupingFactory<Long,MyEntity> f = new GroupingFactory<Long,MyEntity>("enumProperty",l);
		
		DefaultGroupBrowser<Long, MyEntity> brw = new DefaultGroupBrowser<Long, MyEntity>(f);
		try {
			brw.select(1, 4);
			fail("Should throw ArrayIndexOutOfBoundException");
		} catch(ArrayIndexOutOfBoundsException e) {
			
		}	
	}
	
	public void testEntitySelection() {
		ArrayList<MyEntity> l = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setEnumProperty(MyEnum.VALUE1);
		l.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setEnumProperty(MyEnum.VALUE2);
		l.add(e2);
		GroupingFactory<Long,MyEntity> f = new GroupingFactory<Long,MyEntity>("enumProperty",l);
		
		DefaultGroupBrowser<Long, MyEntity> brw = new DefaultGroupBrowser<Long, MyEntity>(f);
		brw.select(e1);
		assertEquals(e1, brw.getSelectedObject());
		assertEquals(new Integer(0), brw.getSelection().getGroupIndex());
		assertEquals(new Integer(0), brw.getSelection().getMemberIndex());
		assertEquals(1, brw.getSelectedObjects().size());
		assertEquals(e1, brw.getSelectedObjects().get(0));
	}
	
	public void testDeselect() {
		ArrayList<MyEntity> l = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setEnumProperty(MyEnum.VALUE1);
		l.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setEnumProperty(MyEnum.VALUE2);
		l.add(e2);
		GroupingFactory<Long,MyEntity> f = new GroupingFactory<Long,MyEntity>("enumProperty",l);
		
		DefaultGroupBrowser<Long, MyEntity> brw = new DefaultGroupBrowser<Long, MyEntity>(f);
		brw.select(e1);
		brw.deselect();
		assertNull(brw.getSelectedObject());
		assertTrue(brw.getSelectedObjects().isEmpty());
	}
	
	public void testSelectionListener() {
		ArrayList<MyEntity> l = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setEnumProperty(MyEnum.VALUE1);
		l.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setEnumProperty(MyEnum.VALUE2);
		l.add(e2);
		GroupingFactory<Long,MyEntity> f = new GroupingFactory<Long,MyEntity>("enumProperty",l);
		
		DefaultGroupBrowser<Long, MyEntity> brw = new DefaultGroupBrowser<Long, MyEntity>(f);
		MockSelectionListener listener = new MockSelectionListener();
		brw.addSelectionListener(listener);
		brw.select(e1);
		assertEquals(1,listener.firedNum);
		brw.deselect();
		assertEquals(2,listener.firedNum);
	}
	
	public void testReSelectionWhenSingle() {
		ArrayList<MyEntity> l = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setEnumProperty(MyEnum.VALUE1);
		l.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setEnumProperty(MyEnum.VALUE2);
		l.add(e2);
		GroupingFactory<Long,MyEntity> f = new GroupingFactory<Long,MyEntity>("enumProperty",l);
		
		DefaultGroupBrowser<Long, MyEntity> brw = new DefaultGroupBrowser<Long, MyEntity>(f);
		brw.select(e1);
		brw.select(e1);
		assertEquals(1, brw.getSelectedObjects().size());
		assertTrue(brw.getSelectedObjects().contains(e1));
	}
	
	public void testMultipleSelection() {
		ArrayList<MyEntity> l = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setEnumProperty(MyEnum.VALUE1);
		l.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setEnumProperty(MyEnum.VALUE2);
		l.add(e2);
		GroupingFactory<Long,MyEntity> f = new GroupingFactory<Long,MyEntity>("enumProperty",l);
		
		DefaultGroupBrowser<Long, MyEntity> brw = new DefaultGroupBrowser<Long, MyEntity>(f);
		brw.setMultiple(true);
		brw.select(e1);
		brw.select(e2);
		assertEquals(2, brw.getSelectedObjects().size());
		assertTrue(brw.getSelectedObjects().contains(e1));
		assertTrue(brw.getSelectedObjects().contains(e2));
	}
	
	public void testReSelectionWhenMultiple() {
		ArrayList<MyEntity> l = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setEnumProperty(MyEnum.VALUE1);
		l.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setEnumProperty(MyEnum.VALUE2);
		l.add(e2);
		GroupingFactory<Long,MyEntity> f = new GroupingFactory<Long,MyEntity>("enumProperty",l);
		
		DefaultGroupBrowser<Long, MyEntity> brw = new DefaultGroupBrowser<Long, MyEntity>(f);
		brw.setMultiple(true);
		brw.select(e1);
		brw.select(e1);
		assertEquals(0, brw.getSelectedObjects().size());
		assertFalse(brw.getSelectedObjects().contains(e1));
	}
	
	public void testSelectGroup() {
		ArrayList<MyEntity> l = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setEnumProperty(MyEnum.VALUE1);
		l.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setEnumProperty(MyEnum.VALUE2);
		l.add(e2);
		MyEntity e3 = new MyEntity();
		e3.setId(3L);
		e3.setEnumProperty(MyEnum.VALUE1);
		l.add(e3);
		MyEntity e4 = new MyEntity();
		e4.setId(4L);
		e4.setEnumProperty(MyEnum.VALUE2);
		l.add(e4);
		GroupingFactory<Long,MyEntity> f = new GroupingFactory<Long,MyEntity>("enumProperty",l);
		
		DefaultGroupBrowser<Long, MyEntity> brw = new DefaultGroupBrowser<Long, MyEntity>(f);
		brw.setMultiple(true);
		brw.selectGroup(brw.getGroup(0));
		assertEquals(2, brw.getSelectedObjects().size());
		assertTrue(brw.getSelectedObjects().contains(e1));
		assertTrue(brw.getSelectedObjects().contains(e3));
		assertTrue(brw.isGroupSelected(0));
		assertEquals(1, brw.getSelectedGroupsIndex().size());
		assertTrue(brw.getSelectedGroupsIndex().contains(0));
	}

	public void testSelectGroupWhenMemberAlreadySelected() {
		ArrayList<MyEntity> l = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setEnumProperty(MyEnum.VALUE1);
		l.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setEnumProperty(MyEnum.VALUE2);
		l.add(e2);
		MyEntity e3 = new MyEntity();
		e3.setId(3L);
		e3.setEnumProperty(MyEnum.VALUE1);
		l.add(e3);
		MyEntity e4 = new MyEntity();
		e4.setId(4L);
		e4.setEnumProperty(MyEnum.VALUE2);
		l.add(e4);
		GroupingFactory<Long,MyEntity> f = new GroupingFactory<Long,MyEntity>("enumProperty",l);
		
		DefaultGroupBrowser<Long, MyEntity> brw = new DefaultGroupBrowser<Long, MyEntity>(f);
		brw.setMultiple(true);
		brw.select(e1);
		brw.selectGroup(0);
		assertEquals(2, brw.getSelectedObjects().size());
		assertTrue(brw.getSelectedObjects().contains(e1));
		assertTrue(brw.getSelectedObjects().contains(e3));
		assertTrue(brw.isGroupSelected(0));
		assertEquals(1, brw.getSelectedGroupsIndex().size());
		assertTrue(brw.getSelectedGroupsIndex().contains(0));
	}
	
	public void testReSelectGroup() {
		ArrayList<MyEntity> l = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setEnumProperty(MyEnum.VALUE1);
		l.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setEnumProperty(MyEnum.VALUE2);
		l.add(e2);
		MyEntity e3 = new MyEntity();
		e3.setId(3L);
		e3.setEnumProperty(MyEnum.VALUE1);
		l.add(e3);
		MyEntity e4 = new MyEntity();
		e4.setId(4L);
		e4.setEnumProperty(MyEnum.VALUE2);
		l.add(e4);
		GroupingFactory<Long,MyEntity> f = new GroupingFactory<Long,MyEntity>("enumProperty",l);
		
		DefaultGroupBrowser<Long, MyEntity> brw = new DefaultGroupBrowser<Long, MyEntity>(f);
		brw.setMultiple(true);
		brw.select(e2);
		brw.selectGroup(brw.getGroup(0));
		brw.selectGroup(brw.getGroup(0));
		assertEquals(1, brw.getSelectedObjects().size());
		assertTrue(brw.getSelectedObjects().contains(e2));
		assertFalse(brw.isGroupSelected(0));
	}
	
	public void testWrongGroupSelection() {
		ArrayList<MyEntity> l = new ArrayList<MyEntity>();
		MyEntity e1 = new MyEntity();
		e1.setId(1L);
		e1.setEnumProperty(MyEnum.VALUE1);
		l.add(e1);
		MyEntity e2 = new MyEntity();
		e2.setId(2L);
		e2.setEnumProperty(MyEnum.VALUE2);
		l.add(e2);
		GroupingFactory<Long,MyEntity> f = new GroupingFactory<Long,MyEntity>("enumProperty",l);
		
		DefaultGroupBrowser<Long, MyEntity> brw = new DefaultGroupBrowser<Long, MyEntity>(f);
		brw.setMultiple(true);
		try {
			brw.selectGroup(3);
			fail("Should throw ArrayIndexOutOfBoundsException");
		} catch(ArrayIndexOutOfBoundsException e) {
			
		}
	}
	
	public static class MockSelectionListener implements SelectionListener {
		private int firedNum = 0;
		
		@Override
		public void objectSelected(SelectionEvent event) {
			firedNum++;
		}
		
	}
}
