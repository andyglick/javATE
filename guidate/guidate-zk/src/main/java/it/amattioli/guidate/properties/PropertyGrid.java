package it.amattioli.guidate.properties;

import org.zkoss.zul.Grid;

public class PropertyGrid extends Grid {
	
	public void setPropertyName(String propertyName) {
		setAttribute(PropertyNameRetriever.PROPERTY_NAME, propertyName);
	}
	
	public String getPropertyName() {
		return (String)getAttribute(PropertyNameRetriever.PROPERTY_NAME);
	}
	
	public void setGroupBy(String groupBy) {
		setAttribute(CollectionPropertyComposer.GROUP_BY_ATTRIBUTE, groupBy);
	}
	
	public String getGroupBy() {
		return (String)getAttribute(CollectionPropertyComposer.GROUP_BY_ATTRIBUTE);
	}

}
