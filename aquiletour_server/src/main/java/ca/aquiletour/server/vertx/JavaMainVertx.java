package ca.aquiletour.server.vertx;

import java.nio.file.Path;
import java.nio.file.Paths;

import ca.aquiletour.server.AquiletourConfig;
import ca.aquiletour.server.LocalStoreServer;
import ca.aquiletour.server.MessageServiceWebserver;
import ca.aquiletour.server.backend.AquiletourBackendService;
import ca.aquiletour.web.AquiletourRouterService;
import ca.ntro.jdk.digest.PasswordDigest;
import ca.ntro.jdk.web.NtroWebServer;
import ca.ntro.services.RouterService;

public class JavaMainVertx {
	
	public static void main(String[] args) {
		String userHome = System.getProperty("user.home");
		
		Path configFilepath = Paths.get(userHome, ".aiguilleur", "config.json");
		
		AquiletourConfig config = AquiletourConfig.loadFromJson(configFilepath);

		PasswordDigest.initialize(config.getPasswordSalt());
		
		RouterService routerService = new AquiletourRouterService();
		
		NtroWebServer.defaultInitializationTask(AquiletourBackendService.class, 
				                                LocalStoreServer.class, 
				                                MessageServiceWebserver.class, 
				                                config, 
				                                routerService)
		             .setOptions(args)
		             .addNextTask(new AquiletourMainServerVertx())
		             .execute();
		             //.execute().addGraphWriter(new GraphTraceWriterJdk(new File("TMP")));
	}
}
