package it.amattioli.dominate;

import java.io.Serializable;
import java.util.List;

/**
 * A repository is an object that encapsulate access to a group of entities.
 * You can add new entities to the group, retrieve an entity by id, retrieve
 * all the entities contained in this repository or filter them. 
 *
 * @author a.mattioli
 *
 */
public interface Repository<I extends Serializable, T extends Entity<I>> extends Cloneable {

    /**
     * Retrieve an entity from this repository using its id.
     *
     * @param id the id of the entity you want to retrieve.
     * @return the entity, contained in this repository, whose id is the same
     *         as the parameter
     */
    public T get(I id);

    /**
     * Add a new entity to this repository.
     *
     * @param object the entity you want to add
     * 
     */
    public void put(T object);

    /**
     * If this repository is associated to an external data source (i.e a 
     * relational database) this method delete an object from the repository
     * local cache so that a {@link #get(Serializable)} will retrieve a fresh
     * one from the data source.
     * If the repository is not associated to an external data source or it
     * has no local cache this method will have no effect. 
     *
     * @param object the object to be refreshed
     */
    public void refresh(T object);

    /**
     * If this repository is associated to an external data source (i.e a 
     * relational database) this method delete an object from the repository
     * local cache so that a {@link #get(Serializable)} will retrieve a fresh
     * one from the data source.
     * If the repository is not associated to an external data source or it
     * has no local cache this method will have no effect. 
     *
     * @param objectId the id of the object to be refreshed
     */
    public void refresh(I objectId);

    /**
     * Check if this repository supports object removal.
     * 
     * @return true if this repository supports object removal
     */
    public boolean isRemoveAllowed();
    
    /**
     * Removes an object from this repository. Before calling this method clients
     * should check id this repository supports object removal using {@link #isRemoveAllowed()}
     * 
     * @param object the object to remove
     * 
     * @throws UnsupportedOperationException if this repository does not support
     *         object removal
     */
    public void remove(T object);
    
    /**
     * Removes an object from this repository. Before calling this method clients
     * should check id this repository supports object removal using {@link #isRemoveAllowed()}
     * 
     * @param objectId the id of the object to remove
     * 
     * @throws UnsupportedOperationException if this repository does not support
     *         object removal
     */
    public void remove(I objectId);
    
    /**
     * Retrieves all the objects contained in this repository.
     * The order of the object inside the list depends on the "order" property.
     * If "first" and "last" properties have been set only a part of the
     * entities will be retrieved. 
     *
     * @return all the entities contained in this repository
     * 
     * @see {@link #setFirst(int)}, {@link #setLast(int)}, {@link #setOrder(String, boolean)}
     */
    public List<T> list();
    
    /**
     * Retrieves the object contained in this repository that has a specified property
     * equals to a certain value.<p>
     * This method makes sense only if the specified property has distinct values
     * for each entity.<p>
     * If there is no entity in this repository that has the specified value, null is
     * returned.<p>
     * if there is more than one entity in this repository that has the specified
     * value, one of them is returned.
     * 
     * @param propertyName the name of the property
     * @param value the property value
     * @return
     */
    public T getByPropertyValue(String propertyName, Object value);
    
    /**
     * Retrieves the objects contained in this repository that satisfied the
     * given specification.
     * 
     * The order of the object inside the list depends on the "order" property.
     * If "first" and "last" properties have been set only a part of the
     * entities will be retrieved.
     * 
     * @param spec the specification that must be satisfied
     * @return the objects contained in this repository that satisfied the
     *         given specification
     */
    public List<T> list(Specification<T> spec);

    /**
     * Set the index of the first object that will be retrieved by
     * {@link #list} and {@link #list(Filter)} when value 0 has been
     * set for 'first' in the filter.<p>
     * The value in the filter, if different than 0, will always superseed
     * this value.
     *
     * @param first the index of the first object that will be retrieved by
     *              {@link #list} e {@link #list(Filter)}
     */
    public void setFirst(int first);

    /**
     * Retrieves the index of the first object that will be retrieved by
     * {@link #list} e {@link #list(Filter)}.
     * 
     * @return the index of the first object that will be retrieved by
     *         {@link #list} e {@link #list(Filter)}
     */
    public int getFirst();

    /**
     * Set the index of the last object that will be retrieved by
     * {@link #list} e {@link #list(Filter)} when value 0 has been
     * set for 'first' in the filter.<p>
     * The value in the filter, if different than 0, will always superseed
     * this value.<p>
     * If the repository contains less objects than the indicated index,
     * all the objects will be retrieved.
     *
     * @param last the index of the last object that will be retrieved by
     *              {@link #list} and {@link #list(Specification)}
     */
    public void setLast(int last);

    /**
     * Retrieves the index of the last object that will be retrieved by
     * {@link #list} e {@link #list(Specification)}.
     * This method will retrieve the same number that has been set using
     * {@link #setLast(int)} independently of the repository content size.
     *
     * @return the index of the last object that will be retrieved by
     *         {@link #list} and {@link #list(Filter)}
     */
    public int getLast();

    /**
     * Set the order for the lists retrieved using
     * {@link #list} and {@link #list(Specification)}.
     * If a previous order property was set it will be replaced.
     *
     * @param property the name of the property that will be used to order
     *                 the lists
     * @param reverse if this parameter is true the order will be ascendant,
     *                otherwise it will be descendant
     */
    public void setOrder(String property, boolean reverse);
    
    /**
     * Add an order property for the lists retrieved using
     * {@link #list} and {@link #list(Specification)}.
     * If a previous order property was set it will remain in place 
     * and the new property will be added to the list.
     * 
     * @param property the name of the property that will be used to order
     *                 the lists
     * @param reverse if this parameter is true the order will be ascendant,
     *                otherwise it will be descendant
     */
    public void addOrder(String property, boolean reverse);
    
    /**
     * Remove the last added order property.
     * If no order was set nothing will happen.
     */
    public void removeLastOrder();

    /**
     * Retrieves the name of the property used for ordering the result of the
     * {@link #list()} and {@link #list(Specification)} methods.
     * 
     * @return the name of the property used for ordering
     */
    public String getOrderProperty();

    /**
     * Returns true if the order of the result of the {@link #list()} and 
     * {@link #list(Specification)} methods is ascending, false otherwise.
     *  
     * @return
     */
    public boolean isReverseOrder();

}
