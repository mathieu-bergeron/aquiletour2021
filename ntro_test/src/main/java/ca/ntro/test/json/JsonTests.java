package ca.ntro.test.json;

import org.junit.Test;

import ca.ntro.core.Ntro;

import static ca.ntro.assertions.Factory.thatObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ca.ntro.assertions.Factory.thatList;
import static ca.ntro.assertions.Factory.that;

public class JsonTests {

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

	}

	@Test
	public void testSimpleRoundTrip() {

	}

	@Test
	public void testSimpleDeserialization() {

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
