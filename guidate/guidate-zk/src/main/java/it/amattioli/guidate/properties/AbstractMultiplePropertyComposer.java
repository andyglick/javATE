package it.amattioli.guidate.properties;

import it.amattioli.dominate.properties.PropertyChangeEmitter;
import it.amattioli.guidate.collections.CollectionPropertyModel;
import it.amattioli.guidate.collections.PrototypeRowRenderer;
import it.amattioli.guidate.collections.ValueItemRenderer;
import it.amattioli.guidate.collections.ValueRowRenderer;
import it.amattioli.guidate.containers.BackBeans;
import it.amattioli.guidate.converters.Converters;

import java.util.Collection;
import java.util.Collections;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericComposer;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

public abstract class AbstractMultiplePropertyComposer extends GenericComposer {

	private static final String BINDING_UPDATER_ATTRIBUTE = CollectionPropertyComposer.class.getName()+".bindingUpdater";

	protected abstract void setModel(Grid grid, String propertyName);

	private String propertyName;
	private TypeConverter converter;
	private String draggableItems = "false";

	public AbstractMultiplePropertyComposer() {
		super();
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public TypeConverter getConverter(Component comp) {
		if (converter == null) {
			Object converterAttribute = comp.getAttribute("typeConverter");
			converter = Converters.createFrom(converterAttribute);
		}
		return converter;
	}

	public void setConverter(TypeConverter converter) {
		this.converter = converter;
	}

	public String getDraggableItems() {
		return draggableItems;
	}

	public void setDraggableItems(String draggableItems) {
		this.draggableItems = draggableItems;
	}

	public Object getBackBean(Component comp) {
		return BackBeans.findBackBean(comp);
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		if (propertyName == null) {
			this.propertyName = (new PropertyNameRetriever(comp)).retrieve();
		}
		super.doAfterCompose(comp);
	}

	public void onCreate(Event evt) throws Exception {
		Component comp = evt.getTarget();
		registerPropertyChangeListener(comp);
		if (comp instanceof Listbox) {
			initListbox((Listbox)comp);
		} else if (comp instanceof Grid) {
			initGrid((Grid)comp);
		}
	}

	private void initGrid(Grid grid) throws Exception {
		if (grid.getRowRenderer() == null) {
			Rows rows = grid.getRows();
			if (rows != null && !rows.getChildren().isEmpty()) {
				grid.setRowRenderer(new PrototypeRowRenderer(grid));
			} else {
				grid.setRowRenderer(new ValueRowRenderer(getConverter(grid), draggableItems));
			}
		}
		setModel(grid, propertyName);
	}

	protected Collection<? extends Row> implicitPrototypes(Grid grid) throws Exception {
		return Collections.emptyList();
	}

	private void initListbox(Listbox listbox) {
		listbox.setItemRenderer(new ValueItemRenderer(getConverter(listbox), draggableItems));
		listbox.setModel(new CollectionPropertyModel(getBackBean(listbox), propertyName));
	}

	private void registerPropertyChangeListener(Component comp) {
		Object backBean = getBackBean(comp);
		if (backBean instanceof PropertyChangeEmitter) {
			PropertyChangeEmitter emitter = (PropertyChangeEmitter)backBean;
			BindingUpdater bindingUpdater = new BindingUpdater(propertyName, comp);
			emitter.addPropertyChangeListener(bindingUpdater);
			comp.setAttribute(BINDING_UPDATER_ATTRIBUTE, bindingUpdater);
		}
	}

}