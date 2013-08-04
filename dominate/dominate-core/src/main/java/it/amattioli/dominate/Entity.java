package it.amattioli.dominate;

import java.io.Serializable;

/**
 * This interface should be implemented by all entity objects.
 * 
 * <p>An entity is an object whose identity does not come from its attributes
 * but from an independent identification schema</p>
 * 
 * <p>In our case the identification schema is a particular property
 * called "id" whose class is a generic parameter of this interface.</p>
 * 
 * <p>The id property is the identifier for objects that implements this interface
 * so equals and hashCode methods must be implemented so to use this property
 * to accomplish their job</p>
 * 
 * <p>For details on entity objects see Eric Evans, Domain Driven Design [Addison Wesley] Ch. 4</p>
 *
 * @author a.mattioli
 *
 * @param <I> The id property class
 */
public interface Entity<I extends Serializable> {

    /**
     * Returns this entity object identifier.
     *
     * @return this entity object identifier
     */
    public I getId();

    /**
     * Sets this entity object identifier.
     *
     * @param id the identifier to be set
     */
    public void setId(I id);

}
