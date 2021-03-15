package ca.aquiletour.server;
import ca.aquiletour.web.AiguilleurFrontendWeb;
import ca.ntro.jdk.web.NtroWebServer;

public class AiguilleurWebServer {

	public static void main(String[] args) {

		NtroWebServer.launchApp(AiguilleurBackend.class, AiguilleurFrontendWeb.class, args);
	}

}
