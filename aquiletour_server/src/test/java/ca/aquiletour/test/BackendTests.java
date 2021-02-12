package ca.aquiletour.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;

import static ca.aquiletour.test.Constants.*;

public class BackendTests {
	
	private HttpClient client;
	private static final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
	
	@Before
	public void setUp() throws Exception {
		TestUtils.installTestData();
	    
        client = new HttpClient();
        client.start();
	}

	@Test
	public void makeAppointmentTest() throws InterruptedException, ExecutionException, TimeoutException, FileNotFoundException {
		
		int startingSize = numberOfAppointments();
		
        ContentResponse res = client.GET(HOST + "/queue?makeAppointment");
        
        int newSize = numberOfAppointments();
        
		assertTrue(newSize == startingSize + 1);
	}

	private int numberOfAppointments() throws FileNotFoundException {
		FileReader reader = new FileReader(new File("__data__/QueueModel/TODO.json"));

		Map<String, Object> queue = gson.fromJson(reader, Map.class);
		Map<String, Object> appointments = (Map<String,Object>) queue.get("appointments");
		Map<String, Object> value = (Map<String,Object>) appointments.get("value");
		int startingSize = value.size();

		/*
		QueueModel queue = gson.fromJson(reader,QueueModel.class);
		int startingSize = queue.getAppointments().getValue().size();
		*/

		return startingSize;
	}


	@Test
	public void loginTest() {

	}

	@After
	public void tearDown() throws Exception {
        client.stop();
	}

}
