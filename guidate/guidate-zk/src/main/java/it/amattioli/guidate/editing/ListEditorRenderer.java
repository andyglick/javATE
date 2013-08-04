package it.amattioli.guidate.editing;

import it.amattioli.guidate.collections.PrototypeListItemRenderer;
import it.amattioli.guidate.containers.BackBeans;
import it.amattioli.guidate.properties.PropertyNameRetriever;

import org.zkoss.zul.Listitem;

public class ListEditorRenderer extends PrototypeListItemRenderer {

	public ListEditorRenderer(Listitem prototype) {
		super(prototype);
	}

	@Override
	public void render(Listitem item, Object data) throws Exception {
		item.setAttribute(PropertyNameRetriever.PROPERTY_INDEX, item.getIndex());
		super.render(item, data);
		item.removeAttribute(BackBeans.BACK_BEAN_ATTRIBUTE);
	}

}
