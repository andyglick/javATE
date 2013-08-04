package it.amattioli.encapsulate.dates.hibernate;

import it.amattioli.encapsulate.dates.TimeInterval;
import it.amattioli.encapsulate.dates.TimeIntervalFactory;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

public class TimeIntervalHibernateType implements CompositeUserType {
	private static final Type[] TYPES = {Hibernate.TIMESTAMP, Hibernate.TIMESTAMP};

	@Override
	public String[] getPropertyNames() {
		return new String[] {"low", "high"};
	}

	@Override
	public Type[] getPropertyTypes() {
		return TYPES;
	}

	@Override
	public Object getPropertyValue(Object component, int property) throws HibernateException {
		TimeInterval t = (TimeInterval)component;
		if (property == 0) {
			return t.isLowBounded() ? t.getLow() : null;
		} else {
			return t.isHighBounded() ? t.getHigh() : null;
		}
	}

	@Override
	public void setPropertyValue(Object component, int property, Object value) throws HibernateException {
		throw new UnsupportedOperationException("Immutable TimeInterval");
	}

	@Override
	public Class<?> returnedClass() {
		return TimeInterval.class;
	}
	
	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if ( x == y ) {
			return true;
		}
		if ( x == null || y == null ) {
			return false;
		}
		return x.equals(y);
	}
	
	@Override
	public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor s, Object owner) throws HibernateException, SQLException {
		Date begin = (Date) Hibernate.TIMESTAMP.nullSafeGet(resultSet, names[0]);
        Date end = (Date) Hibernate.TIMESTAMP.nullSafeGet(resultSet, names[1]);
        if (begin == null && end == null) {
        	return null;
        }
        TimeInterval result = (new TimeIntervalFactory()).createTimeInterval(begin, end);
		return result;
	}

	@Override
	public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor s) throws HibernateException, SQLException {
		TimeInterval t = (TimeInterval)value;
		Date low = t != null && t.isLowBounded() ? t.getLow() : null;
		Date high = t != null && t.isHighBounded() ? t.getHigh() : null;
		Hibernate.TIMESTAMP.nullSafeSet(statement, low,  index);
        Hibernate.TIMESTAMP.nullSafeSet(statement, high, index + 1);
	}
	
	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Object assemble(Serializable cached, SessionImplementor s, Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Serializable disassemble(Object value, SessionImplementor s) throws HibernateException {
		return (Serializable)value;
	}

	@Override
	public Object replace(Object original, Object target, SessionImplementor s, Object owner) throws HibernateException {
		return original;
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}
}
