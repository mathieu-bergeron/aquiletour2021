package ca.aquiletour.jsweet;

import ca.aquiletour.core.AquiletourMain;
import ca.aquiletour.web.ViewLoaderRegistrationWeb;
import ca.ntro.core.mvc.NtroWindow;
import ca.ntro.core.system.trace.T;
import ca.ntro.jsweet.services.NtroWindowJSweet;

public class AquiletourMainJSweet extends AquiletourMain {

	@Override
	protected void registerViewLoaders() {
		T.call(this);

		ViewLoaderRegistrationWeb.registerViewLoaders();
	}

	@Override
	protected NtroWindow getWindow() {
		T.call(this);

		return new NtroWindowJSweet();
	}
}
