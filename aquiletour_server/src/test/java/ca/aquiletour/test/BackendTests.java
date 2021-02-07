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
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ca.aquiletour.core.pages.queue.QueueModel;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.services.stores.LocalStore;
import ca.ntro.jdk.web.NtroWebserver;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;

import static ca.aquiletour.test.Constants.*;

@RunWith(JUnit4.class)
public class BackendTests {
	
	private static HttpClient client;
	private static final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
	
	@BeforeClass
	public static void initializeNtro(){
		NtroWebserver.defaultInitializationTask()
		             .execute();
	}
	
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
		
		// XXX: assuming that modelLoader is actually Sync
		//      will not work in JSweet
		ModelLoader modelLoader = LocalStore.getLoader(QueueModel.class, "TODO");
		modelLoader.execute();
		
		QueueModel queue = (QueueModel) modelLoader.getModel();

		return queue.getAppointments().getValue().size();
	}


	@Test
	public void loginTest() {

	}

	@After
	public void tearDown() throws Exception {
        client.stop();
	}

}
