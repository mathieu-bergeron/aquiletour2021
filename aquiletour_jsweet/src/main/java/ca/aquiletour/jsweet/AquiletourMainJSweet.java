package ca.aquiletour.jsweet;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.core.pages.rootpage.RootpageController;
import ca.ntro.core.system.trace.T;

public class AquiletourMainJSweet extends AquiletourMain {
	
	@Override
	protected RootpageController rootpageMain(String lang) {
		T.call(this);

		return new RootpageMainJSweet(lang);
	}
}
