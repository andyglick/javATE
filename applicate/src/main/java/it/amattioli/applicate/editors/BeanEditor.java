package it.amattioli.applicate.editors;

/**
 * A very basic interface for editors.
 * 
 * Every editor has the concept of an object (a bean) that it can edit.
 * This interface declares the methods that let you set and retrieve 
 * this object.
 * 
 * @author andrea
 *
 * @param <T> The class of the bean that is being edited
 */
public interface BeanEditor<T> {

	/**
	 * Set the bean that is being edited by this editor
	 * 
	 * @param editingBean the bean that is being edited
	 */
	public void setEditingBean(T editingBean);

	/**
	 * Retrieves the bean that is being edited
	 * 
	 * @return the bean that is being edited
	 */
	public T getEditingBean();

}
