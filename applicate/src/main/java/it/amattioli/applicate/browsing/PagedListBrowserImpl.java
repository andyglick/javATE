package it.amattioli.applicate.browsing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;

public class PagedListBrowserImpl<I extends Serializable, T extends Entity<I>>
extends ListBrowserImpl<I, T>
implements PagedListBrowser<I, T> {
    private static final int DEFAULT_PAGE_SIZE = 10;

	private int pageSize = DEFAULT_PAGE_SIZE;

    protected int currPage = 1;

    private Long totalPages;

    private T selected;

    public PagedListBrowserImpl()  {

    }

    public PagedListBrowserImpl(Repository<I, T> repository) {
        super(repository);
        firstPage();
    }

    protected void findSelectedInPage() {
        T selected = this.selected;
        select((Integer) null);
        List<T> page = super.getList();
        int i = 0;
        for (T curr : page) {
            if (curr.equals(selected) && i < getPageSize()) {
                select(i);
                return;
            }
            i++;
        }
        this.selected = selected;
    }

    public void firstPage() {
        getRepository().setFirst(0);
        getRepository().setLast(getPageSize() + 1);
        currPage = 1;
    }

    public List<T> getList() {
        List<T> content = super.getList();
        return content.subList(0, Math.min(content.size(), getPageSize()));
    }

    protected void refreshPageLimits() {
        int first = (currPage - 1) * getPageSize();
        int last = currPage * getPageSize() + 1;
        getRepository().setFirst(first);
        getRepository().setLast(last);
        invalidateContent();
    }

    @Override
	protected void invalidateContent() {
		super.invalidateContent();
		if (getList().isEmpty()) {
			previousPage();
		}
	}

	public void nextPage() {
        if (getHasNextPage()) {
        	int oldPage = currPage;
            currPage++;
            refreshPageLimits();
            if (totalPages == null && !getHasNextPage()) {
            	Long oldPages = totalPages;
                totalPages = getCurrentPageNumber();
                firePropertyChange("totalPages", oldPages, totalPages);
            }
            findSelectedInPage();
            firePropertyChange("currentPageNumber", oldPage, currPage);
        }
    }

    public boolean getHasNextPage() {
        return super.getList().size() > getPageSize();
    }

    public void previousPage() {
        if (getHasPreviousPage()) {
        	int oldPage = currPage;
            currPage--;
            refreshPageLimits();
            findSelectedInPage();
            firePropertyChange("currentPageNumber", oldPage, currPage);
        }
    }

    public boolean getHasPreviousPage() {
        return currPage > 1;
    }

    private void newPage(int page) {
    	int oldPage = currPage;
		currPage = page;
		refreshPageLimits();
		if (super.getList().size() == 0) {
			currPage = oldPage;
			refreshPageLimits();
            throw new IllegalArgumentException();
		}
		if (totalPages == null && !getHasNextPage()) {
			Long oldPages = totalPages;
            totalPages = getCurrentPageNumber();
            firePropertyChange("totalPages", oldPages, totalPages);
        }
		firePropertyChange("currentPageNumber", oldPage, currPage);
    }

    public void setPage(int page) {
		try {
			newPage(page);
		} finally {
			findSelectedInPage();
		}
	}

    public void gotoPage(int page) {
    	int newPage = page;
    	while (newPage > 1) {
    		try {
    			setPage(newPage);
    			break;
    		} catch (IllegalArgumentException e) {
    			newPage--;
    			continue;
    		}
    	}
    	findSelectedInPage();
    }

	public Long getCurrentPageNumber() {
        return Long.valueOf(currPage);
    }

    public Long getTotalPages() {
    	if (totalPages == null && !getHasNextPage()) {
    		totalPages = getCurrentPageNumber();
    	}
        return totalPages;
    }

    public void setPageSize(int size) {
        this.pageSize = size;
        refreshPageLimits();
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public boolean getHasNext() {
        return super.getHasNext() || getHasNextPage();
    }

    public boolean getHasPrevious() {
        return super.getHasPrevious() || getHasPreviousPage();
    }

    public void next() {
        super.next();
        if (!super.getHasNext() && getHasNextPage()) {
            nextPage();
            select(0);
        }
    }

    public void previous() {
        if (!super.getHasPrevious() && getHasPreviousPage()) {
            previousPage();
            select(getPageSize() - 1);
        } else {
            super.previous();
        }
    }

    public void select(int index) {
        super.select(index);
        selected = getSelectedObject();
    }

    public List<T> getListAll() {
    	int oldFirst = getRepository().getFirst();
    	int oldLast = getRepository().getLast();
    	getRepository().setFirst(0);
    	getRepository().setLast(0);
    	List<T> result = null;
    	try {
    		if (getSpecification() == null) {
                result = new ArrayList<T>(getRepository().list());
            } else {
                result = new ArrayList<T>(getRepository().list(getSpecification()));
            }
    	} finally {
    		getRepository().setFirst(oldFirst);
    		getRepository().setLast(oldLast);
    	}
    	return result;
    }

}
