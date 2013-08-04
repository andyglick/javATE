package it.amattioli.dominate.properties;

import it.amattioli.dominate.properties.PropertyClass;
import it.amattioli.dominate.properties.PropertyClassRetrieverImpl;
import it.amattioli.dominate.properties.PropertyNotFoundException;
import junit.framework.TestCase;

public class PropertyClassRetrieverTest extends TestCase {

	public void testSimpleProperty() {
		Object bean = new BeanStub();
		PropertyClassRetrieverImpl retriever = new PropertyClassRetrieverImpl(bean);
		PropertyClass propertyClass = retriever.retrievePropertyClass("simpleProperty");
		assertEquals(String.class, propertyClass.getElementClass());
		assertFalse(propertyClass.isMultiple());
	}

	public void testCollectionProperty() {
		Object bean = new BeanStub();
		PropertyClassRetrieverImpl retriever = new PropertyClassRetrieverImpl(bean);
		PropertyClass propertyClass = retriever.retrievePropertyClass("collectionProperty");
		assertEquals(Integer.class, propertyClass.getElementClass());
		assertTrue(propertyClass.isMultiple());
	}

	public void testIndexedProperty() {
		Object bean = new BeanStub();
		PropertyClassRetrieverImpl retriever = new PropertyClassRetrieverImpl(bean);
		PropertyClass propertyClass = retriever.retrievePropertyClass("indexedProperty");
		assertEquals(String.class, propertyClass.getElementClass());
	}

	public void testUnknownProperty() {
		Object bean = new BeanStub();
		PropertyClassRetrieverImpl retriever = new PropertyClassRetrieverImpl(bean);
		try {
			PropertyClass propertyClass = retriever.retrievePropertyClass("unknownProperty");
			fail();
		} catch (PropertyNotFoundException e) {
			assertEquals("Property unknownProperty not found on bean BeanStub", e.getMessage());
		}
	}

	public void testDottedProperty() {
		BeanStub bean = new BeanStub();
		bean.setAssoc(new BeanStub());
		PropertyClassRetrieverImpl retriever = new PropertyClassRetrieverImpl(bean);
		PropertyClass propertyClass = retriever.retrievePropertyClass("assoc.simpleProperty");
		assertEquals(String.class, propertyClass.getElementClass());
		assertFalse(propertyClass.isMultiple());
	}
	
	public void testDottedCollectionProperty() {
		BeanStub bean = new BeanStub();
		bean.setAssoc(new BeanStub());
		PropertyClassRetrieverImpl retriever = new PropertyClassRetrieverImpl(bean);
		PropertyClass propertyClass = retriever.retrievePropertyClass("assoc.collectionProperty");
		assertEquals(Integer.class, propertyClass.getElementClass());
		assertTrue(propertyClass.isMultiple());
	}
	
	public void testNullBean() {
		PropertyClassRetrieverImpl retriever = new PropertyClassRetrieverImpl(null);
		PropertyClass propertyClass = retriever.retrievePropertyClass("simpleProperty");
		assertNull(propertyClass);
	}
}
