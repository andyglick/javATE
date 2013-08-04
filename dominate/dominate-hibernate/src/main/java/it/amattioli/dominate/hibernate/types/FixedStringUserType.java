package it.amattioli.dominate.hibernate.types;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.usertype.UserType;

/**
 * Custom class for trimming strings on the way out from the database, Hibernate 3 version
 * @author Paul Newport
 */

public class FixedStringUserType implements UserType {

    /**
     * default constructor
     */
    public FixedStringUserType() {

    }

    /**
     * @see org.hibernate.usertype.UserType#sqlTypes()
     */
    public int[] sqlTypes() {
        return new int[] { Types.CHAR };
    }

    /**
     * @see org.hibernate.usertype.UserType#returnedClass()
     */
    public Class returnedClass() {
        return String.class;
    }

    /**
     * @see org.hibernate.usertype.UserType#equals(java.lang.Object, java.lang.Object)
     */
    public boolean equals(Object x, Object y) {
        return (x == y) || (x != null && y != null && (x.equals(y)));
    }

    /**
     * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], java.lang.Object)
     */
    public Object nullSafeGet(ResultSet inResultSet, String[] names, Object o) throws SQLException {
        String val = (String) Hibernate.STRING.nullSafeGet(inResultSet, names[0]);
        return StringUtils.trim(val);
    }

    /**
     * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int)
     */
    public void nullSafeSet(PreparedStatement inPreparedStatement, Object o, int i) throws SQLException {
        String val = (String) o;
        inPreparedStatement.setString(i, val);
    }

    /**
     * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
     */
    public Object deepCopy(Object o) {
        if (o == null) {
            return null;
        }
        return new String(((String) o));
    }

    /**
     * @see org.hibernate.usertype.UserType#isMutable()
     */
    public boolean isMutable() {
        return false;
    }

    /**
     * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable, java.lang.Object)
     */
    public Object assemble(Serializable cached, Object owner) {
        return cached;
    }

    /**
     * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
     */
    public Serializable disassemble(Object value) {
        return (Serializable) value;
    }

    /**
     * @see org.hibernate.usertype.UserType#replace(java.lang.Object, java.lang.Object, java.lang.Object)
     */
    public Object replace(Object original, Object target, Object owner) {
        return original;
    }

    /**
     * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
     */
    public int hashCode(Object x) {
        return x.hashCode();
    }

}
