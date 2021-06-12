package ca.aquiletour.server.vertx;

import ca.ntro.backend.BackendError;
import ca.ntro.services.ModelStoreSync;

public interface LogWriter {

	String write(ModelStoreSync modelStore, StringBuilder builder) throws BackendError;

}
