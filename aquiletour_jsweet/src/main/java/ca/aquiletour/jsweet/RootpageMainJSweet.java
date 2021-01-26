package ca.aquiletour.jsweet;

import ca.aquiletour.web.page.rootpage.RootpageMainWeb;
import ca.ntro.core.system.trace.T;
import ca.ntro.web.NtroWindowWeb;

public class RootpageMainJSweet extends RootpageMainWeb {
	
	private NtroWindowJSweet window = new NtroWindowJSweet();

	public RootpageMainJSweet(String lang) {
		super(lang);
		T.call(this);
	}

	@Override
	protected NtroWindowWeb getWindow() {
		return window;
	}

}
