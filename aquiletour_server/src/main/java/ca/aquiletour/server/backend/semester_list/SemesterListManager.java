package ca.aquiletour.server.backend.semester_list;

import ca.aquiletour.core.Constants;
import ca.aquiletour.core.models.dates.CalendarWeek;
import ca.aquiletour.core.models.schedule.ScheduleItem;
import ca.aquiletour.core.models.schedule.SemesterSchedule;
import ca.aquiletour.core.models.schedule.TeacherSchedule;
import ca.aquiletour.core.models.user.User;
import ca.aquiletour.core.pages.semester_list.models.SemesterList;
import ca.aquiletour.core.pages.semester_list.models.SemesterModel;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterListTeacher;
import ca.aquiletour.core.pages.semester_list.teacher.models.SemesterModelTeacher;
import ca.ntro.core.models.ModelInitializer;
import ca.ntro.core.models.ModelStoreSync;
import ca.ntro.core.models.ModelUpdater;
import ca.ntro.core.system.trace.T;
import ca.ntro.services.Ntro;

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

		modelStore.updateModel(SemesterListTeacher.class, 
							   "admin",
							   userId,
							   new ModelUpdater<SemesterListTeacher>() {

			@Override
			public void update(SemesterListTeacher semesterList) {
				T.call(this);
				
				semesterList.addScheduleItem(semesterId, scheduleItem);
			}
		});
	}
	
	public static <SL extends SemesterList>  void addSemesterWeekForUser(ModelStoreSync modelStore, 
																		      Class<SL> modelClass, 
																		      String semesterId, 
																		      CalendarWeek semesterWeek, 
																		      User user) {

		T.call(SemesterListManager.class);
		
		addSemesterWeekToModel(modelStore, modelClass, semesterId, semesterWeek, user.getId());
	}

	public static <SL extends SemesterList<?>>  void addSemesterWeekToModel(ModelStoreSync modelStore, 
			                                                                  Class<SL> modelClass, 
			                                                                  String semesterId, 
			                                                                  CalendarWeek semesterWeek, 
			                                                                  String modelId) {
		T.call(SemesterListManager.class);

		modelStore.updateModel(modelClass, 
							   "admin",
							   modelId,
							   new ModelUpdater<SemesterList<?>>() {

			@Override
			public void update(SemesterList<?> semesterList) {
				T.call(this);
				
				semesterList.addSemesterWeek(semesterId, semesterWeek);
			}
		});
	}

	public static <SL extends SemesterList<?>>  void deleteSemesterFromModel(ModelStoreSync modelStore, 
																			   Class<SL> modelClass, 
																			   String semesterId, 
																			   String modelId) {

		T.call(SemesterListManager.class);
		
		modelStore.updateModel(modelClass, 
							   "admin",
							   modelId,
							   new ModelUpdater<SL>() {

			@Override
			public void update(SL semesterList) {
				T.call(this);

				semesterList.removeSemester(semesterId);
			}
		});
	}

	public static 
	       <SM extends SemesterModel, SL extends SemesterList<SM>> 
	       void 
	       addSemesterToModel(ModelStoreSync modelStore, 
							  Class<SL> modelClass, 
							  Class<SM> semesterModelClass,
							  String semesterId, 
							  boolean adminControlled,
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
				
				SM semester = Ntro.factory().newInstance(semesterModelClass);
				semester.setSemesterId(semesterId);
				if(semester instanceof SemesterModelTeacher) {
					((SemesterModelTeacher) semester).setAdminControlled(adminControlled);
				}
				semesterList.addSemester(semester);
			}
		});
	}

	public static <SL extends SemesterList<?>>  void deleteSemesterForUser(ModelStoreSync modelStore, 
																		        Class<SL> modelClass,
			 														            String semesterId, 
			 														            User user) {

		T.call(SemesterListManager.class);
		
		deleteSemesterFromModel(modelStore, modelClass, semesterId, user.getId());
	}

	public static 
	       <SL extends SemesterList<SM>, SM extends SemesterModel>  
	       void addSemesterForUser(ModelStoreSync modelStore, 
					               Class<SL> modelClass, 
					               Class<SM> semesterModelClass,
					               String semesterId, 
					               boolean adminControlled,
					               User user) {

		T.call(SemesterListManager.class);
		
		addSemesterToModel(modelStore, modelClass, semesterModelClass, semesterId, adminControlled, user.getId());
	}

	public static <SL extends SemesterList>  void selectCurrentSemesterForModelId(ModelStoreSync modelStore, 
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


	public static <SL extends SemesterList> void selectCurrentSemesterForUser(ModelStoreSync modelStore, 
																					Class<SL> modelClass, 
																					String semesterId, 
																					boolean currentSemester, 
																					User user) {
		T.call(SemesterListManager.class);
		
		selectCurrentSemesterForModelId(modelStore, modelClass, semesterId, currentSemester, user.getId());
	}
	
	
	public static void addCourseGroupForUserId(ModelStoreSync modelStore, 
			                                   String semesterId, 
			                                   String courseId, 
			                                   String groupId, 
			                                   String userId) {

		T.call(SemesterListManager.class);

		modelStore.updateModel(SemesterListTeacher.class, 
							   "admin",
							   userId,
							   new ModelUpdater<SemesterListTeacher>() {

			@Override
			public void update(SemesterListTeacher semesterList) {
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

	public static <SL extends SemesterList<?>> SemesterSchedule getSemesterSchedule(ModelStoreSync modelStore, 
																					  Class<SL> modelClass, 
																					  String semesterId, 
																					  String userId) {
		T.call(SemesterListManager.class);

		SemesterSchedule schedule = null;
		
		if(modelStore.ifModelExists(modelClass, "admin", userId)) {
			
			SemesterList<?> model = modelStore.getModel(modelClass, "admin", userId);
			
			schedule = model.semesterSchedule(semesterId);
		}

		return schedule;

	}

	public static <SL extends SemesterList<?>> SemesterSchedule getSemesterSchedule(ModelStoreSync modelStore, 
																					  Class<SL> modelClass, 
																					  String semesterId, 
																					  User user) {
		T.call(SemesterListManager.class);
		
		return getSemesterSchedule(modelStore, modelClass, semesterId, user.getId());
	}

	public static TeacherSchedule getTeacherSchedule(ModelStoreSync modelStore, 
			                                         String semesterId, 
			                                         String userId) {
		T.call(SemesterListManager.class);

		TeacherSchedule schedule = null;
		
		if(modelStore.ifModelExists(SemesterListTeacher.class, "admin", userId)) {
			
			SemesterListTeacher model = modelStore.getModel(SemesterListTeacher.class, "admin", userId);
			
			schedule = model.teacherSchedule(semesterId);
		}

		return schedule;
	}

	public static TeacherSchedule getTeacherSchedule(ModelStoreSync modelStore, 
			                                         String semesterId, 
			                                         User user) {
		T.call(SemesterListManager.class);
		
		return getTeacherSchedule(modelStore, semesterId, user.getId());
	}
		
}
