package ca.ntro.test.introspector;

import org.junit.Test;

import ca.ntro.core.Ntro;
import ca.ntro.core.introspection.ClassSignature;
import ca.ntro.test.introspector.classes.ChildClassAB;
import ca.ntro.test.introspector.classes.ParentClassAB;
import ca.ntro.test.introspector.interfaces.ChildInterfaceAB;
import ca.ntro.test.introspector.interfaces.ParentInterfaceA;
import ca.ntro.test.introspector.interfaces.ParentInterfaceB;

import static ca.ntro.assertions.Factory.thatObject;

public class IntrospectorTests {
	
	@Test
	public void testDoesImplement() {

		ChildClassAB childClassAB = new ChildClassAB();
		
		/*
		for(ClassSignature _interface : Ntro.introspector().classSignature(childClassAB).allInterfaces()) {
			System.out.println(_interface);
		}

		for(ClassSignature superClass : Ntro.introspector().classSignature(childClassAB).allSuperclasses()) {
			System.out.println(superClass);
		}*/
		
		/*
		Ntro.verify(thatObject(childClassAB).doesImplement(ParentInterfaceA.class)
			 .and().thatObject(childClassAB).doesImplement(ParentInterfaceB.class));
			 */

		Ntro.verify(thatObject(childClassAB).doesImplement(ParentInterfaceA.class));
		Ntro.verify(thatObject(childClassAB).doesImplement(ParentInterfaceB.class));

		Ntro.verify(thatObject(childClassAB).doesImplement(ChildInterfaceAB.class));
		Ntro.verify(thatObject(childClassAB).doesExtend(ParentClassAB.class));
		
		Ntro.verify(thatObject(childClassAB).isInstanceOf(ParentClassAB.class));
	}

}
