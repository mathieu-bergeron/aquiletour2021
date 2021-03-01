package ca.ntro.jdk.introspection;

import ca.ntro.core.introspection.InterfaceSignature;

public class InterfaceSignatureJdk implements InterfaceSignature {
	
	private Class<?> _interface;

	public InterfaceSignatureJdk(Class<?> _interface) {
		this._interface = _interface;
	}

	@Override
	public boolean ifExtends(Class<?> interfaceClass) {
		boolean ifImplements = false;

		Class<?>[] interfaces = _interface.getInterfaces();
		
		for(Class<?> candidateInterface : interfaces) {
			if(candidateInterface.equals(interfaceClass)) {
				ifImplements = true;
				break;

			}else if(new InterfaceSignatureJdk(candidateInterface).ifExtends(interfaceClass)) {
				ifImplements = true;
				break;
			}
		}

		return ifImplements;
	}

}
