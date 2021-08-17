package ca.aquiletour.server.registered_sockets;

import ca.ntro.backend.BackendError;

public interface AuthTokenIterator {
	
	void execute(String authToken) throws BackendError;

}
