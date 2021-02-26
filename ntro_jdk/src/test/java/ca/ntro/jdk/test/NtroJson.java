package ca.ntro.jdk.test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import ca.ntro.core.Ntro;
import ca.ntro.core.json.JsonObject;
import ca.ntro.core.json.JsonParser;
import ca.ntro.jdk.NtroJdk;
import ca.ntro.jdk.test.json.LinkedListNode;
import ca.ntro.jdk.test.json.ListItem;
import ca.ntro.jdk.test.json.ListItemA;
import ca.ntro.jdk.test.json.ListItemB;
import ca.ntro.jdk.test.json.MyList;

public class NtroJson {

	private static File jsonDir = new File("__json__");

	@BeforeClass
	public static void initializeNtro() {
		NtroJdk.defaultInitializationTask().execute();
		
		Ntro.introspector().registerSerializableClass(LinkedListNode.class);
		Ntro.introspector().registerSerializableClass(MyList.class);
		Ntro.introspector().registerSerializableClass(ListItemA.class);
		Ntro.introspector().registerSerializableClass(ListItemB.class);
	}

	@BeforeClass
	public static void initializeTaskDir() {
		if(jsonDir.exists()) {
			Utils.deleteDir(jsonDir);
		}
		
		jsonDir.mkdirs();
	}
	
	private void toFile(JsonObject jsonObject, String name) throws IOException {
		FileOutputStream out = new FileOutputStream(new File(jsonDir, name + ".json"));
		out.write(JsonParser.toString(jsonObject).getBytes());
		out.close();
	}

	private JsonObject fromFile(String name) throws IOException {
		FileInputStream in = new FileInputStream(new File(jsonDir, name + ".json"));
		JsonObject jsonObject = JsonParser.fromStream(in);
		in.close();
		return jsonObject;
	}

	@Before
	public void setUp() {
	}

	@Test
	public void jsonCycle() throws IOException {

		LinkedListNode a = new LinkedListNode("A");
		LinkedListNode b = new LinkedListNode("B");
		LinkedListNode c = new LinkedListNode("C");
		LinkedListNode d = new LinkedListNode("D");
		
		//a.setNext(a);

		a.setNext(b);
		b.setNext(a);
		
		/*
		a.setNext(b);
		b.setNext(c);
		c.setNext(d);
		d.setNext(c);
		
		c.getNextMap().put("d", d);
		d.getNextMap().put("b", b);
		
		b.getNextList().add(b);
		b.getNextList().add(c);
		b.getNextList().add(d);
		
		*/
		
		JsonObject jsonObject = a.toJsonObject();
		
		toFile(jsonObject, "cycle");

		JsonObject parsedObject = fromFile("cycle");

		LinkedListNode parsedA = new LinkedListNode();
		
		assertThrows(RuntimeException.class, new ThrowingRunnable() {
			
			@Override
			public void run() throws Throwable {
				parsedA.loadFromJsonObject(parsedObject);
			}
		});

	}

	@Test
	public void jsonTypes() throws IOException {
		
		MyList myList = new MyList();
		List<ListItem> list = myList.getList();
		
		list.add(new ListItemA());
		list.add(new ListItemB());

		assertTrue(list.get(0) instanceof ListItemA);
		assertTrue(list.get(1) instanceof ListItemB);
		
		JsonObject jsonObject = myList.toJsonObject();
		
		toFile(jsonObject, "jsonTypes");
		
		JsonObject parsedObject = fromFile("jsonTypes");
		
		MyList parsedList = new MyList();
		parsedList.loadFromJsonObject(parsedObject);

		assertTrue(myList.equals(parsedList));
	}

	@After
	public void tearDown() {
	}

}
