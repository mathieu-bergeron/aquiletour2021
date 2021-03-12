package ca.ntro.test.introspector;

import org.junit.Test;

import ca.ntro.core.introspection.NtroClass;
import ca.ntro.core.introspection.NtroMethod;
import ca.ntro.core.models.NtroModelValue;
import ca.ntro.services.Ntro;
import ca.ntro.test.introspector.classes.ChildClassAB;
import ca.ntro.test.introspector.classes.GrandParentClassAB;
import ca.ntro.test.introspector.classes.ParentClassAB;
import ca.ntro.test.introspector.interfaces.ChildInterfaceAB;
import ca.ntro.test.introspector.interfaces.GrandParentInterfaceA;
import ca.ntro.test.introspector.interfaces.ParentInterfaceA;
import ca.ntro.test.introspector.interfaces.ParentInterfaceB;
import ca.ntro.test.introspector.ntro_bug.MyStoredList;

import static ca.ntro.assertions.Factory.thatObject;
import static ca.ntro.assertions.Factory.thatList;
import static ca.ntro.assertions.Factory.that;

import java.util.List;
import java.util.Set;

public class IntrospectorTests {
	
	@Test
	public void testDoesImplement() {

		ChildClassAB childClassAB = new ChildClassAB();
		MyStoredList myStoredList = new MyStoredList();
		
		Ntro.verify(thatObject(childClassAB).doesImplement(ParentInterfaceA.class)
			 .and().thatObject(childClassAB).doesImplement(ParentInterfaceB.class));

		Ntro.verify(thatObject(childClassAB).doesImplement(ParentInterfaceA.class));
		Ntro.verify(thatObject(childClassAB).doesImplement(ParentInterfaceB.class));

		Ntro.verify(thatObject(childClassAB).doesImplement(ChildInterfaceAB.class));

		Ntro.verify(thatObject(childClassAB).doesImplement(GrandParentInterfaceA.class));

		Ntro.verify(thatObject(childClassAB).doesExtend(ParentClassAB.class));
		Ntro.verify(thatObject(childClassAB).doesExtend(GrandParentClassAB.class));
		
		Ntro.verify(thatObject(childClassAB).isInstanceOf(ParentClassAB.class));
		
		Ntro.verify(thatObject(myStoredList).doesImplement(NtroModelValue.class));

		NtroClass ntroClass = Ntro.introspector().ntroClassFromObject(myStoredList);
		Set<NtroClass> superclasses =  ntroClass.allSuperclasses();
		Set<NtroClass> interfaces =  ntroClass.allInterfaces();

		System.out.println("superclasses");
		superclasses.forEach(sc -> System.out.println(sc.simpleName()));

		System.out.println("\ninterfaces");
		interfaces.forEach(i -> System.out.println(i.simpleName()));
		System.out.println("\n");
	}

	@Test
	public void testMethods() {

		ChildClassAB childClassAB = new ChildClassAB();
		
		NtroClass classSignatureAB = Ntro.introspector().ntroClassFromObject(childClassAB);

		List<NtroMethod> methods = classSignatureAB.userDefinedMethods();
		//methods.forEach(ms -> System.out.println(ms.name()));
		
		Ntro.verify(thatList(methods).contains(m -> ((NtroMethod)m).name().equals("abstractMethod")));
		Ntro.verify(thatList(methods).contains(m -> ((NtroMethod)m).name().equals("interfaceMethodA")));
		Ntro.verify(thatList(methods).contains(m -> ((NtroMethod)m).name().equals("inheritedMethod")));
	}

}
