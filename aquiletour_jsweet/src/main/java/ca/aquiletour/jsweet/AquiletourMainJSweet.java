package ca.aquiletour.jsweet;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.web.ViewLoaderRegistrationWeb;
import ca.ntro.core.system.trace.T;

public class AquiletourMainJSweet extends AquiletourMain {

	@Override
	protected void registerViewLoaders() {
		T.call(this);

		ViewLoaderRegistrationWeb.registerViewLoaders();
	}
}
