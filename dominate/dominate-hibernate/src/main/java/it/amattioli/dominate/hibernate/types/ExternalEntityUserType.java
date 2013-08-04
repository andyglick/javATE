package it.amattioli.dominate.hibernate.types;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.RepositoryRegistry;
import it.amattioli.dominate.lazy.LazyEntity;

import java.io.Serializable; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Types; 
import java.util.Properties;

import org.hibernate.HibernateException; 
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType; 

public class ExternalEntityUserType<I extends Serializable, T extends Entity<I>> implements UserType, ParameterizedType {
	public static final String ENTITY_CLASS_PARAM = "entityClass";
	public static final String ID_TYPE_PARAM = "idType";
	public static final String LAZY_PARAM = "lazy";
	public static final TypeLogger typeLogger = new TypeLogger("ExternalEntityUserType");

    private Class<T> clazz = null;
    private int sqlType = Types.VARCHAR;
    private boolean lazy = true;
    
    public ExternalEntityUserType() {
    	
    }
    
    protected ExternalEntityUserType(Class<T> c, int sqlType, boolean lazy) { 
        this.clazz = c; 
        this.sqlType = sqlType;
        this.lazy = lazy;
    } 
     
    public int[] sqlTypes() { 
        return new int[] { sqlType }; 
    } 
 
    public Class<T> returnedClass() { 
        return clazz; 
    } 
 
    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws SQLException { 
        I id = (I)resultSet.getString(names[0]); 
        T result = null; 
        if (!resultSet.wasNull()) {
        	if (lazy) {
        		result = LazyEntity.newInstance(clazz, id);
        	} else {
        		result = RepositoryRegistry.instance().getRepository(clazz).get(id);
        	}
        }
        typeLogger.logGet(result, names[0]);
        return result; 
    } 
 
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index) throws SQLException {
    	typeLogger.logSet(value, index);
        if (null == value) { 
            preparedStatement.setNull(index, sqlTypes()[0]); 
        } else {
        	I toBeSet = ((T)value).getId();
        	preparedStatement.setObject(index, toBeSet, sqlTypes()[0]);
        } 
    } 
 
    public Object deepCopy(Object value) throws HibernateException{ 
        return value; 
    } 
 
    public boolean isMutable() { 
        return false; 
    } 
 
    public Object assemble(Serializable cached, Object owner) {  
         return cached;
    } 

    public Serializable disassemble(Object value) { 
        return (Serializable)value; 
    } 
 
    public Object replace(Object original, Object target, Object owner) { 
        return original; 
    }
    
    public int hashCode(Object x) { 
        return x.hashCode(); 
    }
    
    public boolean equals(Object x, Object y) { 
    	if (x == y) {
    		return true;
    	}
    	if (null == x || null == y) {
    		return false;
    	}
    	return x.equals(y);
    }

	@Override
	public void setParameterValues(Properties props) {
		if (props != null) {
			String s =(String)props.get(ENTITY_CLASS_PARAM);
			try {
				clazz = (Class<T>)Class.forName(s);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException("entityClass "+s+" not found");
			}
			String t = (String)props.get(ID_TYPE_PARAM);
			try {
				sqlType = Types.class.getField(t).getInt(null);
			} catch (SecurityException e) {
				throw new IllegalArgumentException("sqlType "+t+" not found");
			} catch (IllegalAccessException e) {
				throw new IllegalArgumentException("sqlType "+t+" not found");
			} catch (NoSuchFieldException e) {
				throw new IllegalArgumentException("sqlType "+t+" not found");
			}
			String l = (String)props.get(LAZY_PARAM);
			lazy = Boolean.parseBoolean(l);
		}
	}

}
