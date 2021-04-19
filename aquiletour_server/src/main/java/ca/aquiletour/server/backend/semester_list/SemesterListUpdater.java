package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.dates.SemesterWeek;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.semester_list.models.SemesterListModel;
import ca.aquiletour.core.pages.semester_list.models.SemesterModel;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class SemesterListUpdater {

	public static void addSemesterWeekForUser(ModelStoreSync modelStore, 
			                                  String semesterId, 
			                                  SemesterWeek semesterWeek,
			                                  User user) {

		T.call(SemesterListUpdater.class);
		
		addSemesterWeekForUserId(modelStore, semesterId, semesterWeek, user.getId());
	}

	public static void addSemesterWeekForUserId(ModelStoreSync modelStore, 
			                                    String semesterId, 
			                                    SemesterWeek semesterWeek,
			                                    String userId) {
		T.call(SemesterListUpdater.class);

		modelStore.updateModel(SemesterListModel.class, 
							   "admin",
							   userId,
							   new ModelUpdater<SemesterListModel>() {

			@Override
			public void update(SemesterListModel semesterList) {
				T.call(this);
				
				semesterList.addSemesterWeek(semesterId, semesterWeek);
			}
		});
	}

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

	public static void selectCurrentSemesterForUserId(ModelStoreSync modelStore, 
			                                          String semesterId, 
			                                          boolean currentSemester, 
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
				
				if(currentSemester) {
					semesterList.selectCurrentSemester(semesterId);
				}else {
					semesterList.selectCurrentSemester(Constants.COURSE_DRAFTS);
				}
				
			}
		});
	}


	public static void selectCurrentSemesterForUser(ModelStoreSync modelStore, 
			                                        String semesterId, 
			                                        boolean currentSemester, 
			                                        User user) {
		T.call(SemesterListUpdater.class);
		
		selectCurrentSemesterForUserId(modelStore, semesterId, currentSemester, user.getId());
	}
	
	
	public static void addCourseGroupForUserId(ModelStoreSync modelStore, 
			                                   String semesterId, 
			                                   String courseId, 
			                                   String groupId, 
			                                   String userId) {

		T.call(SemesterListUpdater.class);

		modelStore.updateModel(SemesterListModel.class, 
							   "admin",
							   userId,
							   new ModelUpdater<SemesterListModel>() {

			@Override
			public void update(SemesterListModel semesterList) {
				T.call(this);

				semesterList.addCourseGroup(semesterId, courseId, groupId);
			}
		});
	}
	

	public static void addCourseGroupForUser(ModelStoreSync modelStore, 
			                                 String semesterId, 
			                                 String courseId, 
			                                 String groupId, 
			                                 User user) {

		T.call(SemesterListUpdater.class);
		
		addCourseGroupForUserId(modelStore, semesterId, courseId, groupId, user.getId());
	}
		
}
