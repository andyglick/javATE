package it.amattioli.workstate.actions;

import java.util.*;

public interface AttributeReader {

	public Object getAttribute(String tag);

	public Map<String,?> getAllAttributes();

}
