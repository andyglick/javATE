package it.amattioli.guidate.browsing.tree;

import java.io.Serializable;

import it.amattioli.applicate.browsing.TreeBrowser;
import it.amattioli.dominate.Entity;

import org.zkoss.zul.AbstractTreeModel;

public class TreeBrowserModel<I extends Serializable, T extends Entity<I>> extends AbstractTreeModel {
	private TreeBrowser<I,T> browser;
	
	public T getRoot() {
		return (T)super.getRoot();
	}
	
	public TreeBrowserModel(TreeBrowser<I,T> browserParam) {
		super(browserParam.getRoot());
		this.browser = browserParam;
	}
	
	@Override
	public Object getChild(Object parent, int index) {
		return browser.getChildrenOf((T)parent).get(index);
	}

	@Override
	public int getChildCount(Object parent) {
		return browser.getChildrenOf((T)parent).size();
	}

	@Override
	public boolean isLeaf(Object node) {
		return browser.getChildrenOf((T)node).isEmpty();
	}

}
