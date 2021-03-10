package ca.aquiletour.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import ca.aquiletour.core.pages.queue.QueueModel;
import ca.aquiletour.server.backend.AquiletourBackendService;
import ca.ntro.core.models.ModelLoader;
import ca.ntro.core.models.ModelStore;
import ca.ntro.core.services.BackendService;
import ca.ntro.core.services.stores.LocalStore;
import ca.ntro.jdk.models.ModelStoreSync;
import ca.ntro.jdk.services.BackendServiceServer;
import ca.ntro.jdk.services.LocalStoreFiles;
import ca.ntro.jdk.web.NtroWebserver;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.util.HttpCookieStore;
import org.hamcrest.MatcherAssert;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import static ca.aquiletour.test.Constants.*;

@RunWith(JUnit4.class)
public class BackendTests {
	
	private static HttpClient client;
	
	@BeforeClass
	public static void initializeNtro(){
		ModelStore localStore = new LocalStoreFiles();
		

		NtroWebserver.defaultInitializationTask(AquiletourBackendService.class, localStore)
		             .execute();
	}
	
	@Before
	public void setUp() throws Exception {
		TestUtils.installTestData();
	    
        client = new HttpClient();
        client.start();
	}

	@Test
	public void shouldAddAppointment() throws InterruptedException, ExecutionException, TimeoutException, FileNotFoundException, URISyntaxException {

		int startingSize = numberOfAppointments();

		HttpCookieStore cookies = new HttpCookieStore();
		
		cookies.add(new URI(HOST), new HttpCookie("userId","bob"));
		cookies.add(new URI(HOST), new HttpCookie("authToken","bobToken"));
		
		client.setCookieStore(cookies);
		
        client.GET(HOST + "/queue?makeAppointment");
        
        int newSize = numberOfAppointments();
        
		assertTrue(newSize == startingSize + 1);
	}

	private int numberOfAppointments() throws FileNotFoundException {
		
		// XXX: assuming that modelLoader is actually Sync
		//      will not work in JSweet
		ModelLoader modelLoader = LocalStore.getLoader(QueueModel.class, "bobToken", "bob");
		modelLoader.execute();
		
		QueueModel queue = (QueueModel) modelLoader.getModel();

		return queue.getAppointments().getValue().size();
	}


	@Test
	public void loginShouldSucceed() throws URISyntaxException, InterruptedException, ExecutionException, TimeoutException {
		
		HttpCookieStore cookies = new HttpCookieStore();
		
		cookies.add(new URI(HOST), new HttpCookie("userId","bob"));
		cookies.add(new URI(HOST), new HttpCookie("authToken","bobToken"));
		
		client.setCookieStore(cookies);

        ContentResponse res = client.GET(HOST + "/dashboard");
        
        Document document = Jsoup.parse(res.getContentAsString());
        
        assertTrue(document.select(".dashboard-container").size() > 0);
	}

	@Test
	public void loginShouldFail() throws URISyntaxException, InterruptedException, ExecutionException, TimeoutException {
		
		HttpCookieStore cookies = new HttpCookieStore();
		
		cookies.add(new URI(HOST), new HttpCookie("userId","bob"));
		cookies.add(new URI(HOST), new HttpCookie("authToken","wrongToken"));
		
		client.setCookieStore(cookies);

        ContentResponse res = client.GET(HOST + "/dashboard");
        
        Document document = Jsoup.parse(res.getContentAsString());
        
        assertFalse(document.select(".dashboard-container").size() > 0);
	}

	@After
	public void tearDown() throws Exception {
        client.stop();
	}

}
