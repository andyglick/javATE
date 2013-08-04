package it.amattioli.guidate.browsing;

import it.amattioli.applicate.browsing.ContentChangeEvent;
import it.amattioli.applicate.browsing.ContentChangeListener;
import it.amattioli.applicate.browsing.GroupBrowser;
import it.amattioli.dominate.groups.EntityGroup;

import org.zkoss.zul.AbstractGroupsModel;
import org.zkoss.zul.event.GroupsDataEvent;

public class GroupBrowserModel extends AbstractGroupsModel {
	private GroupBrowser<?, ?> browser;
	private ContentChangeListener listChangeListener = new ContentChangeListener() {

		@Override
		public void contentChanged(ContentChangeEvent event) {
			fireEvent(GroupsDataEvent.GROUPS_CHANGED, -1, -1, -1);
		}

	};
	
	public GroupBrowserModel(GroupBrowser<?, ?> browser) {
		this.browser = browser;
		browser.addContentChangeListener(listChangeListener);
	}
	
	@Override
	public Object getChild(int groupIdx, int childIdx) {
		return browser.getMember(groupIdx, childIdx);
	}

	@Override
	public int getChildCount(int idx) {
		return browser.getGroupSize(idx);
	}

	@Override
	public EntityGroup<?,?> getGroup(int idx) {
		return browser.getGroup(idx);
	}

	@Override
	public int getGroupCount() {
		return browser.getGroups().size();
	}

	@Override
	public boolean isClose(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setClose(int arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getGroupfoot(int idx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasGroupfoot(int idx) {
		// TODO Auto-generated method stub
		return false;
	}

}
