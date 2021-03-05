package ca.ntro.jsweet.test;

import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.test.introspector.IntrospectorTests;
import ca.ntro.test.json.JsonTests;

public class NtroMainTest extends NtroTaskSync {

	@Override
	protected void runTask() {
		T.call(this);

		IntrospectorTests introspectorTests = new IntrospectorTests();
		introspectorTests.testDoesImplement();
		System.out.println("testDoesImplement finished");

		introspectorTests.testMethods();
		System.out.println("testMethods finished");
		
		JsonTests.registerSerializableClasses();

		JsonTests jsonTests = new JsonTests();
		jsonTests.testSimpleSerialization();
		System.out.println("testSimpleSerialization finished");

		jsonTests.testUserDefinedObjectSerialization();
		System.out.println("testUserDefinedObjectSerialization finished");

		jsonTests.testCycleSerialization();
		System.out.println("testCycleSerialization finished");

		jsonTests.testSimpleDeserialization();
		System.out.println("testSimpleDeserialization finished");

		jsonTests.testSimpleRoundtrip();
		System.out.println("testSimpleRoundTrip finished");

		jsonTests.testUserDefinedObjectDeserialization();
		System.out.println("testUserDefinedObjectDeserialization finished");

		jsonTests.testUserDefinedObjectRoundTrip();
		System.out.println("testUserDefinedObjectRoundTrip finished");

		jsonTests.testCycleDeserialization();
		System.out.println("testCycleDeserialization finished");

		jsonTests.testCycleRoundTrip();
		System.out.println("testCycleRoundTrip finished");
		
		jsonTests.testUsersModel();
		System.out.println("testUsersModel finished");
	}

	@Override
	protected void onFailure(Exception e) {
		// TODO Auto-generated method stub
		
	}
}
