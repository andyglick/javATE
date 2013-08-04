package it.amattioli.applicate.editors;

import org.apache.commons.beanutils.DynaProperty;

public interface BeanEditorProperty {

	public String getName();

	public DynaProperty getDynaProperty();

	public Object get();

	public Object getIndexed(int idx);

	public void set(Object value);

	public void setIndexed(int idx, Object value);

}