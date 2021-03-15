package ca.aquiletour.server;

import java.util.List;

import ca.ntro.core.NtroUser;
import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.services.LocalStoreFiles;
import ca.ntro.stores.ValuePath;

public class LocalStoreServer extends LocalStoreFiles {
	
	@Override
	public void onValueMethodInvoked(ValuePath valuePath, String methodName, List<Object> args) {
		T.call(LocalStoreServer.class);

		RegisteredSockets.onValueMethodInvoked(valuePath, methodName, args);
	}
}
