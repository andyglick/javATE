package it.amattioli.dominate.morphia;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;

import it.amattioli.dominate.Entity;

public class MorphiaEntity implements Entity<ObjectId> {
	private static final int PRIME_NUMBER = 3512835;
	private static final int NONZERO_ODD_NUMBER = 274699673;
	
	@Id private ObjectId id;
	private Integer hashCode;
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
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
                return getId().equals(ent.getId()) && 
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
}
