package ca.ntro.core.introspection;

public interface ClassSignature {
	
	String simpleName();
	String name();

	boolean ifImplements(Class<?> interfaceClass);
	boolean ifExtends(Class<?> _class);

}
