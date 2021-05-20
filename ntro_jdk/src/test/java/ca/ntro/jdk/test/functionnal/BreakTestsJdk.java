package ca.ntro.jdk.test.functionnal;

import org.junit.BeforeClass;

import ca.ntro.jdk.NtroJdk;
import ca.ntro.test.functionnal.BreakTests;

public class BreakTestsJdk extends BreakTests {

	@BeforeClass
	public static void initializeNtro() {
		NtroJdk.defaultInitializationTask().execute();
	}

}
