package ca.ntro.test.introspector;

import org.junit.Test;

import ca.ntro.core.Ntro;
import ca.ntro.core.introspection.NtroClass;
import ca.ntro.core.introspection.NtroMethod;
import ca.ntro.test.introspector.classes.ChildClassAB;
import ca.ntro.test.introspector.classes.ParentClassAB;
import ca.ntro.test.introspector.interfaces.ChildInterfaceAB;
import ca.ntro.test.introspector.interfaces.ParentInterfaceA;
import ca.ntro.test.introspector.interfaces.ParentInterfaceB;

import static ca.ntro.assertions.Factory.thatObject;
import static ca.ntro.assertions.Factory.thatList;
import static ca.ntro.assertions.Factory.that;

import java.util.List;

public class IntrospectorTests {
	
	@Test
	public void testDoesImplement() {

		ChildClassAB childClassAB = new ChildClassAB();
		
		Ntro.verify(thatObject(childClassAB).doesImplement(ParentInterfaceA.class)
			 .and().thatObject(childClassAB).doesImplement(ParentInterfaceB.class));

		Ntro.verify(thatObject(childClassAB).doesImplement(ParentInterfaceA.class));
		Ntro.verify(thatObject(childClassAB).doesImplement(ParentInterfaceB.class));

		Ntro.verify(thatObject(childClassAB).doesImplement(ChildInterfaceAB.class));
		Ntro.verify(thatObject(childClassAB).doesExtend(ParentClassAB.class));
		
		Ntro.verify(thatObject(childClassAB).isInstanceOf(ParentClassAB.class));

		/*
		ClassSignature classSignatureAB = Ntro.introspector().classSignature(childClassAB);
		Set<ClassSignature> superclasses =  classSignatureAB.allSuperclasses();
		Set<ClassSignature> interfaces =  classSignatureAB.allInterfaces();
		
		superclasses.forEach(sc -> System.out.println(sc.simpleName()));
		interfaces.forEach(i -> System.out.println(i.simpleName()));
		*/
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
