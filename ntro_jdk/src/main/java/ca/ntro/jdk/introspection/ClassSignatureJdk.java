package ca.ntro.jdk.introspection;

import ca.ntro.core.introspection.ClassSignature;

public class ClassSignatureJdk implements ClassSignature {
	
	private Class<?> _class;
	
	public ClassSignatureJdk(Class<?> _class) {
		this._class = _class;
	}

	@Override
	public String simpleName() {
		return _class.getSimpleName();
	}

	@Override
	public String name() {
		return _class.getName();
	}

	@Override
	public boolean ifImplements(Class<?> interfaceClass) {
		boolean ifImplements = false;

		Class<?>[] interfaces = _class.getInterfaces();
		
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

	@Override
	public boolean ifExtends(Class<?> _class) {
		// TODO Auto-generated method stub
		return false;
	}

}
