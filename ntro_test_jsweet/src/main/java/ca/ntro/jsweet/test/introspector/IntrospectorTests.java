package ca.ntro.jsweet.test.introspector;

import ca.ntro.core.Ntro;
import ca.ntro.core.introspection.ClassSignature;
import ca.ntro.jsweet.test.introspector.classes.ChildClassAB;
import ca.ntro.jsweet.test.introspector.classes.ParentClassAB;
import ca.ntro.jsweet.test.introspector.interfaces.ChildInterfaceAB;
import ca.ntro.jsweet.test.introspector.interfaces.ParentInterfaceA;
import ca.ntro.jsweet.test.introspector.interfaces.ParentInterfaceB;

public class IntrospectorTests {
	
	public void testDoesImplement() {
		
		ChildClassAB childClassAB = new ChildClassAB();
		
		ClassSignature classSignatureAB = Ntro.introspector().getClassSignature(childClassAB);
		
		Ntro.verify().that().isTrue(classSignatureAB.ifImplements(ParentInterfaceA.class));
		Ntro.verify().that().isTrue(classSignatureAB.ifImplements(ParentInterfaceB.class));
		Ntro.verify().that().isTrue(classSignatureAB.ifImplements(ChildInterfaceAB.class));
		Ntro.verify().that().isTrue(classSignatureAB.ifExtends(ParentClassAB.class));

		// FIXME: better
		// Ntro.verify().that(classSignatureAB).extends(ParentClassAB.class);
	}

}
