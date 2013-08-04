package it.amattioli.dominate.hibernate.specifications;

import java.util.Properties;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.type.Type;
import org.hibernate.usertype.UserType;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.hibernate.types.EnumUserType;
import it.amattioli.dominate.specifications.Assembler;
import it.amattioli.dominate.specifications.MultipleEnumSpecification;

import static it.amattioli.dominate.hibernate.specifications.HqlUtils.*;

public class HqlMultipleEnumSpecification<T extends Entity<?>, U extends Enum<U>> extends MultipleEnumSpecification<T, U> {
	private String alias;
	private Type hibernateType;
	
	public HqlMultipleEnumSpecification() {

	}
	
	public HqlMultipleEnumSpecification(String propertyName, Class<U> enumClass) {
		super(propertyName, enumClass);
	}
	
	public HqlMultipleEnumSpecification(String propertyName, Class<U> enumClass, String alias) {
		this(propertyName, enumClass);
		this.alias = alias;
	}
	
	public HqlMultipleEnumSpecification(String propertyName, Class<U> enumClass, Class<? extends UserType> hibernateType) {
		super(propertyName, enumClass);
		this.hibernateType = Hibernate.custom(hibernateType);
	}
	
	public HqlMultipleEnumSpecification(String propertyName, Class<U> enumClass, Class<? extends UserType> hibernateType, String alias) {
		this(propertyName, enumClass, hibernateType);
		this.alias = alias;
	}
	
	@Override
	protected void setEnumClass(Class<U> enumClass) {
		super.setEnumClass(enumClass);
		Properties props = new Properties();
		props.put(EnumUserType.ENUM_CLASS_PARAM, enumClass.getName());
		this.hibernateType = Hibernate.custom(EnumUserType.class, props);
	}

	@Override
	public void itselfAssembleQuery(Assembler assembler) {
		addHqlCondition((HqlAssembler)assembler);
		setHqlParam((HqlAssembler)assembler);
	}
	
	public boolean itselfSupportsAssembler(Assembler assembler) {
		return assembler instanceof HqlAssembler;
	}

	public void addHqlCondition(HqlAssembler assembler) {
		if (wasSet()) {
	        assembler.newCriteria();
	        if (alias != null) {
	        	assembler.append(alias).append(".");
	        } else {
            	assembler.append(assembler.getAliasPrefix());
            }
	        assembler.append(hqlPropertyName(getPropertyName()))
			         .append(" in ( ");
	        for (int i = 0; i < getValue().size(); i++) {
	        	assembler.append(":").append(normalizedPropertyName(getPropertyName())).append(Integer.toString(i));
				if (i != getValue().size() - 1) {
					assembler.append(" , ");
				}
	        }
	        assembler.append(" )");
		}
    }
	
	public void setHqlParam(HqlAssembler assembler) {
		if (wasSet()) {
	    	assembler.addParameter(new HqlAssembler.ParameterSetter() {
	
				@Override
				public void setParameter(Query query) {
					int i = 0;
		            for (U value : getValue()) {
		                query.setParameter(normalizedPropertyName(getPropertyName()) + (i++), value, hibernateType);
		            }
				}
	    		
	    	});
		}
    }

}
