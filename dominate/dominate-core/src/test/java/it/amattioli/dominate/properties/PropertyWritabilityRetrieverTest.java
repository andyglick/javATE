package it.amattioli.dominate.properties;

import it.amattioli.dominate.properties.PropertyWritabilityRetriever;
import it.amattioli.dominate.properties.PropertyWritabilityRetrieverImpl;
import junit.framework.TestCase;

public class PropertyWritabilityRetrieverTest extends TestCase {

	public static final class MyObject {
		
		public void setDescription(String val) {
			
		}

		public String getDescription() {
			return "";
		}
		
		public String getDerivedDescription() {
			return getDescription() + " ";
		}
		
		public void setDynamic(String val) {
			
		}
		
		public String getDynamic() {
			return "";
		}
		
		public boolean isDynamicWritable() {
			return false;
		}
		
	}

	public void testWritableProperty() {
		PropertyWritabilityRetriever retriever = new PropertyWritabilityRetrieverImpl(new MyObject());
		assertTrue(retriever.isPropertyWritable("description"));
	}
	
	public void testNoWritableProperty() {
		PropertyWritabilityRetriever retriever = new PropertyWritabilityRetrieverImpl(new MyObject());
		assertFalse(retriever.isPropertyWritable("derivedDescription"));
	}
	
	public void testDynamicWritableProperty() {
		PropertyWritabilityRetriever retriever = new PropertyWritabilityRetrieverImpl(new MyObject());
		assertFalse(retriever.isPropertyWritable("dynamic"));
	}
	
	public void testDelegateWritableProperty() {
		PropertyWritabilityRetriever delegate = new PropertyWritabilityRetrieverImpl(new MyObject());
		PropertyWritabilityRetriever retriever = new PropertyWritabilityRetrieverImpl(delegate);
		assertTrue(retriever.isPropertyWritable("description"));
	}
}
