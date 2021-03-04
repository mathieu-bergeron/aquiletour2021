package ca.ntro.jdk.test;

import org.junit.BeforeClass;

import ca.ntro.jdk.NtroJdk;
import ca.ntro.test.json.JsonTests;

public class JsonTestsJdk extends JsonTests {

	@BeforeClass
	public static void initializeNtro() {
		NtroJdk.defaultInitializationTask().execute();
	}

}
