package ca.aquiletour.server.vertx;


import ca.aquiletour.server.EarlyInitializationServer;
import ca.aquiletour.server.LocalStoreServer;
import ca.aquiletour.server.MessageServiceWebserver;
import ca.aquiletour.server.backend.AquiletourBackendService;
import ca.aquiletour.web.AquiletourRouterService;
import ca.ntro.jdk.web.NtroWebServer;

public class JavaMainVertx {
	
	public static void main(String[] args) {

		NtroWebServer.defaultInitializationTask(new EarlyInitializationServer(),
												AquiletourBackendService.class, 
				                                LocalStoreServer.class, // XXX: see LocalStoreServer.java to select MongoDB as database backend
				                                MessageServiceWebserver.class, 
				                                new AquiletourRouterService())
		             .setOptions(args)
		             .addNextTask(new AquiletourMainServerVertx())
		             .execute();
		             //.execute().addGraphWriter(new GraphTraceWriterJdk(new File("TMP")));
	}
}
