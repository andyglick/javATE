package it.amattioli.dominate.properties;

import it.amattioli.dominate.properties.PropertyAvailabilityRetriever;
import it.amattioli.dominate.properties.PropertyAvailabilityRetrieverImpl;
import junit.framework.TestCase;

public class PropertyAvailabilityRetrieverTest extends TestCase {

	public static final class MyObject {
		
		public void setDescription(String val) {
			
		}

		public String getDescription() {
			return "";
		}
		
		public void setDynamic(String val) {
			
		}
		
		public String getDynamic() {
			return "";
		}
		
		public boolean isDynamicAvailable() {
			return false;
		}
		
		public void setIndexed(int idx, String val) {
			
		}
		
		public String getIndexed(int idx) {
			return "";
		}
		
		public boolean isIndexedAvailable(int idx) {
			return idx < 5;
		}
		
	}

	public void testAvailableProperty() {
		PropertyAvailabilityRetriever retriever = new PropertyAvailabilityRetrieverImpl(new MyObject());
		assertTrue(retriever.isPropertyAvailable("description"));
	}
	
	public void testNoAvailableProperty() {
		PropertyAvailabilityRetriever retriever = new PropertyAvailabilityRetrieverImpl(new MyObject());
		assertFalse(retriever.isPropertyAvailable("derivedDescription"));
	}
	
	public void testDynamicAvailableProperty() {
		PropertyAvailabilityRetriever retriever = new PropertyAvailabilityRetrieverImpl(new MyObject());
		assertFalse(retriever.isPropertyAvailable("dynamic"));
	}
	
	public void testDelegateAvailableProperty() {
		PropertyAvailabilityRetriever delegate = new PropertyAvailabilityRetrieverImpl(new MyObject());
		PropertyAvailabilityRetriever retriever = new PropertyAvailabilityRetrieverImpl(delegate);
		assertTrue(retriever.isPropertyAvailable("description"));
	}
	
	public void testIndexedAvailableProperty() {
		PropertyAvailabilityRetriever retriever = new PropertyAvailabilityRetrieverImpl(new MyObject());
		assertTrue(retriever.isPropertyAvailable("indexed[1]"));
	}
	
//	public void testIndexedNoAvailableProperty() {
//		PropertyAvailabilityRetriever retriever = new PropertyAvailabilityRetrieverImpl(new MyObject());
//		assertFalse(retriever.isPropertyAvailable("indexed[10]"));
//	}
}
