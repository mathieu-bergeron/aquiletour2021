package ca.aquiletour.server;

import java.util.List;

import ca.ntro.core.json.JsonLoader;
import ca.ntro.core.models.NtroModel;
import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.services.LocalStoreFiles;
import ca.ntro.messages.NtroModelMessage;
import ca.ntro.stores.DocumentPath;
import ca.ntro.stores.ValuePath;
import ca.ntro.users.NtroUser;

public class LocalStoreServer extends LocalStoreFiles {
	
	@Override
	public void onValueMethodInvoked(ValuePath valuePath, String methodName, List<Object> args) {
		T.call(LocalStoreServer.class);

		RegisteredSockets.onValueMethodInvoked(valuePath, methodName, args);
	}

	@Override
	protected JsonLoader jsonLoaderFromRequest(String serviceUrl, NtroModelMessage message) {
		
		return null;

	}

}
