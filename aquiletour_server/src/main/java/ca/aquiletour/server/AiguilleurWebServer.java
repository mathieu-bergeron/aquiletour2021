package ca.aquiletour.server;
import ca.aquiletour.web.AiguilleurAppWeb;
import ca.ntro.jdk.web.NtroWebServer;

public class AiguilleurWebServer {

	public static void main(String[] args) {

		NtroWebServer.launchServer(AiguilleurBackend.class, AiguilleurAppWeb.class, args);
	}

}