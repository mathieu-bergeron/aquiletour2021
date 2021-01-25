package ca.aquiletour.jsweet;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.pages.rootpage.RootpageMain;

public class AquiletourMainJSweet extends AquiletourMain {
	
	@Override
	protected RootpageMain rootpageMain(String lang) {
		return new RootpageMainJSweet(lang);
	}
}
