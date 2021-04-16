package ca.aquiletour.web;
import java.util.Map;

import ca.aquiletour.core.models.session.SessionData;
import ca.aquiletour.core.models.users.User;
import ca.ntro.core.Path;
import ca.ntro.core.mvc.NtroContext;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.RouterService;

public class AquiletourRouterService extends RouterService {

	@Override
	public void sendFrontendMessagesFor(NtroContext<?, ?> context, Path path, Map<String, String[]> parameters) {
		T.call(this);

		@SuppressWarnings("unchecked")
		NtroContext<User, SessionData> aquiletourContext = (NtroContext<User, SessionData>) context;

		AquiletourRequestHandler.sendMessages(aquiletourContext, path, parameters);
	}

	@Override
	public void sendBackendMessagesFor(NtroContext<?, ?> context, Path path, Map<String, String[]> parameters) {
		T.call(this);

		@SuppressWarnings("unchecked")
		NtroContext<User, SessionData> aquiletourContext = (NtroContext<User, SessionData>) context;

		AquiletourBackendRequestHandler.sendMessages(aquiletourContext, path, parameters);
	}
}
