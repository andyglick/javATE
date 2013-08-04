package it.amattioli.dominate;

import it.amattioli.dominate.properties.PropertyChangeEmitter;
import it.amattioli.dominate.properties.PropertyChangeSupport;

import java.beans.PropertyChangeListener;

import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Typical implementation of an entity object whose identifier is a Long
 * value. 
 * 
 * <p>This implementation can be used, for example, as a base class
 * for object persisted on a relational database and whose primary key
 * is generated using a sequence or an id field.</p>
 * 
 * <p>It has optimistic lock version property and property change
 *    support too
 * </p>
 * 
 * @author a.mattioli
 *
 */
public class EntityImpl implements Entity<Long>, PropertyChangeEmitter {
    private static final int PRIME_NUMBER = 3512835;
	private static final int NONZERO_ODD_NUMBER = 274699673;
	private Long id;
    private Long version;
    private Integer hashCode;
    private PropertyChangeSupport pChange = new PropertyChangeSupport(this);

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = (Long) id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(final Long version) {
        this.version = version;
    }

    /**
     * Two entities are equals if they have the same id and their classes
     * are compatible.
     *
     * @param obj the object with which to compare
     * @return true if this object is equal to obj, false otherwise
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (getId() != null) {
            if (obj instanceof Entity) {
                Entity<?> ent = (Entity<?>) obj;
                return getId().equals(ent.getId()) && // getClass().equals(ent.getClass());
                        (ent.getClass().isInstance(this) || this.getClass().isInstance(ent));
            } else {
                return false;
            }
        } else {
            return super.equals(obj);
        }
    }

    /**
     * Calculate this entity object hash code using its id
     *
     * @return this entity object hash code
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        if (hashCode == null) {
            if (getId() == null) {
                hashCode = super.hashCode();
            } else {
                hashCode = new HashCodeBuilder(NONZERO_ODD_NUMBER, PRIME_NUMBER).append(this.getId()).toHashCode();
            }
        }
        return hashCode.intValue();
    }

    /**
     * Return a string containing class name, id and version of the entity
     * 
     *  @return a string containing class name, id and version of the entity
     */
    @Override
    public String toString() {
        return "[" + this.getClass().getName() + " id=" + getId() + " version=" + getVersion() + "]";
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		pChange.firePropertyChange(propertyName, oldValue, newValue);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
	    pChange.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
	    pChange.removePropertyChangeListener(listener);
	}

}
