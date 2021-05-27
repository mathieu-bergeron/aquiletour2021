package ca.ntro.jdk.test;

import org.junit.BeforeClass;
import org.junit.Ignore;

import ca.ntro.jdk.NtroJdk;
import ca.ntro.test.introspector.IntrospectorTests;

@Ignore
public class IntrospectorTestJdk extends IntrospectorTests {
	
	@BeforeClass
	public static void initializeNtro() {
		NtroJdk.defaultInitializationTask().execute();
	}

}
