package it.amattioli.guidate.collections;

import it.amattioli.applicate.editors.BeanEditor;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Specification;
import it.amattioli.guidate.containers.BackBeans;
import it.amattioli.guidate.util.EventPoster;
import it.amattioli.guidate.validators.ValidatingComposer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Rows;

public class PrototypeRowRenderer implements RowRenderer {
	private List<Row> prototypes = Collections.emptyList();
    
    public PrototypeRowRenderer(final Row prototype) {
        this.prototypes = new ArrayList<Row>() {{ add(prototype); }};
    }
    
    public PrototypeRowRenderer(List<Row> prototypes) {
    	this.prototypes = prototypes;
    }
    
    public PrototypeRowRenderer(Grid grid) {
    	prototypes = new ArrayList<Row>();
    	Rows rows = grid.getRows();
    	while (rows.getChildren().size() > 0) {
			Row prototype = (Row)rows.getChildren().get(0);
			prototype.detach();
	        prototypes.add(prototype);
		}
    }
    
    private Row findPrototypeFor(Object bean) {
    	Entity<?> entity = null;
		if (bean instanceof Entity<?>) {
			entity = (Entity<?>)bean;
		} else if (bean instanceof BeanEditor<?>) {
			Object editingBean = ((BeanEditor<?>)bean).getEditingBean();
			if (editingBean instanceof Entity<?>) {
				entity = (Entity<?>)editingBean;
			}
		}
    	for (Row curr: prototypes) {
    		Specification<Entity<?>> spec = (Specification<Entity<?>>)curr.getAttribute(PrototypeComposer.SPECIFICATION_ATTRIBUTE);
			if (spec.isSatisfiedBy(entity)) {
    			return curr;
    		}
    	}
    	return null;
    }

	@Override
	public void render(Row row, Object data) throws Exception {
		row.setValue(data);
		row.setAttribute(BackBeans.BACK_BEAN_ATTRIBUTE, data);
		ItemSelectionApplier applier = new ItemSelectionApplier(row.getUuid());
		for (Component comp: (Collection<Component>)findPrototypeFor(data).getChildren()) {
			Component newComp = (Component)comp.clone();
			newComp.setParent(row);
			applier.visit(newComp);
			//Events.postEvent(new Event(Events.ON_CREATE,newComp));
		}
		ValidatingComposer validator = new ValidatingComposer();
    	validator.doAfterCompose(row);
		EventPoster poster = new EventPoster(Events.ON_CREATE);
		poster.visit(row);
	}

}
