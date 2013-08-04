package it.amattioli.dominate.lazy;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.RepositoryRegistry;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * A CGLib method interceptor for lazy loaded entities.
 * 
 * Using the static {@link #newInstance(Class, Serializable)} method you can create instances
 * of an entity class whose state can be loaded from the repository only when needed.
 * 
 * @author andrea
 *
 * @param <I>
 */
public class LazyEntity<I extends Serializable> implements MethodInterceptor {
	private Class<? extends Entity<I>> entityClass;
	private I id;
	private Entity<I> target;
	
	/**
	 * Create a new instance of an entity class whose state can be loaded from the repository 
	 * only when needed.
	 * 
	 * @param <I> The class of the entity id
	 * @param <T> The class of the entity
	 * @param entityClass The class object of the entity
	 * @param id The entity id
	 * @return
	 */
	public static <I extends Serializable, T extends Entity<I>> T newInstance(Class<T> entityClass, I id) {
		Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(entityClass);
        enhancer.setInterfaces(new Class[] {Lazy.class});
        enhancer.setCallback(new LazyEntity<I>(entityClass, id));
        T result = (T)enhancer.create();
        return result;
	}
	
	public LazyEntity(Class<? extends Entity<I>> entityClass, I id) {
		this.entityClass = entityClass;
		this.id = id;
	}
	
	private Entity<I> getTarget() {
		if (target == null) {
			target = RepositoryRegistry.instance().getRepository(entityClass).get(id);
			if (target == null) {
				throw new LazyLoadingException(entityClass, id);
			}
		}
		return target;
	}
	
	@Override
	public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		if ("wasLoaded".equals(method.getName())) {
			return target != null;
		}
		if ("getId".equals(method.getName())) {
			return id;
		}
		if ("finalize".equals(method.getName()) && target == null) {
			return null;
		}
		try {
			return methodProxy.invoke(getTarget(), args);
		} catch(InvocationTargetException e) {
			throw e.getCause();
		}
	}

}
