package it.amattioli.workstate.core;

import java.util.*;

import it.amattioli.workstate.exceptions.*;

public class MetaAttribute {
	public static final String ATTRIBUTE_NAME_RESOURCE = "AttrName";
	private String tag;
	private Class<?> attributeClass;
	private Object initialValue;
	private Collection<AttributeValidator> validators = Collections.emptyList();

	public MetaAttribute(String tag, Class<?> attributeClass, Object initial) {
		setTag(tag);
		setAttributeClass(attributeClass);
		setInitialValue(initial);
	}

	public MetaAttribute(String tag, String attributeClassName, Object initial) {
		setTag(tag.trim());
		setAttributeClass(attributeClassName);
		setInitialValue(initial);
	}

	private void setTag(String tag) {
		this.tag = tag.trim();
	}

	public String getTag() {
		return this.tag;
	}

	private void setInitialValue(Object initialValue) {
		try {
			checkValidAttribute(initialValue);
		} catch (WorkflowException e) {
			throw ErrorMessages.newIllegalArgumentException(ErrorMessage.INVALID_ATTRIBUTE_INIT_VALUE, this.toString());
		}
		this.initialValue = initialValue;
	}

	public Object getInitialValue() {
		return this.initialValue;
	}

	private void setAttributeClass(Class<?> attributeClass) {
		if (attributeClass == null) {
			throw ErrorMessages.newNullPointerException(ErrorMessage.NULL_ATTRIBUTE_CLASS);
		}
		this.attributeClass = attributeClass;
	}

	protected static final Class<?> ClassForName(String className) throws ClassNotFoundException {
		return Class.forName(className, true, Thread.currentThread()
				.getContextClassLoader());
	}

	private void setAttributeClass(String attributeClass) {
		if (attributeClass == null) {
			throw ErrorMessages.newNullPointerException(ErrorMessage.NULL_ATTRIBUTE_CLASS);
		}
		attributeClass = attributeClass.trim();
		String attrClass = attributeClass;
		if (attributeClass.endsWith("[]")) {
			attrClass = "[L"
					+ attributeClass.substring(0, attributeClass.length() - 2)
					+ ";";
		}
		try {
			this.attributeClass = ClassForName(attrClass);
		} catch (ClassNotFoundException e) {
			if (attributeClass.endsWith("[]")) {
				attrClass = "[Ljava.lang."
						+ attributeClass.substring(0,
								attributeClass.length() - 2) + ";";
			} else {
				attrClass = "java.lang." + attributeClass;
			}
			try {
				this.attributeClass = ClassForName(attrClass);
			} catch (ClassNotFoundException e1) {
				throw ErrorMessages.newIllegalArgumentException(ErrorMessage.INVALID_ATTRIBUTE_CLASS, 
						                                        attributeClass,
						                                        getTag());
			}
		}
	}

	public Class<?> getAttributeClass() {
		return attributeClass;
	}

	private boolean isValidAttribute(Object obj) {
		return (obj == null) || (this.attributeClass.isAssignableFrom(obj.getClass()));
	}

	public void checkValidAttribute(Object obj) throws WorkflowException {
		if (isValidAttribute(obj)) {
			for (AttributeValidator validator: validators) {
				try {
					validator.validate(obj);
				} catch (WorkflowException e) {
					e.addParameter(ATTRIBUTE_NAME_RESOURCE, "{" + this.tag + "}");
					throw e;
				}
			}
		} else {
			throw ErrorMessages.newClassCastException(ErrorMessage.INVALID_ATTRIBUTE_VALUE_CLASS, 
					                                  this.getTag(),
					                                  attributeClass.getName(), 
					                                  obj.getClass().getName());
		}
	}

	public void addValidator(AttributeValidator validator) {
		if (validators.isEmpty()) {
			validators = new ArrayList<AttributeValidator>();
		}
		validators.add(validator);
	}

	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof MetaAttribute) {
			MetaAttribute ma = (MetaAttribute) obj;
			result = ma.getTag().equals(this.getTag());
		} else {
			result = super.equals(obj);
		}
		return result;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer(tag);
		buffer.append(":");
		buffer.append(attributeClass.getName());
		if (initialValue != null) {
			buffer.append("=");
			buffer.append(initialValue.toString());
		}
		if (!validators.isEmpty()) {
			buffer.append("{");
			boolean first = true;
			for (AttributeValidator validator: validators) {
				if (!first) {
					buffer.append(" && ");
				}
				buffer.append(validator.toString());
				first = false;
			}
			buffer.append("}");
		}
		return buffer.toString();
	}

}