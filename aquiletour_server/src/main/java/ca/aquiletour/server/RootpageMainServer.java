package ca.aquiletour.server;

import ca.aquiletour.web.pages.rootpage.RootpageControllerWeb;
import ca.ntro.web.NtroWindowWeb;

public class RootpageMainServer extends RootpageControllerWeb {
	
	private NtroWindowServer window = new NtroWindowServer();

	@Override
	protected NtroWindowWeb getWindow() {
		return window;
	}

}
