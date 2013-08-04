package it.amattioli.encapsulate.money.hibernate;

import it.amattioli.dominate.hibernate.types.TypeLogger;
import it.amattioli.encapsulate.money.Money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

public class MoneyHibernateType implements CompositeUserType {
	public static final TypeLogger typeLogger = new TypeLogger("DayHibernateType");
	private static final Type[] TYPES = {Hibernate.BIG_DECIMAL, Hibernate.CURRENCY};

	@Override
	public String[] getPropertyNames() {
		return new String[] {"value", "currency"};
	}

	@Override
	public Type[] getPropertyTypes() {
		return TYPES;
	}

	@Override
	public Object getPropertyValue(Object component, int property) throws HibernateException {
		if (property == 0) {
			return ((Money)component).getValue();
		} else {
			return ((Money)component).getCurrency();
		}
	}

	@Override
	public void setPropertyValue(Object component, int property, Object value) throws HibernateException {
		throw new UnsupportedOperationException("Immutable Money");
	}

	@Override
	public Class<?> returnedClass() {
		return Money.class;
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
		BigDecimal value = (BigDecimal)Hibernate.BIG_DECIMAL.nullSafeGet(resultSet, names[0]);
		Currency currency = (Currency)Hibernate.CURRENCY.nullSafeGet(resultSet, names[1]);
		Money result = new Money(value, currency);
		typeLogger.logGet(result, names[0]);
		return result;
	}

	@Override
	public void nullSafeSet(PreparedStatement statement, Object value, int index, SessionImplementor s) throws HibernateException, SQLException {
		typeLogger.logSet(value, index);
		Money m = (Money)value;
		BigDecimal val = m == null ? null : m.getValue();
		Currency cur = m == null ? null : m.getCurrency();
		Hibernate.BIG_DECIMAL.nullSafeSet(statement, val, index);
		Hibernate.CURRENCY.nullSafeSet(statement, cur, index + 1);
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
