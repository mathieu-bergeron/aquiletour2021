package ca.aquiletour.jsweet;

import ca.aquiletour.web.AiguilleurFrontendWeb;
import ca.ntro.jsweet.services.NtroJSweet;

public class AiguilleurJSweet {
	
	public static void main(String[] args) {
		
		NtroJSweet.launchFrontend(AiguilleurFrontendWeb.class, args);
	}
}
