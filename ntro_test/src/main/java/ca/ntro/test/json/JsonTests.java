package ca.ntro.test.json;

import org.junit.Test;

import ca.ntro.core.Ntro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ca.ntro.assertions.Factory.thatObject;
import static ca.ntro.assertions.Factory.thatList;
import static ca.ntro.assertions.Factory.that;

public class JsonTests {

	public static void registerSerializableClasses() {
		Ntro.jsonService().registerSerializableClass(ListItemA.class);
		Ntro.jsonService().registerSerializableClass(ListItemB.class);
		Ntro.jsonService().registerSerializableClass(LinkedListNode.class);

		Ntro.jsonService().registerSerializableClass(UsersModel.class);
		Ntro.jsonService().registerSerializableClass(ObservableUserMap.class);
		Ntro.jsonService().registerSerializableClass(Student.class);
		Ntro.jsonService().registerSerializableClass(Teacher.class);

		Ntro.jsonService().registerSerializableClass(DashboardModel.class);
		Ntro.jsonService().registerSerializableClass(ObservableCourseList.class);
		Ntro.jsonService().registerSerializableClass(CourseSummary.class);
	}

	@Test
	public void testDashboardModel() {
		
		String jsonString = "{\"courses\":{\"_C\":\"ObservableCourseList\",\"value\":[{\"numberOfAppointments\":2,\"_C\":\"CourseSummary\",\"isQueueOpen\":true,\"myAppointment\":null,\"title\":\"4F5\",\"courseId\":\"4F5\"},{\"numberOfAppointments\":5,\"_C\":\"CourseSummary\",\"isQueueOpen\":true,\"myAppointment\":null,\"title\":\"3C6\",\"courseId\":\"3C6\"}]},\"_C\":\"DashboardModel\"}";
		
		DashboardModel dashboardModel = Ntro.jsonService().fromString(DashboardModel.class, jsonString);

		Ntro.verify(that(dashboardModel.getCourses().getValue().get(0).getCourseId()).isEqualTo("4F5"));
		
		String jsonString2 = Ntro.jsonService().toString(dashboardModel);

		System.out.println(jsonString2);
	}

	@Test
	public void testUsersModel() {
		
		String jsonString = "{\"_C\":\"UsersModel\",\"users\":{\"_C\":\"ObservableUserMap\",\"value\":{\"bob\":{\"userPassword\":\"bobPassword\",\"_C\":\"Student\",\"surname\":\"Bérancourt\",\"authToken\":\"bobToken\",\"name\":\"Bob\",\"registrationId\":\"1234567\",\"userEmail\":\"bob.berancourt@test.ca\",\"id\":\"bob\"},\"alice\":{\"userPassword\":\"alicePassword\",\"_C\":\"Teacher\",\"surname\":\"Awama\",\"authToken\":\"aliceToken\",\"name\":\"Alice\",\"userEmail\":\"alice.awama@test.com\",\"id\":\"alice\"},\"charlie\":{\"userPassword\":\"charliePassword\",\"_C\":\"Teacher\",\"surname\":\"Ngo\",\"authToken\":\"charlieToken\",\"name\":\"Charlie\",\"userEmail\":\"charlie.ngo@test.org\",\"id\":\"charlie\"}}}}";
		
		UsersModel usersModel = Ntro.jsonService().fromString(UsersModel.class, jsonString);
		
		Ntro.verify(that(usersModel.getUsers().getValue().get("alice").getName()).isEqualTo("Alice"));
		
		String jsonString2 = Ntro.jsonService().toString(usersModel);

		System.out.println(jsonString2);
	}

	@Test
	public void testCycleRoundTrip() {
		LinkedListNode a = new LinkedListNode("A");
		LinkedListNode b = new LinkedListNode("B");

		a.setNext(b);
		b.setNext(a);

		String jsonString = Ntro.jsonService().toString(a);

		LinkedListNode deserializedA = Ntro.jsonService().fromString(LinkedListNode.class, jsonString);
		
		Ntro.verify(that(deserializedA.getNext().getNext()).isEqualTo(deserializedA));
	}

	@Test
	public void testCycleDeserialization() {

		//String jsonString = "{\"next\":{\"next\":{\"_R\":\"\"},\"nextList\":[],\"_C\":\"LinkedListNode\",\"value\":\"B\",\"nextMap\":{}},\"nextList\":[],\"_C\":\"LinkedListNode\",\"value\":\"A\",\"nextMap\":{}}"; 
		String jsonString = "{\"next\":{\"next\":{\"_R\":\"\"},\"_C\":\"LinkedListNode\",\"value\":\"B\"},\"_C\":\"LinkedListNode\",\"value\":\"A\"}"; 

		LinkedListNode deserializedA = Ntro.jsonService().fromString(LinkedListNode.class, jsonString);
		
		Ntro.verify(that(deserializedA.getNext().getNext()).isEqualTo(deserializedA));
	}

	@Test
	public void testUserDefinedObjectRoundTrip() {
		List<ListItem> javaList = new ArrayList<>();
		javaList.add(new ListItemA());
		javaList.add(new ListItemB());
		
		String jsonList = Ntro.jsonService().toString(javaList);

		@SuppressWarnings("unchecked")
		List<ListItem> javaDeserializedList = Ntro.jsonService().fromString(List.class, jsonList);
		
		Ntro.verify(that(javaList).isEqualTo(javaDeserializedList));
	}

	@Test
	public void testUserDefinedObjectDeserialization() {
		
		String jsonString = "[{\"propA\":0,\"_C\":\"ListItemA\"},{\"propB\":1,\"_C\":\"ListItemB\"}]";
		
		@SuppressWarnings("unchecked")
		List<ListItem> javaList = Ntro.jsonService().fromString(List.class, jsonString);
		
		Ntro.verify(that(((ListItemA)javaList.get(0)).getPropA()).isEqualTo(0));
		Ntro.verify(that(((ListItemB)javaList.get(1)).getPropB()).isEqualTo(1));
	}

	@Test
	public void testSimpleRoundtrip() {
		simpleStringRoundtrip();
		simpleIntegerRoundtrip();

		// JSWEET: FIXME: character encoding error
		//simpleCharRoundtrip();

		simpleLongRoundtrip();
		simpleFloatRoundtrip();
		simpleListRoundtrip();
		simpleMapRoundtrip();
	}

	private void simpleMapRoundtrip() {
		// FIXME??: only works for Double since we
		//          do not have parameter types
		Map<String, Double> javaMap = new HashMap<>();
		javaMap.put("a", 1.0);
		javaMap.put("b", 2.0);
		javaMap.put("c", 3.0);

		String jsonString = Ntro.jsonService().toString(javaMap);

		Map<String, Object> deserializedJavaMap = Ntro.jsonService().fromString(Map.class, jsonString);
		
		Ntro.verify(that(deserializedJavaMap).isEqualTo(javaMap));
	}

	private void simpleListRoundtrip() {
		// FIXME??: only works for Double since we
		//          do not have parameter types
		List<Double> javaList = new ArrayList<>();
		javaList.add(1.0);
		javaList.add(2.0);
		javaList.add(3.0);

		String jsonString = Ntro.jsonService().toString(javaList);

		List<Object> deserializedJavaList = Ntro.jsonService().fromString(List.class, jsonString);
		
		Ntro.verify(that(deserializedJavaList).isEqualTo(javaList));
	}

	private void simpleFloatRoundtrip() {
		float javaFloat = 1.13f;

		String jsonString = Ntro.jsonService().toString(javaFloat);

		float deserializedJavaFloat = Ntro.jsonService().fromString(Float.class, jsonString);
		
		Ntro.verify(that(deserializedJavaFloat).isEqualTo(javaFloat));
	}

	private void simpleLongRoundtrip() {
		long javaLong = 12232L;

		String jsonString = Ntro.jsonService().toString(javaLong);

		long deserializedJavaLong = Ntro.jsonService().fromString(Long.class, jsonString);
		
		Ntro.verify(that(deserializedJavaLong).isEqualTo(javaLong));
	}

	private void simpleCharRoundtrip() {
		char javaChar = 'c';

		String jsonString = Ntro.jsonService().toString(javaChar);

		char deserializedJavaChar = Ntro.jsonService().fromString(Character.class, jsonString);
		
		Ntro.verify(that(deserializedJavaChar).isEqualTo(javaChar));
	}

	private void simpleIntegerRoundtrip() {
		int javaInt = 12;

		String jsonString = Ntro.jsonService().toString(javaInt);

		int deserializedJavaInt = Ntro.jsonService().fromString(Integer.class, jsonString);
		
		Ntro.verify(that(deserializedJavaInt).isEqualTo(javaInt));
	}

	private void simpleStringRoundtrip() {
		String javaString = "asdfsdf";

		String jsonString = Ntro.jsonService().toString(javaString);

		String deserializedJavaString = Ntro.jsonService().fromString(String.class, jsonString);
		
		Ntro.verify(that(deserializedJavaString).isEqualTo(javaString));
	}

	@Test
	public void testSimpleDeserialization() {
		simpleDoubleDeserialization();
		simpleIntegerDeserialization();
		simpleBooleanDeserialization();
		simpleStringDeserialization();
	}

	private void simpleStringDeserialization() {
		String initialJavaString = "asdfsdf";
		String jsonString = "\"" + initialJavaString + "\"";

		String javaString = Ntro.jsonService().fromString(String.class, jsonString);

		Ntro.verify(that(initialJavaString).isEqualTo(javaString));
	}

	private void simpleBooleanDeserialization() {
		String jsonBoolean = "true";

		boolean javaBoolean = Ntro.jsonService().fromString(Boolean.class, jsonBoolean);

		Ntro.verify(that(javaBoolean).isEqualTo(true));
	}

	private void simpleIntegerDeserialization() {
		String jsonInteger = "10";

		int javaInteger = Ntro.jsonService().fromString(Integer.class, jsonInteger);

		Ntro.verify(that(Integer.valueOf(jsonInteger)).isEqualTo(javaInteger));
	}

	private void simpleDoubleDeserialization() {
		String jsonDouble = "10.5";

		double javaDouble = Ntro.jsonService().fromString(Double.class, jsonDouble);

		Ntro.verify(that(Double.valueOf(jsonDouble)).isEqualTo(javaDouble));
	}

	@Test
	public void testCycleSerialization() {
		LinkedListNode a = new LinkedListNode("A");
		LinkedListNode b = new LinkedListNode("B");

		a.setNext(b);
		b.setNext(a);

		String jsonA = Ntro.jsonService().toString(a);
		
		System.out.println(jsonA);
	}


	@Test
	public void testUserDefinedObjectSerialization() {
		
		List<ListItem> javaList = new ArrayList<>();
		javaList.add(new ListItemA());
		javaList.add(new ListItemB());
		
		String jsonList = Ntro.jsonService().toString(javaList);

		System.out.println(jsonList);

		// FIXME: _C appears first in JSweet
		//        better test the deserialization when available
		// Ntro.verify(that(jsonList).is("[{\"propA\":0,\"_C\":\"ListItemA\"},{\"propB\":1,\"_C\":\"ListItemB\"}]"));
	}
	
	@Test
	public void testSimpleSerialization() {
		simpleStringSerialization();
		simpleBooleanSerialization();
		simpleIntegerSerialization();
		simpleDoubleSerialization();
		simpleListSerialization();
		simpleMapSerialization();
	}

	private void simpleMapSerialization() {
		Map<String, Integer> javaMap1 = new HashMap<>();
		javaMap1.put("a", 1);
		javaMap1.put("b", 2);
		javaMap1.put("c", 3);
		
		String jsonMap1 = Ntro.jsonService().toString(javaMap1);
		
		Ntro.verify(that(jsonMap1).isEqualTo("{\"a\":1,\"b\":2,\"c\":3}"));

		Map<String, Object> javaMap2 = new HashMap<>();
		javaMap2.put("a", 1);
		javaMap2.put("b", false);
		javaMap2.put("c", 10.3);
		
		String jsonMap2 = Ntro.jsonService().toString(javaMap2);
		
		Ntro.verify(that(jsonMap2).isEqualTo("{\"a\":1,\"b\":false,\"c\":10.3}"));
	}

	private void simpleListSerialization() {
		List<String> javaList1 = new ArrayList<>();
		javaList1.add("a");
		javaList1.add("b");
		javaList1.add("c");
		
		String jsonList1 = Ntro.jsonService().toString(javaList1);
		
		Ntro.verify(that(jsonList1).isEqualTo("[\"a\",\"b\",\"c\"]"));

		List<Object> javaList2 = new ArrayList<>();
		javaList2.add("a");
		javaList2.add(10);
		javaList2.add(false);
		javaList2.add(10.45);
		
		String jsonList2 = Ntro.jsonService().toString(javaList2);
		
		Ntro.verify(that(jsonList2).isEqualTo("[\"a\",10,false,10.45]"));
	}

	private void simpleDoubleSerialization() {
		double javaDouble = 19.0;

		String jsonDouble = Ntro.jsonService().toString(javaDouble);

		Ntro.verify(that(Double.valueOf(jsonDouble)).isEqualTo(javaDouble));
	}

	private void simpleIntegerSerialization() {
		int javaInt = 10;

		String jsonInt = Ntro.jsonService().toString(javaInt);

		Ntro.verify(that(Integer.valueOf(jsonInt)).isEqualTo(javaInt));
	}

	private void simpleBooleanSerialization() {
		boolean javaBoolean = false;

		String jsonBoolean = Ntro.jsonService().toString(javaBoolean);

		Ntro.verify(that(String.valueOf(javaBoolean)).isEqualTo(jsonBoolean));
	}

	private void simpleStringSerialization() {
		String javaString = "test";

		String jsonString = Ntro.jsonService().toString(javaString);
		
		Ntro.verify(that(jsonString.equals("\"" + javaString + "\"")).isTrue());
	}

}
