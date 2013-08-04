package it.amattioli.encapsulate.money.hibernate;

import it.amattioli.dominate.hibernate.types.TypeLogger;
import it.amattioli.encapsulate.money.Euro;
import it.amattioli.encapsulate.money.Money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

public class EuroHibernateType implements UserType {
	public static final TypeLogger typeLogger = new TypeLogger("EuroHibernateType");
	private static final int[] SQL_TYPES = { Types.DECIMAL };

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
		return Money.class;
	}

	/**
	 * 
	 */
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y) return true;
		if (x == null || y == null) return false;
		return x.equals(y);
	}

	/**
	 * 
	 */
	public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws HibernateException, SQLException {
		BigDecimal d = resultSet.getBigDecimal(names[0]);
		Object result;
		if (resultSet.wasNull()) {
			result = null;
		} else {
			result = Money.euro(d);
		}
		typeLogger.logGet(result, names[0]);
		return result;
	}

	/**
	 * 
	 */
	public void nullSafeSet(PreparedStatement statement, Object value, int index) throws HibernateException, SQLException {
		typeLogger.logSet(value, index);
		if (value == null) {
			statement.setNull(index, Types.DECIMAL);
		} else {
			Money e = (Money)value;
			statement.setBigDecimal(index, e.getValue());
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
		return cached;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Money)value;
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	public int hashCode(Object x) throws HibernateException {
		return ((Money)x).hashCode();
	}

}
