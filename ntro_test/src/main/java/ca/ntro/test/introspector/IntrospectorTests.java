package ca.ntro.test.introspector;

import org.junit.Test;

import ca.ntro.core.Ntro;
import ca.ntro.core.introspection.ClassSignature;
import ca.ntro.core.introspection.MethodSignature;
import ca.ntro.test.introspector.classes.ChildClassAB;
import ca.ntro.test.introspector.classes.ParentClassAB;
import ca.ntro.test.introspector.interfaces.ChildInterfaceAB;
import ca.ntro.test.introspector.interfaces.ParentInterfaceA;
import ca.ntro.test.introspector.interfaces.ParentInterfaceB;

import static ca.ntro.assertions.Factory.thatObject;
import static ca.ntro.assertions.Factory.thatList;
import static ca.ntro.assertions.Factory.that;

import java.util.ArrayList;
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
		
		ClassSignature classSignatureAB = Ntro.introspector().classSignature(childClassAB);

		List<MethodSignature> methodSignatures = classSignatureAB.userDefinedMethods();
		methodSignatures.forEach(ms -> System.out.println(ms.name()));
		
		List<String> methodNames = new ArrayList<>();
		methodSignatures.forEach(ms -> methodNames.add(ms.name()));
		
		Ntro.verify(thatList(methodSignatures).contains(m -> ((MethodSignature)m).name().equals("abstractMethod")));

		Ntro.verify(thatList(methodSignatures).contains(m -> ((MethodSignature)m).name().equals("interfaceMethodA")));

		Ntro.verify(thatList(methodSignatures).contains(m -> ((MethodSignature)m).name().equals("inheritedMethod")));
		
		List<String> desiredMethodNames = new ArrayList<>();
		desiredMethodNames.add("abstractMethod");
		desiredMethodNames.add("inheritedMethod");
		desiredMethodNames.add("interfaceMethodA");
		
		Ntro.verify(that(methodNames).is(desiredMethodNames));

	}

}
