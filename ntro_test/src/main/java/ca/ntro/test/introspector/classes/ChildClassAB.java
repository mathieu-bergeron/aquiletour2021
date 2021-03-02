package ca.ntro.test.introspector.classes;

import ca.ntro.test.introspector.interfaces.ParentInterfaceA;
import ca.ntro.test.introspector.interfaces.ParentInterfaceB;

public class ChildClassAB extends ParentClassAB {

	@Override
	public void abstractMethod(ParentInterfaceA argA, ParentInterfaceB argB) {
	}

	@Override
	public void interfaceMethodA() {
	}

}
