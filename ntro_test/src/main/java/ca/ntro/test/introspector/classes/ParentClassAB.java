package ca.ntro.test.introspector.classes;

import ca.ntro.test.introspector.interfaces.ChildInterfaceAB;
import ca.ntro.test.introspector.interfaces.ParentInterfaceA;
import ca.ntro.test.introspector.interfaces.ParentInterfaceB;

public abstract class ParentClassAB implements ChildInterfaceAB {
	
	public abstract void abstractMethod(ParentInterfaceA argA, ParentInterfaceB argB);

	public String inheritedMethod(String argA, boolean argB ) {
		return "inheritedMethod";
	}
}
