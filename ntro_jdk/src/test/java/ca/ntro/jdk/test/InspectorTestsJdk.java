package ca.ntro.jdk.test;

import org.junit.BeforeClass;

import ca.ntro.core.Ntro;
import ca.ntro.test.introspector.IntrospectorTests;

public class InspectorTestsJdk extends IntrospectorTests {
	
	@BeforeClass
	public static void initializeNtro() {
		Ntro.zzz_registerAssertService(new AssertServiceJUnit());
	}

}
