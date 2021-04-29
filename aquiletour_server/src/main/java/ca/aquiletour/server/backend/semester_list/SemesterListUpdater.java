package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.dates.CalendarWeek;
import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.semester_list.models.SemesterListModel;
import ca.aquiletour.core.pages.semester_list.models.SemesterModel;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class SemesterListUpdater {

	public static void addScheduleItemForUser(ModelStoreSync modelStore, 
			                                  String semesterId, 
			                                  ScheduleItem scheduleItem,
			                                  User user) {

		T.call(SemesterListUpdater.class);
		
		addScheduleItemForUserId(modelStore, semesterId, scheduleItem, user.getId());
	}

	public static void addScheduleItemForUserId(ModelStoreSync modelStore, 
			                                    String semesterId, 
			                                    ScheduleItem scheduleItem,
			                                    String userId) {

		T.call(SemesterListUpdater.class);

		modelStore.updateModel(SemesterListModel.class, 
							   "admin",
							   userId,
							   new ModelUpdater<SemesterListModel>() {

			@Override
			public void update(SemesterListModel semesterList) {
				T.call(this);
				
				semesterList.addScheduleItem(semesterId, scheduleItem);
			}
		});
	}

	
	
	
	
	public static void addSemesterWeekForUser(ModelStoreSync modelStore, 
			                                  String semesterId, 
			                                  CalendarWeek semesterWeek,
			                                  User user) {

		T.call(SemesterListUpdater.class);
		
		addSemesterWeekForUserId(modelStore, semesterId, semesterWeek, user.getId());
	}

	public static void addSemesterWeekForUserId(ModelStoreSync modelStore, 
			                                    String semesterId, 
			                                    CalendarWeek semesterWeek,
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
					semesterList.selectCurrentSemester(Constants.DRAFTS_SEMESTER_ID);
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

	public static SemesterSchedule getSemesterSchedule(ModelStoreSync modelStore, 
			                                           String semesterId, 
			                                           User user) {
		T.call(SemesterListUpdater.class);
		
		SemesterSchedule schedule = null;
		
		if(modelStore.ifModelExists(SemesterListModel.class, "admin", user.getId())) {
			
			SemesterListModel model = modelStore.getModel(SemesterListModel.class, "admin", user.getId());
			
			schedule = model.semesterSchedule(semesterId);
		}

		return schedule;
	}

	public static TeacherSchedule getTeacherSchedule(ModelStoreSync modelStore, 
			                                         String semesterId, 
			                                         User user) {
		T.call(SemesterListUpdater.class);

		TeacherSchedule schedule = null;
		
		if(modelStore.ifModelExists(SemesterListModel.class, "admin", user.getId())) {
			
			SemesterListModel model = modelStore.getModel(SemesterListModel.class, "admin", user.getId());
			
			schedule = model.teacherSchedule(semesterId);
		}

		return schedule;
	}
		
}