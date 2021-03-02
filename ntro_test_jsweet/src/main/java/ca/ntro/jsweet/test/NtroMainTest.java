package ca.ntro.jsweet.test;

import java.util.HashMap;
import java.util.Map;

import ca.ntro.core.Ntro;
import ca.ntro.core.json.JsonObject;
import ca.ntro.core.json.JsonParser;
import ca.ntro.core.system.trace.T;
import ca.ntro.core.tasks.NtroTaskSync;
import ca.ntro.test.introspector.IntrospectorTests;
import ca.ntro.test.json.LinkedListNode;

import static ca.ntro.assertions.Factory.that;

public class NtroMainTest extends NtroTaskSync {

	@Override
	protected void runTask() {
		T.call(this);

		IntrospectorTests introspectorTests = new IntrospectorTests();
		introspectorTests.testDoesImplement();
		System.out.println("testDoesImplement finished");

		introspectorTests.testMethods();
		System.out.println("testMethods finished");
	}
	
	
	private void tmp() {
		
		LinkedListNode a = new LinkedListNode("A");
		LinkedListNode b = new LinkedListNode("B");
		LinkedListNode c = new LinkedListNode("C");
		LinkedListNode d = new LinkedListNode("D");
		
		Map<LinkedListNode, Integer> map = new HashMap<>();
		map.put(a, 10);

		Map<Integer, LinkedListNode> otherMap = new HashMap<>();
		otherMap.put(2, a);
		
		int x = map.get(a);

		Ntro.verify(that(a).isNot(null));

		for(Map.Entry<LinkedListNode, Integer> entry : map.entrySet()) {
			System.out.println(entry.getKey());
		}
		
		for(int value : map.values()) {
			System.out.println(value);
		}
		
		/*
		
		//a.setNext(a);

		//a.setNext(b);
		//b.setNext(a);
		
		a.setNext(b);
		b.setNext(c);
		c.setNext(d);
		d.setNext(c);
		
		c.getNextMap().put("d", d);
		d.getNextMap().put("b", b);
		
		b.getNextList().add(a);
		b.getNextList().add(b);
		b.getNextList().add(c);
		b.getNextList().add(d);
		
		*/
		
		JsonObject jsonObject = a.toJsonObject();
		
		System.out.println(jsonObject);
		
		System.out.println(JsonParser.toString(jsonObject));
		
	}
	

	@Override
	protected void onFailure(Exception e) {
		
	}

}
