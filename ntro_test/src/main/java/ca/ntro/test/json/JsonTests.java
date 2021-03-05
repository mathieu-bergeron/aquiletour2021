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
	}

	@Test
	public void testCycleRoundTrip() {

	}

	@Test
	public void testCycleDeserialization() {
	}

	@Test
	public void testUserDefinedObjectRoundTrip() {
	}

	@Test
	public void testUserDefinedObjectDeserialization() {
		
		String jsonString = "[{\"propA\":0,\"_C\":\"ListItemA\"},{\"propB\":1,\"_C\":\"ListItemB\"}]";
		
		@SuppressWarnings("unchecked")
		List<ListItem> javaList = Ntro.jsonService().fromString(List.class, jsonString);
		
		Ntro.verify(that(((ListItemA)javaList.get(0)).getPropA()).is(0));
		Ntro.verify(that(((ListItemB)javaList.get(0)).getPropB()).is(1));
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
		
		Ntro.verify(that(deserializedJavaMap).is(javaMap));
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
		
		Ntro.verify(that(deserializedJavaList).is(javaList));
	}

	private void simpleFloatRoundtrip() {
		float javaFloat = 1.13f;

		String jsonString = Ntro.jsonService().toString(javaFloat);

		float deserializedJavaFloat = Ntro.jsonService().fromString(Float.class, jsonString);
		
		Ntro.verify(that(deserializedJavaFloat).is(javaFloat));
	}

	private void simpleLongRoundtrip() {
		long javaLong = 12232L;

		String jsonString = Ntro.jsonService().toString(javaLong);

		long deserializedJavaLong = Ntro.jsonService().fromString(Long.class, jsonString);
		
		Ntro.verify(that(deserializedJavaLong).is(javaLong));
	}

	private void simpleCharRoundtrip() {
		char javaChar = 'c';

		String jsonString = Ntro.jsonService().toString(javaChar);

		char deserializedJavaChar = Ntro.jsonService().fromString(Character.class, jsonString);
		
		Ntro.verify(that(deserializedJavaChar).is(javaChar));
	}

	private void simpleIntegerRoundtrip() {
		int javaInt = 12;

		String jsonString = Ntro.jsonService().toString(javaInt);

		int deserializedJavaInt = Ntro.jsonService().fromString(Integer.class, jsonString);
		
		Ntro.verify(that(deserializedJavaInt).is(javaInt));
	}

	private void simpleStringRoundtrip() {
		String javaString = "asdfsdf";

		String jsonString = Ntro.jsonService().toString(javaString);

		String deserializedJavaString = Ntro.jsonService().fromString(String.class, jsonString);
		
		Ntro.verify(that(deserializedJavaString).is(javaString));
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

		Ntro.verify(that(initialJavaString).is(javaString));
	}

	private void simpleBooleanDeserialization() {
		String jsonBoolean = "true";

		boolean javaBoolean = Ntro.jsonService().fromString(Boolean.class, jsonBoolean);

		Ntro.verify(that(javaBoolean).is(true));
	}

	private void simpleIntegerDeserialization() {
		String jsonInteger = "10";

		int javaInteger = Ntro.jsonService().fromString(Integer.class, jsonInteger);

		Ntro.verify(that(Integer.valueOf(jsonInteger)).is(javaInteger));
	}

	private void simpleDoubleDeserialization() {
		String jsonDouble = "10.5";

		double javaDouble = Ntro.jsonService().fromString(Double.class, jsonDouble);

		Ntro.verify(that(Double.valueOf(jsonDouble)).is(javaDouble));
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
		
		Ntro.verify(that(jsonMap1).is("{\"a\":1,\"b\":2,\"c\":3}"));

		Map<String, Object> javaMap2 = new HashMap<>();
		javaMap2.put("a", 1);
		javaMap2.put("b", false);
		javaMap2.put("c", 10.3);
		
		String jsonMap2 = Ntro.jsonService().toString(javaMap2);
		
		Ntro.verify(that(jsonMap2).is("{\"a\":1,\"b\":false,\"c\":10.3}"));
	}

	private void simpleListSerialization() {
		List<String> javaList1 = new ArrayList<>();
		javaList1.add("a");
		javaList1.add("b");
		javaList1.add("c");
		
		String jsonList1 = Ntro.jsonService().toString(javaList1);
		
		Ntro.verify(that(jsonList1).is("[\"a\",\"b\",\"c\"]"));

		List<Object> javaList2 = new ArrayList<>();
		javaList2.add("a");
		javaList2.add(10);
		javaList2.add(false);
		javaList2.add(10.45);
		
		String jsonList2 = Ntro.jsonService().toString(javaList2);
		
		Ntro.verify(that(jsonList2).is("[\"a\",10,false,10.45]"));
	}

	private void simpleDoubleSerialization() {
		double javaDouble = 19.0;

		String jsonDouble = Ntro.jsonService().toString(javaDouble);

		Ntro.verify(that(Double.valueOf(jsonDouble)).is(javaDouble));
	}

	private void simpleIntegerSerialization() {
		int javaInt = 10;

		String jsonInt = Ntro.jsonService().toString(javaInt);

		Ntro.verify(that(Integer.valueOf(jsonInt)).is(javaInt));
	}

	private void simpleBooleanSerialization() {
		boolean javaBoolean = false;

		String jsonBoolean = Ntro.jsonService().toString(javaBoolean);

		Ntro.verify(that(String.valueOf(javaBoolean)).is(jsonBoolean));
	}

	private void simpleStringSerialization() {
		String javaString = "test";

		String jsonString = Ntro.jsonService().toString(javaString);
		
		Ntro.verify(that(jsonString.equals("\"" + javaString + "\"")).isTrue());
	}

}
