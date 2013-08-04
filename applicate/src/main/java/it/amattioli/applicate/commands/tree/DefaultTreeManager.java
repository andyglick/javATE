package it.amattioli.applicate.commands.tree;

import it.amattioli.applicate.editors.BeanEditor;
import it.amattioli.applicate.editors.BeanEditorImpl;
import it.amattioli.dominate.proxies.ProxyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

public class DefaultTreeManager<T> extends AbstractTreeManager<T> {
	private Class<T> nodeClass;
	private String childrenPropertyName;
	private String parentPropertyName;
	
	public DefaultTreeManager(T root, String childrenPropertyName, String parentPropertyName) {
		super(root);
		this.childrenPropertyName = childrenPropertyName;
		this.parentPropertyName = parentPropertyName;
		initNodeClass();
	}
	
	public DefaultTreeManager(T root) {
		this(root, "children", "parent");
	}
	
	public void setRoot(T root) {
		super.setRoot(root);
		initNodeClass();
	}
	
	private void initNodeClass() {
		if (root != null) {
			nodeClass = (Class<T>)ProxyUtils.unProxyClass(root.getClass());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getChildrenOf(T target) {
		try {
			return (List<T>) PropertyUtils.getProperty(target, childrenPropertyName);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getParentOf(T target) {
		try {
			return (T) PropertyUtils.getProperty(target, parentPropertyName);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void addChild(T parent, T child) {
		try {
			PropertyUtils.setProperty(child, parentPropertyName, parent);
			List<T> children = (List<T>)PropertyUtils.getProperty(parent, childrenPropertyName);
			children.add(child);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public BeanEditor<T> addChild(T parent) {
		T node = createNode();
		addChild(parent, node);
		return createNodeEditor(node);
	}
	
	public T createNode() {
		try {
			return nodeClass.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T remove(T toBeRemoved) {
		try {
			T parent = getParentOf(toBeRemoved);
			PropertyUtils.setProperty(toBeRemoved, parentPropertyName, null);
			List<T> children = (List<T>)PropertyUtils.getProperty(parent, childrenPropertyName);
			children.remove(toBeRemoved);
			return toBeRemoved;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public BeanEditor<T> createNodeEditor(T node) {
		return new BeanEditorImpl<T>(node);
	}

}
