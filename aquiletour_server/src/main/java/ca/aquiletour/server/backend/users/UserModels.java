package ca.aquiletour.server.backend.users;

import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboards.DashboardModel;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;
import ca.ntro.stores.DocumentPath;

public class UserModels {

	public static void addUsers(ModelStoreSync modelStore, List<User> usersToAdd) {
		T.call(UserModels.class);

		for(User user : usersToAdd) {
			addUser(modelStore, user);
		}
	}

	public static void addUser(ModelStoreSync modelStore, User user) {
		T.call(UserModels.class);

		if(!modelStore.ifModelExists(User.class, "admin", user.getId())) {

			DocumentPath documentPath = new DocumentPath();
			documentPath.setCollection(Ntro.introspector().getSimpleNameForClass(User.class));
			documentPath.setDocumentId(user.getId());
			modelStore.saveJsonString(documentPath, Ntro.jsonService().toString(user));
		}

		if(!modelStore.ifModelExists(DashboardModel.class, "admin", user.getId())) {

			DocumentPath documentPath = new DocumentPath();
			documentPath.setCollection(Ntro.introspector().getSimpleNameForClass(DashboardModel.class));
			documentPath.setDocumentId(user.getId());
			modelStore.saveJsonString(documentPath, Ntro.jsonService().toString(new DashboardModel()));
		}
	}
}
