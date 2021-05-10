package ca.aquiletour.server.backend.users;

import java.util.List;

import ca.aquiletour.core.models.users.Teacher;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.dashboard.models.DashboardModel;
import ca.aquiletour.core.pages.dashboard.student.models.DashboardModelStudent;
import ca.aquiletour.core.pages.dashboard.teacher.models.DashboardModelTeacher;
import ca.aquiletour.server.backend.dashboard.DashboardUpdater;
import ca.ntro.core.models.ModelInitializer;
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
			
			// FIXME: old way... because new way below is not aquivalent(??)
            DocumentPath documentPath = new DocumentPath();
            documentPath.setCollection(Ntro.introspector().getSimpleNameForClass(User.class));
            documentPath.setDocumentId(user.getId());
            modelStore.saveJsonString(documentPath, Ntro.jsonService().toString(user));

			/*
			modelStore.createModel(User.class, "admin", user.getId(), new ModelInitializer<User>() {
				@Override
				public void initialize(User newModel) {
					T.call(this);

					newModel.copyPublicInfomation(user);
					newModel.setAuthToken(user.getAuthToken());
				}
			});*/
		}
		
		if(user instanceof Teacher) {

			DashboardUpdater.createDashboardForUser(modelStore, DashboardModelTeacher.class, user);
			DashboardUpdater.createDashboardForUser(modelStore, DashboardModelStudent.class, user);

		}else {

			DashboardUpdater.createDashboardForUser(modelStore, DashboardModelStudent.class, user);
		}
	}

	public static void updateScreenName(ModelStoreSync modelStore, String screenName, User user) {
		T.call(UserUpdater.class);
		
		modelStore.updateModel(User.class, "admin", user.getId(), new ModelUpdater<User>() {
			@Override
			public void update(User existingModel) {
				T.call(this);
				
				existingModel.setFirstname(screenName);
				existingModel.setLastname("");
			}
		});
	}

	public static void toggleStudentMode(ModelStoreSync modelStore, User user) {
		T.call(UserUpdater.class);

		modelStore.updateModel(User.class, "admin", user.getId(), new ModelUpdater<User>() {
			@Override
			public void update(User userModel) {
				T.call(this);
				
				if(userModel instanceof Teacher) {
					Teacher teacher = (Teacher) userModel;
					teacher.toggleStudentMode();
				}
			}
		});
		
	}
}
