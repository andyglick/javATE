package it.amattioli.guidate.properties;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Textbox;

import junit.framework.TestCase;

public class PropertyNameRetrieverTest extends TestCase {

	public void testRetrieve() {
		Component comp = new Textbox();
		String propertyName = "astringproperty";
		comp.setAttribute(PropertyNameRetriever.PROPERTY_NAME, propertyName);
		PropertyNameRetriever retriever = new PropertyNameRetriever(comp);
		String result = retriever.retrieve();
		assertEquals(propertyName, result);
	}
	
	public void testRetrieveWithIndex() {
		Component comp = new Textbox();
		String propertyName = "astringproperty";
		comp.setAttribute(PropertyNameRetriever.PROPERTY_NAME, propertyName);
		comp.setAttribute(PropertyNameRetriever.PROPERTY_INDEX, 3L);
		PropertyNameRetriever retriever = new PropertyNameRetriever(comp);
		String result = retriever.retrieve();
		assertEquals(propertyName, result);
	}
	
	public void testRetrieveComplete() {
		Component comp = new Textbox();
		String propertyName = "astringproperty";
		comp.setAttribute(PropertyNameRetriever.PROPERTY_NAME, propertyName);
		PropertyNameRetriever retriever = new PropertyNameRetriever(comp);
		String result = retriever.retrieveComplete();
		assertEquals(propertyName, result);
	}
	
	public void testRetrieveCompleteWithIndex() {
		Component comp = new Textbox();
		String propertyName = "astringproperty";
		comp.setAttribute(PropertyNameRetriever.PROPERTY_NAME, propertyName);
		comp.setAttribute(PropertyNameRetriever.PROPERTY_INDEX, 3L);
		PropertyNameRetriever retriever = new PropertyNameRetriever(comp);
		String result = retriever.retrieveComplete();
		assertEquals(propertyName+"[3]", result);
	}
	
}
