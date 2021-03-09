package ca.ntro.jsweet.test;

import ca.ntro.jsweet.NtroJSweet;
import ca.ntro.jsweet.services.BackendServiceJSweet;

public class JavaMainTest {
	
	public static void main(String[] args) {
		
		String[] options = new String[] {"--traceLevel","APP"};
		
		BackendServiceJSweet backendServiceJSweet = new BackendServiceJSweet("ws://localhost:8080/_messages");
		
		NtroJSweet.defaultInitializationTask(backendServiceJSweet)
				  .setOptions(options)
				  .addNextTask(new NtroMainTest())
				  .execute();
	}
}
