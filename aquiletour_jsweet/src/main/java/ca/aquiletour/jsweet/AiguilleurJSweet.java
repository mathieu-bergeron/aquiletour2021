package ca.aquiletour.jsweet;

import ca.aquiletour.web.AiguilleurAppWeb;
import ca.ntro.jsweet.services.NtroJSweet;

public class AiguilleurJSweet {
	
	public static void main(String[] args) {
		
		NtroJSweet.launchApp(AiguilleurAppWeb.class, args);
	}
}
