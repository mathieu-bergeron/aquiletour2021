package ca.ntro.jsweet.test.introspector;

import ca.ntro.core.Ntro;
import ca.ntro.core.introspection.ClassSignatureImpl;
import ca.ntro.jsweet.test.introspector.classes.ChildClassAB;
import ca.ntro.jsweet.test.introspector.classes.ParentClassAB;
import ca.ntro.jsweet.test.introspector.interfaces.ChildInterfaceAB;
import ca.ntro.jsweet.test.introspector.interfaces.ParentInterfaceA;
import ca.ntro.jsweet.test.introspector.interfaces.ParentInterfaceB;

public class IntrospectorTests {
	
	public void testDoesImplement() {
		
		ChildClassAB childClassAB = new ChildClassAB();
		
		ClassSignatureImpl classSignatureAB = Ntro.introspector().getClassSignature(childClassAB);
		
		classSignatureAB.implementsInterface(ParentInterfaceA.class);
		classSignatureAB.implementsInterface(ParentInterfaceB.class);
		classSignatureAB.implementsInterface(ChildInterfaceAB.class);
		classSignatureAB.extendsClass(ParentClassAB.class);
		
		
	}

}
