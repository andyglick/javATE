package it.amattioli.guidate.smarttab;

import org.zkoss.zk.ui.Component;

public abstract class SmartTabDescriptor {
	private String identifier;
	private String label;
	private String image;

	public SmartTabDescriptor() {
		
	}
	
	public SmartTabDescriptor(String identifier, String label) {
		setIdentifier(identifier);
		setLabel(label);
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public abstract Component getPanelContent();

}