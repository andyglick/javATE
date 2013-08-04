package it.amattioli.guidate.editing;

import static org.zkoss.zk.ui.event.KeyEvent.DOWN;
import static org.zkoss.zk.ui.event.KeyEvent.UP;

import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zk.ui.util.GenericComposer;
import org.zkoss.zul.impl.XulElement;

public abstract class AbstractChangeRowComposer extends GenericComposer {

	public AbstractChangeRowComposer() {
		super();
	}

	public void onCreate(Event evt) {
		XulElement target = (XulElement)evt.getTarget();
		String ctrlKeys = (target.getCtrlKeys() == null) ? "" : target.getCtrlKeys();
		target.setCtrlKeys(ctrlKeys + "#up#down");
	}

	private Component findRow(Component cmp) {
		if (cmp == null) {
			return null;
		} else if (isRow(cmp)) {
			return cmp;
		} else {
			return findRow(cmp.getParent());
		}
	}
	
	protected abstract boolean isRow(Component cmp);

	private int findIndex(Component parent, Component child) {
		int idx = 0;
		for (Object curr: parent.getChildren()) {
			if (curr == child) {
				return idx;
			}
			idx++;
		}
		return -1;
	}

	private ArrayList<Integer> findPath(Component parent, Component child) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		Component curr = child;
		while (curr != parent) {
			int idx = findIndex(curr.getParent(), curr);
			result.add(0, idx);
			curr = curr.getParent();
		}
		return result;
	}

	private Component childFromIndex(Component parent, int idx) {
		if (parent == null || parent.getChildren() == null) {
			return null;
		}
		int i = 0;
		for (Object curr: parent.getChildren()) {
			if (i == idx) {
				return (Component)curr;
			}
			i++;
		}
		return null;
	}

	private Component childFromPath(Component parent, ArrayList<Integer> path) {
		Component result = parent;
		for (Integer idx: path) {
			result = childFromIndex(result, idx);
		}
		return result;
	}

	public void onCtrlKey(KeyEvent evt) {
		if (evt.getKeyCode() == DOWN || evt.getKeyCode() == UP) {
			Component row = findRow(evt.getReference());
			if (row != null) {
				ArrayList<Integer> path = findPath(row, evt.getReference());
				Component targetRow = evt.getKeyCode() == DOWN ? row.getNextSibling() : row.getPreviousSibling();
				if (targetRow != null) {
					Component target = childFromPath(targetRow, path);
					if (target != null) {
						((HtmlBasedComponent)target).focus();
					}
				}
			}
		}
	}

}