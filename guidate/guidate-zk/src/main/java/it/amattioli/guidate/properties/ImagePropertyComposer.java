package it.amattioli.guidate.properties;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Image;

public class ImagePropertyComposer extends PropertyComposer {
	
	public ImagePropertyComposer() {
	}
	
	public ImagePropertyComposer(String propertyName) {
		setPropertyName(propertyName);
	}
	
	@Override
	protected String getComponentValueAttribute(Component comp) {
		return "src";
	}

	public void onCreate(Event evt) throws Exception {
		setConverter(new TypeConverter() {

			@Override
			public Object coerceToBean(Object value, Component comp) {
				throw new UnsupportedOperationException();
			}

			@Override
			public Object coerceToUi(Object value, Component comp) {
				return findImageSrc(value, comp);
			}
			
		});
		super.onCreate(evt);
	}
	
	private String findImageSrc(Object value, Component comp) {
		for (Object curr: comp.getChildren()) {
			Image currImage = (Image)curr;
			if (currImage.getAttribute("value").equals(value)) {
				return currImage.getSrc();
			}
		}
		return "";
	}
}
