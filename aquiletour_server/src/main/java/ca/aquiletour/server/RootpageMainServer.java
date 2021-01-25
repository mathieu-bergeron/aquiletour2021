package ca.aquiletour.server;

import ca.aquiletour.web.page.rootpage.RootpageMainWeb;
import ca.ntro.core.web.NtroWindowWeb;

public class RootpageMainServer extends RootpageMainWeb {
	
	private NtroWindowServer window = new NtroWindowServer();

	public RootpageMainServer(String lang) {
		super(lang);
	}

	@Override
	protected NtroWindowWeb getWindow() {
		return window;
	}

}
