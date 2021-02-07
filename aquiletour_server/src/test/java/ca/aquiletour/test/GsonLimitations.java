package ca.aquiletour.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

public class GsonLimitations {

	private static final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

	@Before
	public void setUp() throws Exception {
		TestUtils.installTestData();
	}

	private interface ListItem {
		
	}
	
	private class ListItemA implements ListItem {
		
		public int propA = 10;
		
	}

	private class ListItemB implements ListItem {

		public int propB = 10;

	}

	@Test
	public void gsonLimitations() throws FileNotFoundException, IOException {

		List<ListItem> list = new ArrayList<>();
		list.add(new ListItemA());
		list.add(new ListItemB());

		assertTrue(list.get(0) instanceof ListItemA);
		assertTrue(list.get(1) instanceof ListItemB);
		
		new FileOutputStream(new File("gsonLimitations.json")).write(gson.toJson(list).getBytes());
		
		List<ListItem> parsedList = gson.fromJson(new FileReader(new File("gsonLimitations.json")), List.class);
		
		// XXX: in JSON, there is not type information 
		//      for objects in the list
		assertFalse(parsedList.get(0) instanceof ListItemA);
		assertFalse(parsedList.get(1) instanceof ListItemB);

		// XXX: Gson deserializes to LinkedTreeMap and throws ClassCastException
		//      when we try to access the value as its origin class
		assertTrue(((Object) parsedList.get(0)) instanceof LinkedTreeMap);
		assertTrue(((Object) parsedList.get(1)) instanceof LinkedTreeMap);
		
	}
}
