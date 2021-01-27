package ca.aquiletour.jsweet;

import ca.aquiletour.web.pages.rootpage.RootpageControllerWeb;
import ca.ntro.web.NtroWindowWeb;

public class RootpageMainJSweet extends RootpageControllerWeb {
	
	private NtroWindowJSweet window = new NtroWindowJSweet();

	@Override
	protected NtroWindowWeb getWindow() {
		return window;
	}

}
