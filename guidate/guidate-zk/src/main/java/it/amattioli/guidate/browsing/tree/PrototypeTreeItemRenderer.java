package it.amattioli.guidate.browsing.tree;

import java.util.List;

import it.amattioli.guidate.containers.BackBeans;
import it.amattioli.guidate.util.EventPoster;
import it.amattioli.guidate.validators.ValidatingComposer;

import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

public class PrototypeTreeItemRenderer implements TreeitemRenderer {
	private Treeitem prototype;
	
	public PrototypeTreeItemRenderer(Treeitem prototype) {
		this.prototype = prototype;
	}
	
	@Override
	public void render(Treeitem item, Object data) throws Exception {
		item.setValue(data);
		item.setAttribute(BackBeans.BACK_BEAN_ATTRIBUTE, data);
		TreeItemSelectionApplier applier = new TreeItemSelectionApplier(item.getId());
		Treerow row = item.getTreerow();
		if (row == null) {
			row = new Treerow();
			row.setParent(item);
		}
		row.getChildren().clear();
		for (Treecell curr: (List<Treecell>)prototype.getTreerow().getChildren()) {
			Treecell newCell = (Treecell)curr.clone();
			newCell.setParent(row);
			applier.visit(newCell);
		}
		ValidatingComposer validator = new ValidatingComposer();
    	validator.doAfterCompose(item);
		EventPoster poster = new EventPoster(Events.ON_CREATE);
		poster.visit(item);
        row.addForward(Events.ON_DOUBLE_CLICK, BackBeans.findContainer(item.getTree()), "onOpenSelectedItem");
	}

}
