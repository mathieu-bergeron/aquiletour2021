package ca.ntro.jdk.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
		
		JsonParser.registerClass(LinkedListNode.class);
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
	public void jsonCycle() {

		LinkedListNode a = new LinkedListNode();
		LinkedListNode b = new LinkedListNode();
		
		a.setNext(b);
		b.setNext(a);
		
		JsonObject object = a.toJsonObject();
	}

	@Test
	public void jsonTypes() throws IOException {
		
		MyList myList = new MyList();
		List<ListItem> list = myList.getValue();
		
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
