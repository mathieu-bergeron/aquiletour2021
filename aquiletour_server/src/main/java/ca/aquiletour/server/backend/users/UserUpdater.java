package ca.aquiletour.server.backend.users;

import java.util.List;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboard.models.DashboardModel;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
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

		if(!modelStore.ifModelExists(DashboardModel.class, "admin", user.getId())) {

			DocumentPath documentPath = new DocumentPath();
			documentPath.setCollection(Ntro.introspector().getSimpleNameForClass(DashboardModel.class));
			documentPath.setDocumentId(user.getId());
			modelStore.saveJsonString(documentPath, Ntro.jsonService().toString(new DashboardModel()));
		}
	}

	public static void updateScreenName(ModelStoreSync modelStore, String screenName, User user) {
		T.call(UserUpdater.class);
		
		modelStore.updateModel(User.class, "admin", user.getId(), new ModelUpdater<User>() {
			@Override
			public void update(User existingModel) {
				T.call(this);
				
				existingModel.setName(screenName);
				existingModel.setSurname("");
			}
		});
	}
}
