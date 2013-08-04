package it.amattioli.dominate.proxies;

public class ProxyUtils {
	private static final String CGLIB_MARKER = "$EnhancerByCGLIB$";
	private static final String JAVASSIST_MARKER = "$$_javassist";

	public static <T> Class<? super T> unProxyClass(Class<T> beanClass) {
		Class<? super T> result = beanClass;
		if (isProxy(result)) {
			result = result.getSuperclass();
		}
		return result;
	}

	public static boolean isProxy(Class<?> result) {
		return result.getName().contains(CGLIB_MARKER) 
		    || result.getName().contains(JAVASSIST_MARKER);
	}
	
}
