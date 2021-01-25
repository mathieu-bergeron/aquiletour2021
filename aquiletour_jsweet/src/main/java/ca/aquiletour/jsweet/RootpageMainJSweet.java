package ca.aquiletour.jsweet;

import ca.aquiletour.web.RootpageMainWeb;
import ca.ntro.core.web.NtroWindowWeb;

public class RootpageMainJSweet extends RootpageMainWeb {

	public RootpageMainJSweet(String lang) {
		super(lang);
	}

	@Override
	protected NtroWindowWeb getWindow() {
		return new NtroWindowJSweet();
	}

}
