package ca.ntro.jdk.introspection;

import ca.ntro.core.introspection.ClassSignature;

public class ClassSignatureJdk implements ClassSignature {
	
	private Class<?> _class;
	
	public ClassSignatureJdk(Class<?> _class) {
		this._class = _class;
	}

}
