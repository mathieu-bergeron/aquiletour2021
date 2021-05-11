package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.dates.CalendarWeek;
import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.models.users.User;
import ca.aquiletour.core.pages.semester_list.models.SemesterListModel;
import ca.aquiletour.core.pages.semester_list.models.SemesterModel;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListModelTeacher;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;

public class SemesterListManager {

	public static void addScheduleItemForUser(ModelStoreSync modelStore, 
			                                  String semesterId, 
			                                  ScheduleItem scheduleItem, 
			                                  User user) {

		T.call(SemesterListManager.class);
		
		addScheduleItemForUserId(modelStore, semesterId, scheduleItem, user.getId());
	}

	public static void addScheduleItemForUserId(ModelStoreSync modelStore, 
			                                    String semesterId, 
			                                    ScheduleItem scheduleItem,
			                                    String userId) {

		T.call(SemesterListManager.class);

		modelStore.updateModel(SemesterListModelTeacher.class, 
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
	
	public static <SL extends SemesterListModel>  void addSemesterWeekForUser(ModelStoreSync modelStore, 
																		      Class<SL> modelClass, 
																		      String semesterId, 
																		      CalendarWeek semesterWeek, 
																		      User user) {

		T.call(SemesterListManager.class);
		
		addSemesterWeekForUserId(modelStore, modelClass, semesterId, semesterWeek, user.getId());
	}

	public static <SL extends SemesterListModel>  void addSemesterWeekForUserId(ModelStoreSync modelStore, 
			                                                                    Class<SL> modelClass, 
			                                                                    String semesterId, 
			                                                                    CalendarWeek semesterWeek, 
			                                                                    String userId) {
		T.call(SemesterListManager.class);

		modelStore.updateModel(modelClass, 
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

	public static <SL extends SemesterListModel>  void addSemesterToModel(ModelStoreSync modelStore, 
																			Class<SL> modelClass, 
																			String semesterId, 
																			String userId) {

		T.call(SemesterListManager.class);
		
		if(!modelStore.ifModelExists(modelClass, "admin", userId)) {
			modelStore.createModel(modelClass, "admin", userId, new ModelInitializer<SL>() {
				@Override
				public void initialize(SL newModel) {
					T.call(this);
				}
			});
		}

		modelStore.updateModel(modelClass, 
							   "admin",
							   userId,
							   new ModelUpdater<SL>() {

			@Override
			public void update(SL semesterList) {
				T.call(this);
				
				SemesterModel semester = new SemesterModel();
				semester.setSemesterId(semesterId);
				
				semesterList.addSemester(semester);
			}
		});
	}

	public static <SL extends SemesterListModel>  void addSemesterForUser(ModelStoreSync modelStore, 
																		  Class<SL> modelClass,
			 														      String semesterId, 
			 														      User user) {

		T.call(SemesterListManager.class);
		
		addSemesterToModel(modelStore, modelClass, semesterId, user.getId());
	}

	public static <SL extends SemesterListModel>  void selectCurrentSemesterForUserId(ModelStoreSync modelStore, 
																					  Class<SL> modelClass,
																				      String semesterId, 
																				      boolean currentSemester, 
																				      String userId) {
		T.call(SemesterListManager.class);

		if(!modelStore.ifModelExists(modelClass, "admin", userId)) {
			modelStore.createModel(modelClass, "admin", userId, new ModelInitializer<SL>() {
				@Override
				public void initialize(SL newModel) {
					T.call(this);
				}
			});
		}

		modelStore.updateModel(modelClass, 
							   "admin",
							   userId,
							   new ModelUpdater<SL>() {

			@Override
			public void update(SL semesterList) {
				T.call(this);
				
				if(currentSemester) {
					semesterList.selectCurrentSemester(semesterId);
				}else {
					semesterList.selectCurrentSemester(Constants.DRAFTS_SEMESTER_ID);
				}
				
			}
		});
	}


	public static <SL extends SemesterListModel> void selectCurrentSemesterForUser(ModelStoreSync modelStore, 
																					Class<SL> modelClass, 
																					String semesterId, 
																					boolean currentSemester, 
																					User user) {
		T.call(SemesterListManager.class);
		
		selectCurrentSemesterForUserId(modelStore, modelClass, semesterId, currentSemester, user.getId());
	}
	
	
	public static void addCourseGroupForUserId(ModelStoreSync modelStore, 
			                                   String semesterId, 
			                                   String courseId, 
			                                   String groupId, 
			                                   String userId) {

		T.call(SemesterListManager.class);

		modelStore.updateModel(SemesterListModelTeacher.class, 
							   "admin",
							   userId,
							   new ModelUpdater<SemesterListModelTeacher>() {

			@Override
			public void update(SemesterListModelTeacher semesterList) {
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

		T.call(SemesterListManager.class);
		
		addCourseGroupForUserId(modelStore, semesterId, courseId, groupId, user.getId());
	}

	public static <SL extends SemesterListModel> SemesterSchedule getSemesterSchedule(ModelStoreSync modelStore, 
																					  Class<SL> modelClass, 
																					  String semesterId, 
																					  User user) {
		T.call(SemesterListManager.class);
		
		SemesterSchedule schedule = null;
		
		if(modelStore.ifModelExists(modelClass, "admin", user.getId())) {
			
			SemesterListModel model = modelStore.getModel(modelClass, "admin", user.getId());
			
			schedule = model.semesterSchedule(semesterId);
		}

		return schedule;
	}

	public static TeacherSchedule getTeacherSchedule(ModelStoreSync modelStore, 
			                                         String semesterId, 
			                                         User user) {
		T.call(SemesterListManager.class);

		TeacherSchedule schedule = null;
		
		if(modelStore.ifModelExists(SemesterListModelTeacher.class, "admin", user.getId())) {
			
			SemesterListModel model = modelStore.getModel(SemesterListModelTeacher.class, "admin", user.getId());
			
			schedule = model.teacherSchedule(semesterId);
		}

		return schedule;
	}
		
}
