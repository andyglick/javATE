package it.amattioli.guidate.collections;

import it.amattioli.guidate.converters.DefaultConverter;

import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

public class ValueRowRenderer implements RowRenderer {
	private TypeConverter converter;
	private String draggableItems = "false";
	
	public ValueRowRenderer() {
		
	}
	
	public ValueRowRenderer(TypeConverter converter, String draggableItems) {
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
	public void render(Row row, Object data) throws Exception {
		Label label = new Label();
		label.setParent(row);
		label.setValue((String)converter.coerceToUi(data, row.getGrid()));
		row.setValue(data);
		row.setDraggable(getDraggableItems());
	}

}
