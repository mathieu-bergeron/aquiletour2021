package ca.aquiletour.server;

import ca.aquiletour.core.rootpage.RootpageMain;
import ca.aquiletour.web.AquiletourRequestHandler;

public class AquiletourRequestHandlerServer extends AquiletourRequestHandler {

	@Override
	protected RootpageMain rootpageMain(String lang) {
		return new RootpageMainServer(lang);
	}

}
