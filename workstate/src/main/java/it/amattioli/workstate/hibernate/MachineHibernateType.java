package it.amattioli.workstate.hibernate;

import it.amattioli.workstate.config.Registry;
import it.amattioli.workstate.core.Machine;
import it.amattioli.workstate.core.StateMemento;
import it.amattioli.workstate.exceptions.WorkflowException;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MachineHibernateType implements UserType, ParameterizedType {
	private static final Logger logger = LoggerFactory.getLogger(MachineHibernateType.class);

	private static final Object MACHINE_ID_PARAM = "machineId";

	private String id;
	private int sqlType = Types.VARCHAR;
    
    public MachineHibernateType() {
    	
    }

    @Override
    public int[] sqlTypes() { 
        return new int[] { sqlType }; 
    } 
 
    @Override
    public Class<?> returnedClass() { 
        return Machine.class; 
    } 
 
    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner) throws HibernateException, SQLException { 
        String stateTag = resultSet.getString(names[0]); 
        Machine result = null; 
        if (!resultSet.wasNull()) { 
        	StateMemento memento = (stateTag == null) ? null : StateMemento.fromTag(stateTag);
            try {
				result = Registry.instance().newMachine(id, owner, null, memento);
			} catch (WorkflowException e) {
				throw new HibernateException(e);
			}
        } 
        return result; 
    } 
 
    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException { 
        if (null == value) { 
        	logger.debug("binding null to parameter " + index);
            preparedStatement.setNull(index, sqlTypes()[0]); 
        } else {
        	String stateTag = ((Machine)value).getMemento().getTag();
        	logger.debug("binding " + stateTag + " to parameter " + index);
        	preparedStatement.setString(index, stateTag);
        } 
    } 
 
    @Override
    public Object deepCopy(Object value) throws HibernateException{
    	return ((Machine)value).clone(); 
    } 
 
    @Override
    public boolean isMutable() { 
        return true; 
    } 
 
    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {  
         return cached;
    } 

    @Override
    public Serializable disassemble(Object value) throws HibernateException { 
        return (Serializable)value; 
    } 
 
    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException { 
        return original; 
    }
    
    @Override
    public int hashCode(Object x) throws HibernateException { 
        return x.hashCode(); 
    }
    
    @Override
    public boolean equals(Object x, Object y) throws HibernateException { 
        if (x == y) 
            return true; 
        if (null == x || null == y) 
            return false; 
        return x.equals(y); 
    }
    
    @Override
	public void setParameterValues(Properties props) {
		if (props != null) {
			id =(String)props.get(MACHINE_ID_PARAM);
		}
	}
}
