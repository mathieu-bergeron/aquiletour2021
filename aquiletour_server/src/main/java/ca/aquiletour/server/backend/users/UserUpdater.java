package ca.aquiletour.server.backend.users;

import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;

public class UserUpdater {

	public static void addUsers(ModelStoreSync modelStore, List<User> usersToAdd) {
		T.call(UserUpdater.class);

		for(User user : usersToAdd) {
			addUser(modelStore, user);
		}
	}

	public static void addUser(ModelStoreSync modelStore, User user) {
		T.call(UserUpdater.class);

		if(!modelStore.ifModelExists(User.class, "admin", user.getId())) {

			DocumentPath documentPath = new DocumentPath();
			documentPath.setCollection(Ntro.introspector().getSimpleNameForClass(User.class));
			documentPath.setDocumentId(user.getId());
			modelStore.saveJsonString(documentPath, Ntro.jsonService().toString(user));
		}
	}
}
