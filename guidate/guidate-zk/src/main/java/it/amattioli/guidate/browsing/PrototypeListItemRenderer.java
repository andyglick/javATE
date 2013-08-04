package it.amattioli.guidate.browsing;

import it.amattioli.guidate.containers.BackBeans;

import java.util.List;

import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class PrototypeListItemRenderer implements ListitemRenderer {
    private Listitem prototype;
    
    public PrototypeListItemRenderer(Listitem prototype) {
        this.prototype = prototype;
    }
    
    @Override
    public void render(Listitem item, Object data) throws Exception {
    	item.setValue(data);
        for (BrowserListCell cell:(List<BrowserListCell>)prototype.getChildren()) {
        	BrowserListCell newCell = (BrowserListCell)cell.clone();
            newCell.setParent(item);
            newCell.createCellContent();
        }
        item.addForward(Events.ON_DOUBLE_CLICK, BackBeans.findContainer(item), "onOpenSelectedItem");
    }

}
