package ca.aquiletour.server;

import ca.aquiletour.core.pages.rootpage.RootpageController;
import ca.aquiletour.web.AquiletourRequestHandler;

public class AquiletourRequestHandlerServer extends AquiletourRequestHandler {

	@Override
	protected RootpageController rootpageMain(String lang) {
		return new RootpageMainServer(lang);
	}

}
