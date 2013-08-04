package it.amattioli.dominate.hibernate.types;

import java.io.Serializable; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Types; 
import java.util.Properties;

import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType; 

public class EnumUserType<E extends Enum<E>> implements UserType, ParameterizedType {
	public static final TypeLogger typeLogger = new TypeLogger("EnumUserType");
	public static final String ENUM_CLASS_PARAM = "enumClass";
	private static final int[] SQL_TYPES = {Types.VARCHAR};
 
    private Class<E> clazz = null;
    
    public EnumUserType() {
    	
    }
    
    protected EnumUserType(Class<E> c) { 
        this.clazz = c; 
    } 
     
    public int[] sqlTypes() { 
        return SQL_TYPES; 
    } 
 
    public Class<E> returnedClass() { 
        return clazz; 
    } 
 
    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws SQLException { 
        String name = resultSet.getString(names[0]); 
        E result = null; 
        if (!resultSet.wasNull()) { 
            result = Enum.valueOf(clazz, name); 
        }
        typeLogger.logGet(result, names[0]);
        return result; 
    } 
 
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index) throws SQLException {
    	typeLogger.logSet(value, index);
        if (null == value) { 
        	preparedStatement.setNull(index, Types.VARCHAR); 
        } else {
        	String toBeSet = ((Enum<?>)value).name();
        	preparedStatement.setString(index, toBeSet); 
        } 
    } 
 
    public Object deepCopy(Object value) { 
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
			String s =(String)props.get(ENUM_CLASS_PARAM);
			try {
				clazz = (Class<E>)Class.forName(s);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException("enumClass "+s+" not found");
			}
		}
	}
    
    
} 