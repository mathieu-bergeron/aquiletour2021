package ca.ntro.jsweet.test.introspector;

import ca.ntro.core.Ntro;
import ca.ntro.jsweet.test.introspector.classes.ChildClassAB;
import ca.ntro.jsweet.test.introspector.classes.ParentClassAB;
import ca.ntro.jsweet.test.introspector.interfaces.ChildInterfaceAB;
import ca.ntro.jsweet.test.introspector.interfaces.ParentInterfaceA;
import ca.ntro.jsweet.test.introspector.interfaces.ParentInterfaceB;

public class IntrospectorTests {
	
	public void testDoesImplement() {

		ChildClassAB childClassAB = new ChildClassAB();

		Ntro.verify().thatObject(childClassAB).doesImplement(ParentInterfaceA.class);
		Ntro.verify().thatObject(childClassAB).doesImplement(ParentInterfaceB.class);
		Ntro.verify().thatObject(childClassAB).doesImplement(ChildInterfaceAB.class);
		Ntro.verify().thatObject(childClassAB).doesExtend(ParentClassAB.class);
	}

}
