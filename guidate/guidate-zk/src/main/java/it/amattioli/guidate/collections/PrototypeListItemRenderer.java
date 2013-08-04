package it.amattioli.guidate.collections;

import it.amattioli.applicate.editors.BeanEditor;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Specification;
import it.amattioli.guidate.containers.BackBeans;
import it.amattioli.guidate.util.EventPoster;
import it.amattioli.guidate.validators.ValidatingComposer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class PrototypeListItemRenderer implements ListitemRenderer {
    private List<Listitem> prototypes = Collections.emptyList();
    
    public PrototypeListItemRenderer(final Listitem prototype) {
        this.prototypes = new ArrayList<Listitem>() {{ add(prototype); }};
    }
    
    public PrototypeListItemRenderer(List<Listitem> prototypes) {
    	this.prototypes = prototypes;
    }
    
    public PrototypeListItemRenderer(Listbox listbox) {
    	prototypes = new ArrayList<Listitem>();
    	while (listbox.getItemCount() > 0) {
	    	Listitem prototype = listbox.getItemAtIndex(0);
	    	prototype.detach();
	        prototypes.add(prototype);
    	}
    }
    
    private Listitem findPrototypeFor(Object bean) {
    	Entity<?> entity = null;
		if (bean instanceof Entity<?>) {
			entity = (Entity<?>)bean;
		} else if (bean instanceof BeanEditor<?>) {
			Object editingBean = ((BeanEditor<?>)bean).getEditingBean();
			if (editingBean instanceof Entity<?>) {
				entity = (Entity<?>)editingBean;
			}
		}
    	for (Listitem curr: prototypes) {
    		Specification<Entity<?>> spec = (Specification<Entity<?>>)curr.getAttribute(PrototypeComposer.SPECIFICATION_ATTRIBUTE);
    		if (spec.isSatisfiedBy(entity)) {
    			return curr;
    		}
    	}
    	return null;
    }
    
    @Override
    public void render(Listitem item, Object data) throws Exception {
    	item.setValue(data);
    	item.setAttribute(BackBeans.BACK_BEAN_ATTRIBUTE, data);
    	ItemSelectionApplier applier = new ItemSelectionApplier(item.getUuid());
    	for (Listcell cell:(List<Listcell>)findPrototypeFor(data).getChildren()) {
        	Listcell newCell = (Listcell)cell.clone();
            newCell.setParent(item);
            applier.visit(newCell);
        }
    	ValidatingComposer validator = new ValidatingComposer();
    	validator.doAfterCompose(item);
    	EventPoster poster = new EventPoster(Events.ON_CREATE);
		poster.visit(item);
        item.addForward(Events.ON_DOUBLE_CLICK, BackBeans.findContainer(item.getListbox()), "onOpenSelectedItem");
    }

}
