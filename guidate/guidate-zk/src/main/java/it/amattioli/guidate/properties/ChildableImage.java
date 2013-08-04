package it.amattioli.guidate.properties;

import org.zkoss.zul.Image;

public class ChildableImage extends Image {

	@Override
	public boolean isChildable() {
		return true;
	}

}
