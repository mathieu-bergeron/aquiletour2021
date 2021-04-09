package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.semester_list.models.SemesterListModel;
import ca.aquiletour.core.pages.semester_list.models.SemesterModel;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class SemesterListUpdater {

	public static void addSemesterForUserId(ModelStoreSync modelStore, 
			                              String semesterId, 
			                              String userId) {

		T.call(SemesterListUpdater.class);
		
		if(!modelStore.ifModelExists(SemesterListModel.class, "admin", userId)) {
			modelStore.createModel(SemesterListModel.class, "admin", userId, new ModelInitializer<SemesterListModel>() {
				@Override
				public void initialize(SemesterListModel newModel) {
					T.call(this);
				}
			});
		}

		modelStore.updateModel(SemesterListModel.class, 
							   "admin",
							   userId,
							   new ModelUpdater<SemesterListModel>() {

			@Override
			public void update(SemesterListModel semesterList) {
				T.call(this);
				
				SemesterModel semester = new SemesterModel();
				semester.setSemesterId(semesterId);
				
				semesterList.addSemester(semester);
			}
		});
		
		
		
	}

	public static void addSemesterForUser(ModelStoreSync modelStore, 
			                              String semesterId, 
			                              User user) {

		T.call(SemesterListUpdater.class);
		
		addSemesterForUserId(modelStore, semesterId, user.getId());
	}
}
