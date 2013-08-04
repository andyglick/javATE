package it.amattioli.applicate.browsing;

import it.amattioli.dominate.Entity;

import java.io.Serializable;
import java.util.List;

public interface PagedListBrowser<I extends Serializable, T extends Entity<I>> extends ListBrowser<I, T> {

    public void nextPage();

    public boolean getHasNextPage();

    public void previousPage();

    public boolean getHasPreviousPage();

    public void setPage(int page);

    public void gotoPage(int page);

    public Long getCurrentPageNumber();

    public Long getTotalPages();

    public void setPageSize(int size);

    public int getPageSize();

    public List<T> getListAll();

}
