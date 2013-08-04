package it.amattioli.encapsulate.dates.hibernate;

import it.amattioli.dominate.hibernate.types.TypeLogger;
import it.amattioli.encapsulate.dates.Day;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

public class DayHibernateType implements UserType {
	public static final TypeLogger typeLogger = new TypeLogger("DayHibernateType");
	private static final int[] SQL_TYPES = {Types.TIMESTAMP};

	/**
	 *
	 */
	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	/**
	 *
	 */
	public Class returnedClass() {
		return Day.class;
	}

	/**
	 *
	 */
	public boolean equals(Object x, Object y) throws HibernateException {
		if ( x == y ) {
			return true;
		}
		if ( x == null || y == null ) {
			return false;
		}
		return x.equals(y);
	}

	/**
	 *
	 */
	public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner)
	    throws HibernateException, SQLException {
		Timestamp d = resultSet.getTimestamp(names[0]);
		Object result;
		if (resultSet.wasNull()) {
			result = null;
		} else {
			result = new Day(d);
		}
		typeLogger.logGet(result, names[0]);
		return result;
	}

	/**
	 *
	 */
	public void nullSafeSet(PreparedStatement statement, Object value, int index)
	    throws HibernateException, SQLException {
		typeLogger.logSet(value, index);
		if (value == null) {
			statement.setNull(index, Types.DATE);
		} else {
			Day d = (Day)value;
			statement.setTimestamp(index, new Timestamp(d.getInitTime().getTime()));
		}
	}

	/**
	 *
	 */
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	/**
	 *
	 */
	public boolean isMutable() {
		return false;
	}

	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return (Day)cached;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Day)value;
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return (Day)original;
	}

	public int hashCode(Object x) throws HibernateException {
		return ((Day)x).hashCode();
	}

}
