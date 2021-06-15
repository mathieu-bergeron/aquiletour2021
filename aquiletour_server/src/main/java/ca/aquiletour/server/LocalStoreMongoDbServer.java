package ca.aquiletour.server;

import ca.aquiletour.core.Constants;
import ca.ntro.backend.BackendError;
import ca.ntro.jdk.services.LocalStoreMongoDb;
import ca.ntro.services.ModelIdReader;

public class LocalStoreMongoDbServer extends LocalStoreMongoDb {

	@Override
	protected String connectionString() {
		return Constants.MONGO_DB_CONNECTION_STRING;
	}

	@Override
	protected String databaseName() {
		return Constants.MONGO_DB_DATABASE_NAME;
	}


}
