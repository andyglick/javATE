package it.amattioli.guidate.browsing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import it.amattioli.applicate.browsing.ContentChangeEvent;
import it.amattioli.applicate.browsing.ContentChangeListener;
import it.amattioli.applicate.browsing.ListBrowser;

import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.event.ListDataEvent;

public class ListBrowserModel extends AbstractListModel {
    private ListBrowser<?, ?> browser;
    private List<?> currContent;
	private ContentChangeListener listChangeListener;
    
    public ListBrowserModel(ListBrowser<?,?> browserParam) {
        this.browser = browserParam;
        currContent = browser.getList();
        listChangeListener = new ContentChangeListener() {
			
			@Override
			public void contentChanged(ContentChangeEvent event) {
				List<?> newContent = browser.getList();
				currContent = newContent;
				fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
			}
		};
		browser.addContentChangeListener(listChangeListener);
    }

    @Override
    public Object getElementAt(int idx) {
        return currContent.get(idx);
    }

    @Override
    public int getSize() {
        return currContent.size();
    }

}
