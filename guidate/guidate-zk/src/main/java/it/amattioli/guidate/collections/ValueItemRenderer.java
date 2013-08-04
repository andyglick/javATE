package it.amattioli.guidate.collections;

import it.amattioli.guidate.converters.DefaultConverter;

import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class ValueItemRenderer implements ListitemRenderer {
	private TypeConverter converter;
	private String draggableItems = "false";
	
	public ValueItemRenderer() {
		
	}
	
	public ValueItemRenderer(TypeConverter converter, String draggableItems) {
		setConverter(converter);
		setDraggableItems(draggableItems);
	}
	
	public TypeConverter getConverter() {
		return converter;
	}

	public void setConverter(TypeConverter converter) {
		this.converter = converter;
		if (converter == null) {
			this.converter = new DefaultConverter();
		}
	}

	public String getDraggableItems() {
		return draggableItems;
	}

	public void setDraggableItems(String draggableItems) {
		this.draggableItems = draggableItems;
	}

	@Override
	public void render(Listitem item, Object data) throws Exception {
		item.setLabel((String)converter.coerceToUi(data, item.getListbox()));
		item.setValue(data);
		item.setDraggable(getDraggableItems());
	}

}
