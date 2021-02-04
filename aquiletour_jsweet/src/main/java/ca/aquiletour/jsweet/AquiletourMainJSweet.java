package ca.aquiletour.jsweet;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.web.ViewRegistrationWeb;
import ca.ntro.core.system.trace.T;

public class AquiletourMainJSweet extends AquiletourMain {

	@Override
	protected void registerViewLoaders() {
		T.call(this);

		ViewRegistrationWeb.registerViewLoaders();
	}
}
