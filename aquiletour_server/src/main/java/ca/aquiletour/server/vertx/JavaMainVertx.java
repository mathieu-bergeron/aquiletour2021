package ca.aquiletour.server.vertx;

import java.nio.file.Path;
import java.nio.file.Paths;

import ca.aquiletour.server.AquiletourConfig;
import ca.aquiletour.server.EarlyInitializationServer;
import ca.aquiletour.server.LocalStoreMongoDbServer;
import ca.aquiletour.server.LocalStoreServer;
import ca.aquiletour.server.MessageServiceWebserver;
import ca.aquiletour.server.backend.AquiletourBackendService;
import ca.aquiletour.web.AquiletourRouterService;
import ca.ntro.jdk.digest.PasswordDigest;
import ca.ntro.jdk.web.NtroWebServer;
import ca.ntro.services.RouterService;

public class JavaMainVertx {
	
	public static void main(String[] args) {

		NtroWebServer.defaultInitializationTask(new EarlyInitializationServer(),
												AquiletourBackendService.class, 
				                                LocalStoreServer.class,  // DEV
												//LocalStoreMongoDbServer.class, // PROD
				                                MessageServiceWebserver.class, 
				                                new AquiletourRouterService())
		             .setOptions(args)
		             .addNextTask(new AquiletourMainServerVertx())
		             .execute();
		             //.execute().addGraphWriter(new GraphTraceWriterJdk(new File("TMP")));
	}
}
