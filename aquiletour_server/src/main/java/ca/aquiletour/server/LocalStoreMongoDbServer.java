package ca.aquiletour.server;

import java.util.List;

import ca.aquiletour.core.Constants;
import ca.aquiletour.server.registered_sockets.RegisteredSocketsSockJS;
import ca.ntro.backend.BackendError;
import ca.ntro.core.system.trace.T;
import ca.ntro.jdk.services.LocalStoreMongoDb;
import ca.ntro.services.ModelIdReader;
import ca.ntro.services.Ntro;
import ca.ntro.stores.ValuePath;

public class LocalStoreMongoDbServer extends LocalStoreMongoDb {

	@Override
	protected String connectionString() {
		
		String connectionString;
		
		if(Ntro.config().isProd()) {
			
			connectionString = Constants.MONGO_DB_CONNECTION_STRING;
			
		}else {
			
			connectionString = "localhost:27017";
		}
		
		return connectionString;
		
	}

	@Override
	protected String databaseName() {
		return Constants.MONGO_DB_DATABASE_NAME;
	}
}
